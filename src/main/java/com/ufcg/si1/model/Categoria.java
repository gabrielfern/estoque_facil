package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Integer id;

	@Column
	private String nome;

	@Column
	private float desconto;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public float getDesconto() {
		return desconto;
	}


	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}


	public float calculaDesconto(float valor) {
		return valor * desconto;
	}
}
