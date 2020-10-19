package br.com.zup.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.zup.pojo.LojaDePecas;

public class LojaDePecasDAO {

	EntityManager manager;

	public LojaDePecasDAO() {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("carros");
		this.manager = managerFactory.createEntityManager();

	}

	public void adicionaPeca(LojaDePecas peca) throws SQLException {

		manager.getTransaction().begin();
		manager.persist(peca);
		manager.getTransaction().commit();

	}

	public List<LojaDePecas> consultaPecas() throws EstoqueDeCarrosException {

		Query query = manager.createQuery("select c from LojaDePecas as c");

		List<LojaDePecas> pecasDB = query.getResultList();

		if (pecasDB != null) {
			return pecasDB;
		} else {
			throw new EstoqueDeCarrosException("\n\tN�O H� PE�AS NO BANCO DE DADOS!\n");
		}

	}

	public void removePecaPorCodigoDeBarras(int codigoDeBarras) throws EstoqueDeCarrosException {

		// - remover uma pe�a do estoque;
		LojaDePecas peca = manager.find(LojaDePecas.class, codigoDeBarras);

		if (peca != null) {

			manager.getTransaction().begin();
			manager.remove(peca);
			manager.getTransaction().commit();
		} else {
			throw new EstoqueDeCarrosException("\n\tPE�A N�O ENCONTRADA PELO C�DIGO DE BARRAS!\n");
		}

	}

	public List<LojaDePecas> buscaPecasPorCodigoDeBarras(int codigoDeBarras) throws SQLException {

		// - consultar uma pe�a pelo seu c�digo de barras;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where codigo_de_barras = ?";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setInt(1, codigoDeBarras);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tC�DIGO DE BARRAS N�O ENCONTRADO!\n");
		}
	}

	public List<LojaDePecas> buscaPecasPorLetraReferencia(String letraReferencia) throws SQLException {

		// - listar todas as pe�as come�adas com o nome come�ado por um determinado
		// texto;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where nome like ? ;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setString(1, letraReferencia + '%');

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tPE�A N�O ENCONTRADA PELA LETRA REFER�NCIA!\n");
		}
	}

	public List<LojaDePecas> buscaPecasPorModeloDoCarro(String modeloDoCarro) throws SQLException {

		// - listar todas as pe�as para um determinado modelo de carro ;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where modelo_do_carro = ? ;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setString(1, modeloDoCarro);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tN�O H� PE�AS DO MODELO DESTE CARRO NO BANCO DE DADOS!\n");
		}
	}

	public List<LojaDePecas> buscaPecasPorCategoria(String categoria) throws SQLException {

		// - listar todas as pe�as de uma determinada categoria;

		List<LojaDePecas> pecas = new ArrayList<LojaDePecas>();

		String sqlConsulta = "select * from estoque_de_pecas where categoria = ? ;";

		PreparedStatement stmt = connection.prepareStatement(sqlConsulta);

		stmt.setString(1, categoria);

		pecas = gereciaConsultasNoBancoDeDados(pecas, stmt);

		if (pecas != null) {
			return pecas;

		} else {
			throw new SQLException("\n\tN�O H� PE�AS DESSA CATEGORIA NO BANCO DE DADOSS!\n");
		}
	}

	public float vendaDePecas(int qtdPecasAComprar, int codigoDeBarras) throws SQLException, IOException {

		int totalDePecas = 0;
		int qtdPecasRestantes = 0;
		float valorDaPeca = 0;
		String nomeDaPeca = "";

		PreparedStatement stmt;

		String sqlConsulta = "select nome, quantidade_em_estoque, pre�o_de_venda from estoque_de_pecas where codigo_de_barras = ?;";

		stmt = connection.prepareStatement(sqlConsulta);

		stmt.setInt(1, codigoDeBarras);

		ResultSet rs = stmt.executeQuery();

		if (rs != null && rs.next()) {
			totalDePecas = rs.getInt("quantidade_em_estoque");

			valorDaPeca = rs.getFloat("pre�o_de_venda");

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
			throw new SQLException("\n\tN�O H� PE�AS SUFICIENTES NO ESTOQUE!\n");
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
				throw new IOException("N�O H� VENDAS NESTE DIA!");
			}
		}

		leitor.close();
		estrutura.close();

	}
}
