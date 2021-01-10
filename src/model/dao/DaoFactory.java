package model.dao;

import DB.DB;
import model.dao.impl.DespesasDaoJDBC;
import model.dao.impl.PessoaDaoJDBC;

public class DaoFactory {

	public static DespesasDao createDespesasDao() {
		return new DespesasDaoJDBC(DB.getConnection());
	}
	
	public static PessoaDao createPessoaDao() {
		return new PessoaDaoJDBC(DB.getConnection());
	}
	
}
