package br.com.zup.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.zup.connectionfactory.ConnectionFactory;
import br.com.zup.pojo.LojaDePecas;

public class LojaDePecasDAO {

	private List<LojaDePecas> gereciaConsultasNoBancoDeDados(List<LojaDePecas> pecas, PreparedStatement stmt)
			throws SQLException {

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			LojaDePecas peca = new LojaDePecas();

			peca.setCodigoDeBarras(rs.getInt("codigo_de_barras"));
			peca.setNome(rs.getString("nome"));
			peca.setModeloDoCarro(rs.getNString("modelo_do_carro"));
			peca.setFabricante(rs.getString("fabricante"));
			peca.setPrecoDeCusto(rs.getFloat("preço_de_custo"));
			peca.setPrecoDeVenda(rs.getFloat("preço_de_venda"));
			peca.setQuantidadeEmEstoque(rs.getInt("quantidade_em_estoque"));
			peca.setCategoria(rs.getString("categoria"));

			pecas.add(peca);

		}
		return pecas;
	}

	private Connection connection;

	public LojaDePecasDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adicionaPeca(LojaDePecas peca) throws SQLException {

		// - cadastrar uma nova peça;

		String sqlInsert = "insert into estoque_de_pecas "
				+ "(nome, modelo_do_carro, fabricante, preço_de_custo, preço_de_venda, quantidade_em_estoque, categoria) "
				+ "values (?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement stmt = connection.prepareStatement(sqlInsert);
		stmt.setString(1, peca.getNome());
		stmt.setString(2, peca.getModeloDoCarro());
		stmt.setString(3, peca.getFabricante());
		stmt.setFloat(4, peca.getPrecoDeCusto());
		stmt.setFloat(5, peca.getPrecoDeVenda());
		stmt.setInt(6, peca.getQuantidadeEmEstoque());
		stmt.setString(7, peca.getCategoria());

		stmt.execute();
		stmt.close();
	}

	public List<LojaDePecas> consultaPecas() throws SQLException {

		// - listar todas as peças em estoque;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tNÃO HÁ PEÇAS NO BANCO DE DADOS!\n");
		}
	}

	public void removePecaPorCodigoDeBarras(int codigoDeBarras) throws SQLException {

		// - remover uma peça do estoque;

		String sqlConsulta = "delete from estoque_de_pecas where codigo_de_barras = ?;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setInt(1, codigoDeBarras);

		stmt.execute();

	}

	public List<LojaDePecas> buscaPecasPorCodigoDeBarras(int codigoDeBarras) throws SQLException {

		// - consultar uma peça pelo seu código de barras;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where codigo_de_barras = ?";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setInt(1, codigoDeBarras);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tCÓDIGO DE BARRAS NÃO ENCONTRADO!\n");
		}
	}

	public List<LojaDePecas> buscaPecasPorLetraReferencia(String letraReferencia) throws SQLException {

		// - listar todas as peças começadas com o nome começado por um determinado
		// texto;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where nome like ? ;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setString(1, letraReferencia + '%');

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tPEÇA NÃO ENCONTRADA PELA LETRA REFERÊNCIA!\n");
		}
	}

	public List<LojaDePecas> buscaPecasPorModeloDoCarro(String modeloDoCarro) throws SQLException {

		// - listar todas as peças para um determinado modelo de carro ;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where codigo_de_barras = ? ;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setString(1, modeloDoCarro);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tNÃO HÁ PEÇAS DO MODELO DESTE CARRO NO BANCO DE DADOS!\n");
		}
	}

	public List<LojaDePecas> buscaPecasPorCategoria(String categoria) throws SQLException {

		// - listar todas as peças de uma determinada categoria;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where categoria = ? ;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setString(1, categoria);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tNÃO HÁ PEÇAS DESSA CATEGORIA NO BANCO DE DADOSS!\n");
		}
	}

	public float vendaDePecas(int qtdPecasAComprar, int codigoDeBarras) throws SQLException, IOException {

		int totalDePecas = 0;
		int qtdPecasRestantes = 0;
		float valorDaPeca = 0;
		String nomeDaPeca = "";

		PreparedStatement stmt;

		String sqlConsulta = "select nome, quantidade_em_estoque, preço_de_venda from estoque_de_pecas where codigo_de_barras = ?;";

		stmt = connection.prepareStatement(sqlConsulta);

		stmt.setInt(1, codigoDeBarras);

		ResultSet rs = stmt.executeQuery();

		if (rs != null && rs.next()) {
			totalDePecas = rs.getInt("quantidade_em_estoque");

			valorDaPeca = rs.getFloat("preço_de_venda");

			nomeDaPeca = rs.getString("nome");
		}

		if (totalDePecas > qtdPecasAComprar) {
			FileWriter escrita = new FileWriter("estoque.txt", true);
			qtdPecasRestantes = totalDePecas - qtdPecasAComprar;
			String sqlUptade = "update estoque_de_pecas set quantidade_em_estoque = ? where codigo_de_barras = ?;";

			stmt = connection.prepareStatement(sqlUptade);

			stmt.setInt(1, qtdPecasRestantes);
			stmt.setInt(2, codigoDeBarras);

			stmt.execute();
			stmt.close();

			float valorDaCompra = valorDaPeca * qtdPecasAComprar;

			escrita.append(String.format("\n%d\t\t%s\t\t%d\t\tR$ %.2f", codigoDeBarras, nomeDaPeca, qtdPecasAComprar,
					valorDaCompra));
			escrita.close();

			return valorDaCompra;
		} else {
			throw new SQLException("\n\tNÃO HÁ PEÇAS SUFICIENTES NO ESTOQUE!\n");
		}
	}

	@SuppressWarnings("unused")
	public void imprimeRelatorioDeVendas() throws IOException {
		FileReader estrutura = new FileReader("estoque.txt");
		BufferedReader leitor = new BufferedReader(estrutura);

		String lerLinhas;

		while ((lerLinhas = leitor.readLine()) != null) {
			if (lerLinhas != null) {
				System.out.println("\n\t" + lerLinhas);
			} else {
				throw new IOException("NÃO HÁ VENDAS NESTE DIA!");
			}
		}

		leitor.close();
		estrutura.close();

	}
}
