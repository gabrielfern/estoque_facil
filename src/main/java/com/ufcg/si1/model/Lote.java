package com.ufcg.si1.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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


	public boolean vencido() {
		Data data = Data.dataHoje();
		if (data.compareTo(this.getDataDeValidade()) >= 0)
			return true;
		return false;
	}


    @Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", numeroDeItens=" + numeroDeItens +
                ", dataDeValidade='" + dataDeValidade + '\'' +
                '}';
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lote other = (Lote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
