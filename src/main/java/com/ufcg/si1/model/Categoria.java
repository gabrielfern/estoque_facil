package com.ufcg.si1.model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Integer id;

	@Column
	private String nome;

	@Column
	private double desconto;


	public Categoria() {

	}


	public Categoria(List<Produto> produtos, String nome, double desconto) {
		this.nome = nome;
		this.desconto = desconto;
	}


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


	public double getDesconto() {
		return desconto;
	}


	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}
}
