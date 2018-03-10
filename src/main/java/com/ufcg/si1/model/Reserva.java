package com.ufcg.si1.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemVenda> itens;

    @Column
    private String dataReserva;

    @Column
    private double valorTotal;


    public Reserva() {

    }

    public Reserva(Integer id, List<ItemVenda> itens, String dataReserva, double valorTotal) {
        this.id = id;
        this.itens = itens;
        this.dataReserva = dataReserva;
        this.valorTotal = valorTotal;
    }


    public double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public int getItensReservados() {
        int qtd = 0;
        for(ItemVenda itemVenda: this.itens) {
            qtd += itemVenda.getQtd();
        }

        return qtd;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
