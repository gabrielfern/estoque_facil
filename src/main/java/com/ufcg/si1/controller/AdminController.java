package com.ufcg.si1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Data;
import com.ufcg.si1.model.Categoria;
import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.Produto;
import com.ufcg.si1.model.Relatorio;
import com.ufcg.si1.model.Venda;
import com.ufcg.si1.service.CategoriaService;
import com.ufcg.si1.service.ProdutoService;
import com.ufcg.si1.service.VendasService;
import com.ufcg.si1.service.RelatorioService;


@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private VendasService vendasService;

	@Autowired
    private RelatorioService relatorioService;

	@Autowired
	private CategoriaService categoriaService;


	@RequestMapping(value = "/produtos", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
		if (produtoService.doesProdutoExist(produto)) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}

		categoriaService.certificaCategoria(produto);
		produtoService.criaProduto(produto);

		return new ResponseEntity<Produto>(HttpStatus.CREATED);
	}


	@RequestMapping(value = "/produtos/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") Integer id, @RequestBody Produto produto) {
		if(produtoService.doesProdutoExist(id)) {
			produto.mudaId(id);
			categoriaService.certificaCategoria(produto);
			produtoService.updateProduto(produto);
			return new ResponseEntity<Produto>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	@RequestMapping(value = "/produtos/{id}/lotes", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") Integer produtoId, @RequestBody Lote lote) {

		if (!produtoService.doesProdutoExist(produtoId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		produtoService.saveLote(produtoId, lote);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}


	@RequestMapping(value= "/vendas", method = RequestMethod.GET)
	public ResponseEntity<?> getHistoricoVendas() {
		return new ResponseEntity<>(vendasService.findAllVendas(), HttpStatus.OK);
	}


	@RequestMapping(value= "/vendas", method = RequestMethod.POST)
	public ResponseEntity<?> registraVenda(@RequestBody Venda venda) {
		boolean vendaBemSucedida = vendasService.realizaVenda(venda);
		if(!vendaBemSucedida)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(HttpStatus.OK);

	}

    @RequestMapping(value= "/relatorio", method = RequestMethod.GET)
    public ResponseEntity<?> geraRelatorio() {
        Relatorio relatorio = relatorioService.geraRelatorio();
        return new ResponseEntity<>(relatorio, HttpStatus.OK);
    }


	@RequestMapping(value= "/categorias", method = RequestMethod.GET)
	public ResponseEntity<?> getCategorias(@RequestParam String senha) {
	    return new ResponseEntity<>(categoriaService.getCategorias(), HttpStatus.OK);
	}


	@RequestMapping(value= "/categorias/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategoria(@PathVariable Integer id,	@RequestBody Categoria categoria) {

        if (categoriaService.hasCategoria(id)) {
            categoriaService.saveCategoria(id, categoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}


	@RequestMapping(value = "/produtos/falta", method = RequestMethod.GET)
	public ResponseEntity<?> produtosEmFalta(@RequestParam(defaultValue = "15") int qtd) {
		return new ResponseEntity<>(produtoService.getProdutosEmFalta(qtd), HttpStatus.OK);
	}


	@RequestMapping(value = "/produtos/vencidos", method = RequestMethod.GET)
	public ResponseEntity<?> produtosVencidos(@RequestParam(defaultValue = "1") int meses) {
		Data data = Data.dataHoje();
		data.addMeses(meses);

		return new ResponseEntity<>(produtoService.getProdutosVencidos(data), HttpStatus.OK);
	}
}
