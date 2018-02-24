package com.ufcg.si1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ufcg.si1.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	  @Query(value="Select produto from Produto produto where produto.categoria=:categorianome")
	  List<Produto> getProdutosCategoria(@Param("categorianome") String categoria);
	  
	  
	  @Query(value="SELECT CASE WHEN COUNT(produto) > 0 THEN true ELSE false END FROM Produto produto WHERE produto.codigoBarra=:codigo")
	  boolean hasProduct(@Param("codigo") String codigobarras);
	  
}
