package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.ItemVenda;
import com.ufcg.si1.model.Venda;
import com.ufcg.si1.repository.VendaRepository;

@Service("vendasService")
public class VendasService {

	@Autowired
	private VendaRepository vendaRepository;
	
	public void registraVenda(Venda venda) {
		this.validaVenda(venda);
		
	}
	
	private void validaVenda(Venda venda) {
		List<ItemVenda> itensVenda = venda.getItens();
	}
	
	
	
	

}
