package com.ufcg.si1.model;

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
	private double preco;

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
		this.preco = 0;
		this.lotes = new ArrayList<Lote>();
	}


	public Produto(Integer id, String nome, String codigoBarra, String fabricante,
			Categoria nomeCategoria) {
		this.id = id;
		this.nome = nome;
		this.preco = 0;
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


	public double getPreco() {
		return preco;
	}


	public void setPreco(double preco) {
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
	

	public List<Lote> getLotes() {
		return lotes;
	}


	public void setLotes(List<Lote> lote) {
		this.lotes = lote;
	}
	
	public void saveLote(Lote lote) {
		if (lote.getNumeroDeItens() > 0 && !lote.vencido()) {
			if(this.situacao == Situacao.INDISPONIVEL) {
				this.mudaSituacao(Situacao.DISPONIVEL);
			}
			this.lotes.add(lote);
		}
	}

	public void mudaSituacao(Situacao situacao) {
		this.situacao = situacao;
	}


	public Situacao getSituacao() {
		return this.situacao;
	}


	public int getQtdProdutosDisponiveis() {
		int qtdProdutos = 0;
		this.verificaDisponibilidadeProduto();

		if (this.getSituacao() == Situacao.DISPONIVEL) {
			for(Lote lote: this.getLotes())
				qtdProdutos += lote.getNumeroDeItens();
		}

		return qtdProdutos;
	}


	public void abateQtdProdutosLote(int qtdProdutos) {
		Iterator<Lote> it = this.getLotes().iterator();
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


	public void excluiLotesVencidos() {
		Iterator<Lote> it = this.getLotes().iterator();
		Lote lote;

		while (it.hasNext()) {
			lote = it.next();
			if (lote.vencido())
				it.remove();
		}
	}


	public void verificaDisponibilidadeProduto() {
		this.excluiLotesVencidos();

		if (this.getLotes().size() == 0)
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