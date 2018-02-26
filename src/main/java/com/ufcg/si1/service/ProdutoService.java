package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.Produto;
import com.ufcg.si1.repository.ProdutoRepository;

@Service("produtoService")
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;

	public List<Produto> findAllProdutos() {
		return produtoRepository.findAll();
	}

	public void saveProduto(Produto produto) {
		produtoRepository.save(produto);
	}

	public void updateProduto(Produto produto) {
		if(produto.getSituacao() == null) {
			Produto oldProduct = produtoRepository.getOne(produto.getId());
			produto.mudaSituacao(oldProduct.getSituacao());
		}
		produtoRepository.save(produto);
	}

	public void deleteProdutoById(Integer id) {
		produtoRepository.delete(id);
	}

	public long size() {
		return produtoRepository.count();
	}

	public void deleteAllProducts() {
		produtoRepository.deleteAll();
	}

	public Produto findById(Integer id) {
		return produtoRepository.findOne(id);
	}

	public boolean doesProdutoExist(Produto produto) {
		return produtoRepository.hasProduct(produto.getCodigoBarra());
	}
	
	public boolean doesProdutoExist(Integer id) {
		return produtoRepository.exists(id);
	}
	
	public Lote saveLote(Integer idProduct, Lote lote) {
		Produto productToAddLote = produtoRepository.findOne(idProduct);
		productToAddLote.saveLote(lote);
		produtoRepository.save(productToAddLote);
		return lote;
	}

	public List<Lote> getLotesProduto(Integer produtoId) {
		Produto produto = produtoRepository.getOne(produtoId);
		return produto.getLote();
	}
	
	public int getQtdProdutosLote(Integer id) {
		Produto produto = produtoRepository.getOne(id);
		return produto.getQtdProdutosDisponiveis();
	}
	
	public void abateQtdProdutosLote(Integer produtoId, int qtd) {
		produtoRepository.getOne(produtoId).abateQtdProdutosLote(qtd);
	}

	public double calculaPreco(Integer idProduct, int qtdAVender) {
		Produto produto = produtoRepository.getOne(idProduct);
		int PORCENTAGEM_TOTAL = 1;
		double porcentagemfinalproduto = PORCENTAGEM_TOTAL - produto.getDescontoCategoria();
		return (produto.getPreco().doubleValue() * porcentagemfinalproduto) * qtdAVender;
	}

	public String getDetalhesProduto(Integer idProduct) {
		Produto produto = produtoRepository.getOne(idProduct);
		return "Nome: " + produto.getNome() + ", Fabricante: " + produto.getFabricante();
		
	}
	
}
