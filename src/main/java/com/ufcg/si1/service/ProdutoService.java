package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Data;
import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.Produto;
import com.ufcg.si1.model.enums.Situacao;
import com.ufcg.si1.repository.ProdutoRepository;


@Service("produtoService")
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;


	public List<Produto> getProdutos() {
		this.atualizaDisponibilidadeProdutos();
		return produtoRepository.findAll();
	}
	
	
	private void atualizaDisponibilidadeProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		for (Produto produto: produtos)
			produto.verificaDisponibilidadeProduto();

		produtoRepository.save(produtos);
	}


	public void saveProduto(Produto produto) {
		produtoRepository.save(produto);
	}


	public void updateProduto(Produto produto) {
		if(produto.getSituacao() == null) {
			Produto oldProduct = produtoRepository.getOne(produto.getId());
			produto.mudaSituacao(oldProduct.getSituacao());
		}

		produto.verificaDisponibilidadeProduto();
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


	public Produto getProduto(Integer id) {
		Produto produto = produtoRepository.findOne(id);
		if (produto != null)
			produto.verificaDisponibilidadeProduto();
		
		produtoRepository.save(produto);
		return produto;
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
		produto.verificaDisponibilidadeProduto();
		return produto.getLotes();
	}
	

	public int getQtdProdutosLote(Integer id) {
		return produtoRepository.getOne(id).getQtdProdutosDisponiveis();
	}
	

	public void abateQtdProdutosLote(Integer produtoId, int qtd) {
		Produto produto = produtoRepository.getOne(produtoId);
		produto.abateQtdProdutosLote(qtd);
		produtoRepository.save(produto);
	}


	public double calculaPreco(Integer idProduto, int qtdAVender) {
		Produto produto = produtoRepository.getOne(idProduto);

		double porcentagemFinalProduto = 1 - produto.descontoCategoria()/100;
		return produto.getPreco()*porcentagemFinalProduto * qtdAVender;
	}


	public String getDetalhesProduto(Integer idProduct) {
		Produto produto = produtoRepository.getOne(idProduct);
		return "Nome: " + produto.getNome() + ", Fabricante: " + produto.getFabricante();
	}


	public List<Produto> getProdutosEmFalta(int qtd) {
		List<Produto> produtos = produtoRepository.findAll();
		List<Produto> produtosEmFalta = new ArrayList<>();

		for (Produto produto: produtos) {
			if (produto.getQtdProdutosDisponiveis() < qtd)
				produtosEmFalta.add(produto);
		}

		return produtosEmFalta;
	}


	private boolean verificaProdutoVencido(Data data, Produto produto) {
		boolean vencido = false;

		List<Lote> lotes = produto.getLotes();
		for (Lote lote: lotes) {
			if (data.compareTo(lote.getDataDeValidade()) > 0) {
				vencido = true;
				break;
			}
		}

		return vencido;
	}


	public List<Produto> getProdutosVencidos(Data data) {
		List<Produto> produtos = produtoRepository.findAll();
		List<Produto> produtosVencidos = new ArrayList<>();

		for (Produto produto: produtos) {
			if (verificaProdutoVencido(data, produto))
				produtosVencidos.add(produto);
		}

		return produtosVencidos;
	}


	public void criaProduto(Produto produto) {
		produto.mudaSituacao(Situacao.INDISPONIVEL);
		produtoRepository.save(produto);
	}
}
