package com.ufcg.si1.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Reserva> reservaList;

    @Id
    @Column
    private String login;

    @Column
    private String senha;


    public Usuario() {
        this.reservaList = new ArrayList<>();
    }

    public Usuario(List<Reserva> reservaList, String login, String senha) {
        this.reservaList = reservaList;
        this.login = login;
        this.senha = senha;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }


    public void adicionaReserva(Reserva reserva) {
        this.reservaList.add(reserva);
    }
}
