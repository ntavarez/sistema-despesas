package model.dao;

import java.util.List;

import model.entities.Despesas;

public interface DespesasDao {

	void insert(Despesas obj);
	void update(Despesas obj);
	void deleteById(Integer id);
	Despesas findById(Integer id);
	List<Despesas> findAll();
	
}
