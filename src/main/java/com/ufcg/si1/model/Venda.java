package com.ufcg.si1.model;

import java.util.List;


public class Venda {

	private List<ItemVenda> itens;

	private float valorTotal;


	public List<ItemVenda> getItens() {
		return itens;
	}


	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}


	public float getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
}