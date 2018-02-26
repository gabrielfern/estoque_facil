package com.ufcg.si1.service;

import com.ufcg.si1.model.Produto;
import com.ufcg.si1.model.Relatorio;
import com.ufcg.si1.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    VendasService vendasService;

    public Relatorio geraRelatorio() {
        List<Produto> produtos = produtoService.findAllProdutos();
        List<Venda> vendas = vendasService.findAllVendas();
        double receitaTotal = vendasService.geraReceita();

        return new Relatorio(produtos, vendas, receitaTotal);
    }

}
