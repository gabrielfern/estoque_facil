package com.ufcg.si1.service;

import java.util.List;

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
	private ProdutoService 	produtoService;
	
	public boolean realizaVenda(Venda venda) {
		boolean vendaValida = this.validaVenda(venda);
		
		if(vendaValida) {
			List<ItemVenda> itensVenda = venda.getItens();
			double precoTotal = 0.0;
			for(ItemVenda itemVenda: itensVenda) {
				Integer idProduct = itemVenda.pegaProduto().getId();
				int qtdAVender = itemVenda.getQtd();
				precoTotal += produtoService.calculaPreco(idProduct, qtdAVender);
				produtoService.abateQtdProdutosLote(idProduct, qtdAVender);
				itemVenda.setDetalhesProduto(produtoService.getDetalhesProduto(idProduct));
				itemVenda.setIdProduto(idProduct);
			}
			venda.setValorTotal(precoTotal);
			this.registraVenda(venda);
		}
		
		return vendaValida;
		
	}
	
	private boolean validaVenda(Venda venda) {
		List<ItemVenda> itensVenda = venda.getItens();
		for(ItemVenda itemVenda: itensVenda) {
			Integer idProduct = itemVenda.pegaProduto().getId();
			int qtdAVender = itemVenda.getQtd();
			if(!this.podeVender(idProduct, qtdAVender)) 
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

	public List<Venda> findAllVendas() {
		return vendaRepository.findAll();
	}

	public double geraReceita() {
		List<Venda> registroVendas = this.findAllVendas();

		double receitaTotal = 0.0;
		for(Venda venda: registroVendas)
			receitaTotal += venda.getValorTotal();
		return receitaTotal;
	}


}
