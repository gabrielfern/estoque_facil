package com.ufcg.si1.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Venda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	@OneToMany(cascade = CascadeType.ALL)
	private List<ItemVenda> itens;

	@Column
	private double valorTotal;
	
	public Venda() {
		this.valorTotal = 0.0;
	}
	
	public Venda(Integer id, List<ItemVenda> itens) {
		this(id, itens, 0.0);
	}
	
	public Venda(Integer id, List<ItemVenda> itens, double valorTotal) {
		this.id = id;
		this.itens = itens;
		this.valorTotal = valorTotal;
	}


	public List<ItemVenda> getItens() {
		return itens;
	}


	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}


	public double getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}