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
@RequestMapping("api/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value="s", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> getProdutos() {
		return new ResponseEntity<>(produtoService.findAllProdutos(), HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) throws Exception {

		if (produtoService.doesProdutoExist(produto)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		produto.mudaSituacao(Situacao.INDISPONIVEL);
		produtoService.saveProduto(produto);

		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") Integer id) {

		Produto produtoProcurado = produtoService.findById(id);

		if (produtoProcurado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Produto>(produtoProcurado, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") Integer id, @RequestBody Produto produto) {

	    if(produtoService.doesProdutoExist(id)) {
	    	produto.mudaId(id);
            produtoService.updateProduto(produto);
            return new ResponseEntity<Produto>(produto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {

		if (!produtoService.doesProdutoExist(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produtoService.deleteProdutoById(id);

		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") Integer produtoId, @RequestBody Lote lote) {
		
		if (!produtoService.doesProdutoExist(produtoId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		produtoService.saveLote(produtoId, lote);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}/lote", method = RequestMethod.GET)
	public ResponseEntity<?> getLotesProduto(@PathVariable("id") Integer produtoId) {
		
		if (!produtoService.doesProdutoExist(produtoId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Lote> lotesProduto = produtoService.getLotesProduto(produtoId);

		return new ResponseEntity<>(lotesProduto, HttpStatus.CREATED);
	}


}
