package com.ufcg.si1.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ufcg.si1.model.Data;

@Entity
public class Lote {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Integer id;

	@Column
    private int numeroDeItens;

	  @OneToOne(cascade=CascadeType.ALL)
	  @JoinColumn(name="DATA_ID")
    private Data dataDeValidade;


    public Lote() {
    }

    public Lote(Integer id, int numeroDeItens, Data dataDeValidade) {
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


    public Data getDataDeValidade() {
        return dataDeValidade;
    }


    public void setDataDeValidade(Data dataDeValidade) {
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
