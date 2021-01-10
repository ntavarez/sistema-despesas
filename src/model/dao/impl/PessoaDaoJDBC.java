package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.DB;
import DB.DbException;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class PessoaDaoJDBC implements PessoaDao {
	
	private Connection conn;

	public PessoaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Pessoa obj) {
		PreparedStatement st = null; 
		try {
			st = conn.prepareStatement("INSERT INTO PESSOAS "
										+ "(Nome, Cpf, Salario) "
										+ "VALUES "
										+ "(?, ?, ?)",
										Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setDouble(3, obj.getSalario());
		
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma nova linha inserida.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE pessoas "
										+ "SET Nome = ?, Cpf = ?, Salario = ? "
										+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setDouble(3, obj.getSalario());
	
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM pessoas WHERE Id = ?");
			
			st.setInt(1, id);
			
			int rows = st.executeUpdate();
			
			if (rows == 0) {
				throw new DbException("Esse ID não existe!");
			}
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Pessoa findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pessoas WHERE Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery(); // executa o comando
			
			if (rs.next()) {
				Pessoa pessoa = instantiatePessoa(rs);
				return pessoa;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Pessoa instantiatePessoa(ResultSet rs) throws SQLException {
		Pessoa obj = new Pessoa();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setCpf(rs.getString("Cpf"));
		obj.setSalario(rs.getDouble("Salario"));
		return obj;
	}

	@Override
	public List<Pessoa> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pessoas ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Pessoa> list = new ArrayList<>();
			Map<Integer, Pessoa> map = new HashMap<>(); //evitar repetições
			
			while (rs.next()) {
				Pessoa pessoa = map.get(rs.getInt("Id")); // se já existir o id informado no banco, retornará o id
				
				if (pessoa == null){ // caso a pessoa não exista, instanciar nova pessoa
					pessoa = instantiatePessoa(rs);
					map.put(rs.getInt("Id"), pessoa);
				}
				
				Pessoa obj = instantiatePessoa(rs);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
