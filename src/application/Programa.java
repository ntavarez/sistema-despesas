package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PessoaDao pessoaDao = DaoFactory.createPessoaDao();
		
		System.out.println("-- Pessoa findByID --");
		Pessoa pessoa = pessoaDao.findById(3);
		System.out.println(pessoa);
		
		System.out.println("\n-- Pessoa findAll --");
		List<Pessoa> list = pessoaDao.findAll();
		for (Pessoa obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n-- Pessoa INSERT --");
		System.out.print("Nome: ");
		String nome = sc.next();
		System.out.print("CPF: ");
		String cpf = sc.next();
		System.out.println("Salário: ");
		Double salario = sc.nextDouble();
		
		Pessoa newPessoa = new Pessoa(null, nome, cpf, salario);
		pessoaDao.insert(newPessoa);
		System.out.println("Objeto inserido! Novo ID = " + newPessoa.getId());
		
		System.out.println("\n-- Pessoa UPDATE --");
		pessoa = pessoaDao.findById(1);
		pessoa.setNome("Natan Silva");
		pessoaDao.update(pessoa);
		System.out.println("Objeto atualizado!");
		
		System.out.println("\n-- Pessoa DELETE --");
		System.out.println("Digite o ID para deleção: ");
		int id = sc.nextInt();
		pessoaDao.deleteById(id);
		System.out.println("Objeto deletado!");
		
		sc.close();
		
	}
}
