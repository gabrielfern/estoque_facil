package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class ItemVenda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;

	transient Produto produto;

	@Column
	private int qtd;
	
	public ItemVenda() {
		
	}
	
	public ItemVenda(Integer id, Produto produto, int qtd) {
		this.id = id;
		this.produto = produto;
		this.qtd = qtd;
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public int getQtd() {
		return qtd;
	}


	public void setQtd(int qtd) {
		this.qtd = qtd;
	}


	@Override
	public String toString() {
		return "ItemVenda [produto=" + produto + ", qtd=" + qtd + "]";
	}
	
	
}