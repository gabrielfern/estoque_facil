package com.ufcg.si1.service;

import java.util.List;

import com.ufcg.si1.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Categoria;
import com.ufcg.si1.repository.CategoriaRepository;


@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;


	public List<Categoria> getCategorias() {
		return categoriaRepository.findAll();
	}


	public void saveCategoria(Integer id, Categoria categoria) {
		categoria.setId(id);
		categoriaRepository.save(categoria);
	}


	public Categoria getCategoria(Integer id) {
		return categoriaRepository.getOne(id);
	}


	public boolean hasCategoria(Integer id) {
		return categoriaRepository.findOne(id) != null;
	}

	public List<Produto> getProdutosCategoria(Integer id) {
		Categoria categoria = categoriaRepository.findOne(id);
		return categoria.pegaProdutosCategoria();
	}
}