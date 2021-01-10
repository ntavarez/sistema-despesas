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
import model.dao.DespesasDao;
import model.entities.Despesas;

public class DespesasDaoJDBC implements DespesasDao{
	
	private Connection conn;

	public DespesasDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Despesas obj) {
		PreparedStatement st = null; 
		try {
			st = conn.prepareStatement(
					"INSERT INTO DESPESAS "
					+ "(Nome, Tipo, ValorDespesa, FormaDePagamento, QuantidadeParcelas, ValorParcelas) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTipo());
			st.setDouble(3, obj.getValorDespesa());
			st.setString(4, obj.getFormaDePagamento());
			st.setInt(5, obj.getQuantidadeParcelas());
			st.setDouble(6, obj.getValorParcelas());
			
		
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
	public void update(Despesas obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE despesas "
										+ "SET Nome = ?, Tipo = ?, ValorDespesa = ?, FormaDePagamento = ?, QuantidadeParcelas = ?, ValorParcelas = ? "
										+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTipo());
			st.setDouble(3, obj.getValorDespesa());
			st.setString(4, obj.getFormaDePagamento());
			st.setInt(5, obj.getQuantidadeParcelas());
			st.setDouble(6, obj.getValorParcelas());
	
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
			st = conn.prepareStatement("DELETE FROM despesas WHERE Id = ?");
			
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
	public Despesas findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM despesas WHERE Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery(); // executa o comando
			
			if (rs.next()) {
				Despesas dep = instantiateDespesas(rs);
				return dep;
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

	private Despesas instantiateDespesas(ResultSet rs) throws SQLException {
		Despesas obj = new Despesas();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setTipo(rs.getString("Tipo"));
		obj.setValorDespesa(rs.getDouble("ValorDespesa"));
		obj.setFormaDePagamento(rs.getString("FormaDePagamento"));
		obj.setQuantidadeParcelas(rs.getByte("QuantidadeParcelas"));
		obj.setValorParcelas(rs.getDouble("ValorParcelas"));
		return obj;
	}

	@Override
	public List<Despesas> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM despesas ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Despesas> list = new ArrayList<>();
			Map<Integer, Despesas> map = new HashMap<>(); //evitar repetições
			
			while (rs.next()) {
				Despesas despesas = map.get(rs.getInt("Id")); // se já existir o id informado no banco, retornará o id
				
				if (despesas == null){ // caso a despesa não exista, instanciar nova despesa
					despesas = instantiateDespesas(rs);
					map.put(rs.getInt("Id"), despesas);
				}
				
				Despesas obj = instantiateDespesas(rs);
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
