package com.ufcg.si1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.Produto;
import com.ufcg.si1.model.enums.Situacao;
import com.ufcg.si1.service.ProdutoService;


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> getProdutos() {
		return new ResponseEntity<>(produtoService.findAllProdutos(), HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduto(@PathVariable("id") Integer id) {

		Produto produtoProcurado = produtoService.findById(id);

		if (produtoProcurado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Produto>(produtoProcurado, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> getLotesProduto(@PathVariable("id") Integer produtoId) {
		
		if (!produtoService.doesProdutoExist(produtoId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Lote> lotesProduto = produtoService.getLotesProduto(produtoId);

		return new ResponseEntity<>(lotesProduto, HttpStatus.CREATED);
	}
}
