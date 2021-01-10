package model.entities;

import java.io.Serializable;

public class Despesas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String tipo;
	private Double valorDespesa;
	private String formaDePagamento;
	private int quantidadeParcelas;
	private Double valorParcelas;
	
	public Despesas() {
	}

	public Despesas(Integer id, String tipo, String nome, Double valorDespesa, String formaDePagamento, int quantidadeParcelas, Double valorParcelas) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.valorDespesa = valorDespesa;
		this.formaDePagamento = formaDePagamento;
		this.quantidadeParcelas = quantidadeParcelas;
		this.valorParcelas = valorParcelas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(Double valorDespesa) {
		this.valorDespesa = valorDespesa;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public Double getValorParcelas() {
		return valorParcelas;
	}

	public void setValorParcelas(Double valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesas other = (Despesas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
