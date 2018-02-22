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
import com.ufcg.si1.util.CustomErrorType;

import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	ProdutoService produtoService = new ProdutoServiceImpl();

	// -------------------Retrieve All
	// Products---------------------------------------------

	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listAllUsers() {
		List<Produto> produtos = produtoService.findAllProdutos();

		if (produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	// -------------------Criar um
	// Produto-------------------------------------------

	@RequestMapping(value = "/produto", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) throws ObjetoInvalidoException{

		if (produtoService.doesProdutoExist(produto)) {
			return new ResponseEntity<>(new CustomErrorType("O produto " + produto.getNome() + " do fabricante "
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
			return new ResponseEntity<>(new CustomErrorType("Produto with id " + id + " not found"),
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

        return new ResponseEntity<>(new CustomErrorType("Unable to upate. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {

		if (!produtoService.doesProdutoExist(id)) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Produto with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		produtoService.deleteProdutoById(id);
		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}
}
