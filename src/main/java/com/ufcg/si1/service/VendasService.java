package com.ufcg.si1.service;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.ItemVenda;
import com.ufcg.si1.model.Venda;
import com.ufcg.si1.repository.VendaRepository;

@Service
public class VendasService {

	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private ProdutoService produtoService;
	
	public boolean realizaVenda(Venda venda) {
		boolean vendaValida = this.validaVenda(venda);
		
		return vendaValida;
		
	}
	
	private boolean validaVenda(Venda venda) {
		List<ItemVenda> itensVenda = venda.getItens();
		for(ItemVenda itemVenda: itensVenda) {
			Integer idProduct = itemVenda.getProduto().getId();
			int qtdAVender = itemVenda.getQtd();
			if(this.podeVender(idProduct, qtdAVender)) 
				produtoService.abateQtdProdutosLote(idProduct, qtdAVender);
			else
				return false;
		}
		
		return true;
	}
	
	private boolean podeVender(Integer id, int qtdAVender) {
		return produtoService.doesProdutoExist(id) && (produtoService.getQtdProdutosLote(id) >= qtdAVender); 
	}
	
	private void registraVenda(Venda venda) {
		vendaRepository.save(venda);
	}
	
	
	
	

}
