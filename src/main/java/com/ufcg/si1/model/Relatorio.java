package com.ufcg.si1.model;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {

    private List<Produto> produtos;
    private List<Venda> registrosVenda;
    private double receitaArrecadada;

    public Relatorio() {
        this.produtos = new ArrayList<Produto>();
        this.registrosVenda = new ArrayList<Venda>();
        this.receitaArrecadada = 0;
    }

    public Relatorio(List<Produto> produtos, List<Venda> registrosVenda, double receitaArrecadada) {
        this.produtos = produtos;
        this.registrosVenda = registrosVenda;
        this.receitaArrecadada = receitaArrecadada;
    }


    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Venda> getRegistrosVenda() {
        return registrosVenda;
    }

    public void setRegistrosVenda(List<Venda> registrosVenda) {
        this.registrosVenda = registrosVenda;
    }

    public double getReceitaArrecadada() {
        return receitaArrecadada;
    }

    public void setReceitaArrecadada(double receitaArrecadada) {
        this.receitaArrecadada = receitaArrecadada;
    }
}
