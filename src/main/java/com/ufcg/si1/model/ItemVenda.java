	package com.ufcg.si1.model;


public class ItemVenda {

	private Produto produto;

	private int qtd;


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