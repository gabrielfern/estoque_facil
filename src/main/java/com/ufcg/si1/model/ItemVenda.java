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
	private String detalhesProduto;

	@Column
	private Integer idProduto;

	@Column
	private int qtd;
	
	public ItemVenda() {
		this.qtd = 0;
	}
	
	public ItemVenda(Produto produto, int qtd, String detalhesProduto) {
		this.produto = produto;
		this.qtd = qtd;
		this.detalhesProduto = detalhesProduto;
	}
	
	public ItemVenda(Integer id, Produto produto, int qtd, String detalhesProduto) {
		this(produto, qtd, detalhesProduto);
		this.id = id;
	}
	


	public Produto pegaProduto() {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetalhesProduto() {
		return detalhesProduto;
	}

	public void setDetalhesProduto(String detalhesProduto) {
		this.detalhesProduto = detalhesProduto;
	}

	@Override
	public String toString() {
		return "ItemVenda [produto=" + produto + ", qtd=" + qtd + "]";
	}


	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
}