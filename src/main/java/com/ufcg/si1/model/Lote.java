package com.ufcg.si1.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lote {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Integer id;
	@Column
    private int numeroDeItens;
	@Column
    private String dataDeValidade;


    public Lote() {
    }

    public Lote(Integer id, Produto produto, int numeroDeItens, String dataDeValidade) {
        this.id = id;
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public int getNumeroDeItens() {
        return numeroDeItens;
    }


    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }


    public String getDataDeValidade() {
        return dataDeValidade;
    }


    public void setDataDeValidade(String dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }


    @Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", numeroDeItens=" + numeroDeItens +
                ", dataDeValidade='" + dataDeValidade + '\'' +
                '}';
    }
    
    
}
