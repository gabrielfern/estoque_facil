package com.ufcg.si1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Produto;
import com.ufcg.si1.service.ProdutoService;
import com.ufcg.si1.service.ProdutoServiceImpl;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestApiController {

	ProdutoService produtoService = new ProdutoServiceImpl();


	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> getProdutos() {
		return new ResponseEntity<>(produtoService.findAllProdutos(), HttpStatus.OK);
	}


	@RequestMapping(value = "/produto", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) throws Exception {

		if (produtoService.doesProdutoExist(produto)) {
			return new ResponseEntity<>(new Exception("O produto " + produto.getNome() + " do fabricante "
					+ produto.getFabricante() + " ja esta cadastrado!"), HttpStatus.CONFLICT);
		}

		produto.mudaSituacao(Produto.INDISPONIVEL);
		produtoService.saveProduto(produto);

		return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {

		Produto produtoProcurado = produtoService.findById(id);

		if (produtoProcurado == null) {
			return new ResponseEntity<>(new Exception("Produto with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Produto>(produtoProcurado, HttpStatus.OK);
	}


	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {

	    if(produtoService.doesProdutoExist(id)) {
            produtoService.updateProduto(produto);
            return new ResponseEntity<Produto>(produto, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Exception("Unable to upate. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
	}


	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {

		if (!produtoService.doesProdutoExist(id)) {
			return new ResponseEntity<>(new Exception("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		produtoService.deleteProdutoById(id);

		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}
}
