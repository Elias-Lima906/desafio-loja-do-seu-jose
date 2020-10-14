package br.com.zup.programaprincipal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.zup.dao.LojaDePecasDAO;
import br.com.zup.pojo.LojaDePecas;

public class ProgramaPrincipal {

	public static void percorrerEImprimirPecasDoEstoque(List<LojaDePecas> pecasDB) {

		for (LojaDePecas peca : pecasDB) {
			System.out.println("\n\t" + peca + "\n");
		}

	}

	public static void menuInterno() {
		System.out.println("\n\tBem Vindo A Oficina De Pe�as Do Jos�!\n");
		System.out.println("\n\t(1) - Para Cadastrar Uma Nova Pe�as;\n" + "\n\t(2) - Para Checkar O Estoque De Pe�as;\n"
				+ "\n\t(3) - Para Remover Uma Pe�a Do Estoque;\n"
				+ "\n\t(4) - Para Buscar Uma Pe�a Pelo C�digo De Barras;\n"
				+ "\n\t(5) - Para Buscar Uma Pe�a Pela Letra Referencia;\n"
				+ "\n\t(6) - Para Buscar Uma Pe�a Pelo Modelo Do Carro;\n"
				+ "\n\t(7) - Para Buscar Pe�as Pela Categoria;\n" + "\n\t(0) - Para Sair;\n");
	}

	public static void gerenciaEstoque(Scanner teclado) throws SQLException {

		menuInterno();

		LojaDePecasDAO pecasDAO = new LojaDePecasDAO();
		int opcaoInterna = 0;
		int codigoDeBarras = 0;

		System.out.print("\n\tOp��o: \n");
		opcaoInterna = teclado.nextInt();

		switch (opcaoInterna) {

		case 1:
			adicionaPecas(teclado, pecasDAO);
			break;

		case 2:
			consultaPecas(teclado, pecasDAO);
			break;

		case 3:
			removePecaPorCodigoDeBarras(teclado, pecasDAO, codigoDeBarras);
			break;

		case 4:
			buscaPecasPorCodigoDeBarras(teclado, pecasDAO, codigoDeBarras);
			break;

		case 5:
			buscaPecasPorLetraReferencia(teclado, pecasDAO);
			break;

		case 6:
			buscaPecasPorModeloDoCarro(teclado, pecasDAO);
			break;

		case 7:
			buscaPecasPorCategoria(teclado, pecasDAO);
			break;
		case 0:
			System.out.println("\n\tMuito Obrigado!\n");
			break;
		default:
			System.out.println("\n\tOp��o Inv�lida, Tente Novamente\n");
		}

	}

	public static void adicionaPecas(Scanner teclado, LojaDePecasDAO pecasDAO) {

		System.out.println("\n\tPreciso Das Seguintes Informa��es!\n");

		System.out.print("\tNome: ");
		String nome = teclado.next();

		System.out.print("\n\tModelo Do Carro: ");
		String modeloDoCarro = teclado.next();

		teclado.next();

		System.out.print("\n\tFabricante: ");
		String fabricante = teclado.next();

		System.out.print("\n\tPre�o De Custo: ");
		float precoDeCusto = teclado.nextFloat();

		System.out.print("\n\tPre�o De Venda: ");
		float precoDeVenda = teclado.nextFloat();

		System.out.print("\n\tQuantidade Em Estoque: ");
		int quantidadeEmEstoque = teclado.nextInt();

		System.out.print("\n\tCategoria: ");
		String categoria = teclado.next();

		try {
			pecasDAO.adicionaPeca(new LojaDePecas(nome, modeloDoCarro, fabricante, precoDeCusto, precoDeVenda,
					quantidadeEmEstoque, categoria));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void consultaPecas(Scanner teclado, LojaDePecasDAO pecasDAO) {
		List<LojaDePecas> pecasDB = new ArrayList<LojaDePecas>();
		try {
			pecasDB = pecasDAO.consultaPecas();
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void removePecaPorCodigoDeBarras(Scanner teclado, LojaDePecasDAO pecasDAO, int codigoDeBarras)
			throws SQLException {
		System.out.println("\n\tDigite o Cep Da Cidade A Ser Removida!\n");
		System.out.print("\tC�digo De Barras: ");
		codigoDeBarras = teclado.nextInt();
		pecasDAO.removePecaPorCodigoDeBarras(codigoDeBarras);
	}

	public static void buscaPecasPorCodigoDeBarras(Scanner teclado, LojaDePecasDAO pecasDAO, int codigoDeBarras) {
		System.out.println("\n\tDigite o Cep Da Cidade A Ser Buscada!\n");
		System.out.print("\tC�digo De Barras: ");
		codigoDeBarras = teclado.nextInt();
		List<LojaDePecas> pecasDB = new ArrayList<LojaDePecas>();
		try {
			pecasDB = pecasDAO.buscaPecasPorCodigoDeBarras(codigoDeBarras);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void buscaPecasPorLetraReferencia(Scanner teclado, LojaDePecasDAO pecasDAO) {
		System.out.println("\n\tDigite a Letra Referencia Da(s) Pe�a(s) A Ser(em) Buscada!\n");
		System.out.print("\tLetra Refencia: ");
		String letraRefencia = teclado.next();
		List<LojaDePecas> pecasDB = new ArrayList<LojaDePecas>();
		try {
			pecasDB = pecasDAO.buscaPecasPorLetraReferencia(letraRefencia);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void buscaPecasPorModeloDoCarro(Scanner teclado, LojaDePecasDAO pecasDAO) {
		System.out.println("\n\tDigite O Modelo Do Carro Pra Buscar Pe�as Referentes!\n");
		System.out.print("\tModelo Do Carro: ");
		String modeloDoCarro = teclado.next();
		List<LojaDePecas> pecasDB = new ArrayList<LojaDePecas>();
		try {
			pecasDB = pecasDAO.buscaPecasPorModeloDoCarro(modeloDoCarro);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void buscaPecasPorCategoria(Scanner teclado, LojaDePecasDAO pecasDAO) {
		System.out.println("\n\tDigite Uma Das Categoria Pra Buscar Pe�as Referentes!\n");
		System.out.print("\tCategoria: ");
		String categoria = teclado.next();
		List<LojaDePecas> pecasDB = new ArrayList<LojaDePecas>();
		try {
			pecasDB = pecasDAO.buscaPecasPorCategoria(categoria);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws SQLException {

		Scanner teclado = new Scanner(System.in);
		int opcaoExterna = 0;

		System.out.println("\n\t(1) - Para Gerenciar Pe�as Do Estoque;\n");
		System.out.println("\n\t(2) - Para Gerenciar Vendas;\n");
		System.out.println("\n\t(0) - Para Sair;\n");

		System.out.print("\n\tOp��o: \n");

		opcaoExterna = teclado.nextInt();

		do {

			switch (opcaoExterna) {

			case 1:
				gerenciaEstoque(teclado);
				break;

			case 2:
				// Vendas
				break;

			}

		} while (opcaoExterna == 0);
		teclado.close();
	}
}
