package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Produto;


@Service("produtoService")
public class ProdutoService {

	private static final AtomicLong counter = new AtomicLong();

	private List<Produto> produtos;


	public ProdutoService() {
		this.produtos = new ArrayList<>();
	}


	public List<Produto> findAllProdutos() {
		return produtos;
	}


	public void saveProduto(Produto produto) {
		produto.mudaId(counter.incrementAndGet());
		produtos.add(produto);
	}


	public void updateProduto(Produto produto) {
		int index = produtos.indexOf(produto);
		produtos.set(index, produto);
	}


	public void deleteProdutoById(long id) {

		for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
			Produto product = iterator.next();
			if (product.getId() == id) {
				iterator.remove();
			}
		}
	}


	public int size() {
		return produtos.size();
	}


	public Iterator<Produto> getIterator() {
		return produtos.iterator();
	}


	public void deleteAllUsers() {
		produtos.clear();
	}


	public Produto findById(long id) {
		for (Produto produto : produtos) {
			if (produto.getId() == id) {
				return produto;
			}
		}
		return null;
	}


	public boolean doesProdutoExist(Produto produto) {
		for (Produto product : produtos) {
			if (product.getCodigoBarra().equals(produto.getCodigoBarra())) {
				return true;
			}
		}
		return false;
	}


	public boolean doesProdutoExist(long id) {
		for (Produto product : produtos) {
			if (product.getId() == id) {
				return true;
			}
		}
		return false;
	}
}
