package com.ufcg.si1.model.enums;


public enum Situacao {

	DISPONIVEL("Disponivel"), 
	INDISPONIVEL("Indisponivel");
	
	private final String disponibilidade;
	
	Situacao(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
	public String getDisponibilidade() {
		return this.disponibilidade;
	}

}
