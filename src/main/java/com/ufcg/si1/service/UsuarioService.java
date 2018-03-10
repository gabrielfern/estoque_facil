package com.ufcg.si1.service;

import com.ufcg.si1.model.ItemVenda;
import com.ufcg.si1.model.Reserva;
import com.ufcg.si1.model.Usuario;
import com.ufcg.si1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoService produtoService;

    public void saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }


    public boolean usuarioExiste(Usuario usuario) {
        return usuarioRepository.findOne(usuario.getLogin()) != null;
    }

    public boolean registraReserva(String login, String senha, Reserva reserva) {
        Usuario usuarioBusca = usuarioRepository.findOne(login);
        if(usuarioBusca != null && usuarioBusca.getSenha().equals(senha)) {
            double valorTotal = 0.0;
            for(ItemVenda itemVenda: reserva.getItens()) {
                Integer idProduct = itemVenda.pegaProduto().getId();
                itemVenda.setDetalhesProduto(produtoService.getDetalhesProduto(idProduct));
                valorTotal += produtoService.calculaPreco(idProduct, itemVenda.getQtd());
            }
            reserva.setValorTotal(valorTotal);
            usuarioBusca.adicionaReserva(reserva);
            usuarioRepository.save(usuarioBusca);
            return true;
        }

        return false;
    }

    public List<Reserva> getAllReservas(String login, String senha) {
        List<Reserva> listaReserva = new ArrayList<>();
        Usuario usuarioBusca = usuarioRepository.findOne(login);
        if(usuarioBusca != null && usuarioBusca.getSenha().equals(senha)) {
            listaReserva = usuarioBusca.getReservaList();
        }
        return listaReserva;
    }
}
