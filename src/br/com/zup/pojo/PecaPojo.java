package br.com.zup.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PecaPojo {

	@Id
	@Column(name = "codigo_de_barras")
	private int codigoDeBarras;

	@Column(nullable = false)
	private String nome;

	@Column(name = "modelo_do_carro", nullable = false)
	private String modeloDoCarro;

	@Column(nullable = false)
	private String fabricante;

	@Column(name = "preço_de_custo", nullable = false)
	private float precoDeCusto;

	@Column(name = "preço_de_venda", nullable = false)
	private float precoDeVenda;

	@Column(name = "quantidade_em_estoque", nullable = false)
	private int quantidadeEmEstoque;

	@Column(nullable = false)
	private String categoria;

	public PecaPojo(int codigoDeBarras, String nome, String modeloDoCarro, String fabricante, float precoDeCusto,
			float precoDeVenda, int quantidadeEmEstoque, String categoria) {
		super();
		this.codigoDeBarras = codigoDeBarras;
		this.nome = nome;
		this.modeloDoCarro = modeloDoCarro;
		this.fabricante = fabricante;
		this.precoDeCusto = precoDeCusto;
		this.precoDeVenda = precoDeVenda;
		this.quantidadeEmEstoque = quantidadeEmEstoque;
		this.categoria = categoria;
	}

	public PecaPojo() {

	}

	public int getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(int codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	@Override
	public String toString() {
		return "\t ----------------------" + "\n\t CÓDIGO DE BARRAS = " + codigoDeBarras + "\n\t NOME = " + nome
				+ "\n\t MODELO DO CARRO = " + modeloDoCarro + "\n\t FABRICANTE = " + fabricante
				+ "\n\t PREÇO DE CUSTO = R$ " + precoDeCusto + "\n\t PREÇO DE VENDA = R$ " + precoDeVenda
				+ "\n\t QUANTIDADE EM ESTOQUE = " + quantidadeEmEstoque + "\n\t CATEGORIA = " + categoria
				+ "\n\t ----------------------";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getModeloDoCarro() {
		return modeloDoCarro;
	}

	public void setModeloDoCarro(String modeloDoCarro) {
		this.modeloDoCarro = modeloDoCarro;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public float getPrecoDeCusto() {
		return precoDeCusto;
	}

	public void setPrecoDeCusto(float preçoDeCusto) {
		this.precoDeCusto = preçoDeCusto;
	}

	public float getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(float preçoDeVenda) {
		this.precoDeVenda = preçoDeVenda;
	}

	public int getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
