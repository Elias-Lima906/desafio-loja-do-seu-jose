package br.com.zup.pojo;

public class LojaDePecas {

	private String nome;
	private String modeloDoCarro;
	private String fabricante;
	private float precoDeCusto;
	private float precoDeVenda;
	private int quantidadeEmEstoque;
	private String categoria;

	public LojaDePecas(String nome, String modeloDoCarro, String fabricante, float precoDeCusto, float precoDeVenda,
			int quantidadeEmEstoque, String categoria) {
		super();
		this.nome = nome;
		this.modeloDoCarro = modeloDoCarro;
		this.fabricante = fabricante;
		this.precoDeCusto = precoDeCusto;
		this.precoDeVenda = precoDeVenda;
		this.quantidadeEmEstoque = quantidadeEmEstoque;
		this.categoria = categoria;
	}

	public LojaDePecas() {

	}

	@Override
	public String toString() {
		return "Peças [nome=" + nome + ", Modelo Do Carro=" + modeloDoCarro + ", Fabricante=" + fabricante
				+ ", Preço De Custo=" + precoDeCusto + ", Preço De Venda=" + precoDeVenda + ", Quantidade Em Estoque="
				+ quantidadeEmEstoque + ", Categoria=" + categoria + "]";
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
