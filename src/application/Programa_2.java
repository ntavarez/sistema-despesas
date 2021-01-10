package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DespesasDao;
import model.entities.Despesas;

public class Programa_2 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		DespesasDao despesasDao = DaoFactory.createDespesasDao();

		System.out.println("-- Despesas findByID --");
		Despesas despesas = despesasDao.findById(3);
		System.out.println(despesas);

		System.out.println("\n-- Despesas findAll --");
		List<Despesas> list = despesasDao.findAll();
		for (Despesas obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n-- Despesas INSERT --");
		System.out.println("Tipo: ");
		String tipo = sc.nextLine();
		System.out.print("Nome: ");
		String nomeDespesa = sc.next();
		System.out.print("Valor: ");
		Double valorDespesa = sc.nextDouble();
		System.out.println("Forma de pagamento (C/D): ");
		String formaPagamento = sc.nextLine();
		
		if(formaPagamento.equals("C")) {
			System.out.print("Quantidade de parcelas: ");
			int quantidadeParcelas = sc.nextInt();
			System.out.println("Valor das parcelas: ");
			Double valorParcelas = sc.nextDouble();
			
			Despesas newDespesas = new Despesas(null, tipo, nomeDespesa, valorDespesa, formaPagamento, quantidadeParcelas, valorParcelas);
			despesasDao.insert(newDespesas);
			System.out.println("Objeto inserido! Novo ID = " + newDespesas.getId());
			
		} else if(formaPagamento.equals("D")) {
			int quantidadeParcelas = 0;
			Double valorParcelas = 0.0;
			
			Despesas newDespesas = new Despesas(null, tipo, nomeDespesa, valorDespesa, formaPagamento, quantidadeParcelas, valorParcelas);
			despesasDao.insert(newDespesas);
			System.out.println("Objeto inserido! Novo ID = " + newDespesas.getId());
			
		} else {
			System.out.println("Dígito inválido! Favor escolher C ou D para prosseguir.");
		}

		System.out.println("\n-- Despesas UPDATE --");
		despesas = despesasDao.findById(6);
		despesas.setValorDespesa(1200.0);
		despesasDao.update(despesas);
		System.out.println("Objeto atualizado!");

		System.out.println("\n-- Despesas DELETE --");
		System.out.println("Digite o ID para deleção: ");
		int id = sc.nextInt();
		despesasDao.deleteById(id);
		System.out.println("Objeto deletado!");

		sc.close();
	}
}
