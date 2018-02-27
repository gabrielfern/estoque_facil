package com.ufcg.si1.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

import com.ufcg.si1.model.enums.Situacao;


@Entity
@Table(name="produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	private String nome;

	@Column
	private BigDecimal preco;

	@Column
	private String codigoBarra;

	@Column
	private String fabricante;

	@ManyToOne(cascade = CascadeType.ALL)
	private Categoria categoria;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Lote> lotes;

	@Column
	@Enumerated
	private Situacao situacao;


	public Produto() {
		this.preco = new BigDecimal(0);
		this.lotes = new ArrayList<Lote>();
	}


	public Produto(Integer id, String nome, String codigoBarra, String fabricante,
			Categoria nomeCategoria) {
		this.id = id;
		this.nome = nome;
		this.preco = new BigDecimal(0);
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.categoria = nomeCategoria;
		this.situacao = Situacao.INDISPONIVEL;

		this.lotes = new ArrayList<Lote>();
	}


	public String getNome() {
		return nome;
	}


	public void mudaNome(String nome) {
		this.nome = nome;
	}


	public BigDecimal getPreco() {
		return preco;
	}


	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}


	public Integer getId() {
		return id;
	}


	public void mudaId(Integer codigo) {
		this.id = codigo;
	}


	public String getFabricante() {
		return fabricante;
	}


	public void mudaFabricante(String fabricante) {
		this.fabricante = fabricante;
	}


	public String getCodigoBarra() {
		return codigoBarra;
	}


	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}


	public Categoria getCategoria() {
		return this.categoria;
	}


	public void mudaCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	

	public List<Lote> getLote() {
		return lotes;
	}


	public void setLote(List<Lote> lote) {
		this.lotes = lote;
	}
	
	public void saveLote(Lote lote) {
		
		if(this.getSituacao() == Situacao.INDISPONIVEL) {
			if (lote.getNumeroDeItens() > 0) {
				this.mudaSituacao(Situacao.DISPONIVEL);
			}
		}
		
		this.lotes.add(lote);		
	}

	public void mudaSituacao(Situacao situacao) {
		this.situacao = situacao;
	}


	public Situacao getSituacao() {
		return this.situacao;
	}


	public int qtdProdutosDisponiveis() {
		
		int qtdProdutos = 0;
		if(this.getSituacao() == Situacao.DISPONIVEL) {
			for(Lote lote: this.getLote())
				qtdProdutos += lote.getNumeroDeItens();
		}
		return qtdProdutos;
	}
	
	public void abateQtdProdutosLote(int qtdProdutos) {
		Iterator<Lote> it = this.getLote().iterator();
		while(it.hasNext() && qtdProdutos > 0) {
			Lote lote = it.next();
			if(lote.getNumeroDeItens() <= qtdProdutos) {
				qtdProdutos -= lote.getNumeroDeItens();
				it.remove();
			} else {
				lote.setNumeroDeItens(lote.getNumeroDeItens() - qtdProdutos);
				qtdProdutos = 0;
			}
		}
		if(this.getSituacao() == Situacao.DISPONIVEL)
			verificaDisponibilidadeProduto();
	}

	private void verificaDisponibilidadeProduto() {
		if(this.lotes.size() == 0)
			this.mudaSituacao(Situacao.INDISPONIVEL);
	}


	public double descontoCategoria() {
		return this.getCategoria().getDesconto();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + ", codigoBarra=" + codigoBarra
				+ ", fabricante=" + fabricante + ", categoria=" + categoria + "]";
	}
}