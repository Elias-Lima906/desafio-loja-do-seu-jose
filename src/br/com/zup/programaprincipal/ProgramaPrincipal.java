package br.com.zup.programaprincipal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.zup.estoqueexcepition.EstoqueDeCarrosException;
import br.com.zup.pecadao.PecaDAO;
import br.com.zup.pojo.PecaPojo;
import br.com.zup.vendadao.VendaDAO;

public class ProgramaPrincipal {

	public static void percorrerEImprimirPecasDoEstoque(List<PecaPojo> pecasDB) {

		for (PecaPojo peca : pecasDB) {
			System.out.println("\n" + peca + "\n");
		}

	}

	public static void menuInterno() {
		System.out.println("\n\tBem Vindo A Oficina De Peças Do José!\n");
		System.out.println("\n\t(1) - Para Cadastrar Uma Nova Peças;\n" + "\n\t(2) - Para Checkar O Estoque De Peças;\n"
				+ "\n\t(3) - Para Remover Uma Peça Do Estoque;\n"
				+ "\n\t(4) - Para Buscar Uma Peça Pelo Código De Barras;\n"
				+ "\n\t(5) - Para Buscar Uma Peça Pela Letra Referencia;\n"
				+ "\n\t(6) - Para Buscar Uma Peça Pelo Modelo Do Carro;\n"
				+ "\n\t(7) - Para Buscar Peças Pela Categoria;\n" + "\n\t(0) - Para Voltr Ao Menu Principal;\n");
	}

	public static void gerenciaEstoque(Scanner teclado, PecaDAO pecasDAO, PecaPojo pecasDB) throws SQLException {

		int opcaoInterna;
		int codigoDeBarras = 0;

		do {
			menuInterno();

			System.out.print("\n\tOpção: ");
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
				buscaPecasPorCodigoDeBarras(teclado, pecasDAO, pecasDB, codigoDeBarras);
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
				break;
			default:
				System.out.println("\n\tOpção Inválida, Tente Novamente\n");
			}
		} while (opcaoInterna != 0);
	}

	public static void adicionaPecas(Scanner teclado, PecaDAO pecasDAO) {

		System.out.println("\n\tPreciso Das Seguintes Informações!\n");

		System.out.print("\tCódigo De Barras: ");
		int codigoDeBarras = teclado.nextInt();

		System.out.print("\tNome: ");
		String nome = teclado.next();

		System.out.print("\n\tModelo Do Carro: ");
		String modeloDoCarro = teclado.next();

		teclado.next();

		System.out.print("\n\tFabricante: ");
		String fabricante = teclado.next();

		System.out.print("\n\tPreço De Custo: ");
		float precoDeCusto = teclado.nextFloat();

		System.out.print("\n\tPreço De Venda: ");
		float precoDeVenda = teclado.nextFloat();

		System.out.print("\n\tQuantidade Em Estoque: ");
		int quantidadeEmEstoque = teclado.nextInt();

		System.out.print("\n\tCategoria: ");
		String categoria = teclado.next();

		try {
			PecaPojo peca = new PecaPojo(codigoDeBarras, nome, modeloDoCarro, fabricante, precoDeCusto, precoDeVenda,
					quantidadeEmEstoque, categoria);
			pecasDAO.adicionaPeca(peca);
			System.out.println("\n\tPeça Adicionada Com Sucesso!\n");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void consultaPecas(Scanner teclado, PecaDAO pecasDAO) {
		List<PecaPojo> pecasDB = new ArrayList<PecaPojo>();
		try {
			pecasDB = pecasDAO.consultaPecas();
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void removePecaPorCodigoDeBarras(Scanner teclado, PecaDAO pecasDAO, int codigoDeBarras)
			throws SQLException {
		System.out.println("\n\tDigite o Código De Barras Da Peças a Ser Removida!\n");
		System.out.print("\tCódigo De Barras: ");
		codigoDeBarras = teclado.nextInt();
		try {
			pecasDAO.removePecaPorCodigoDeBarras(codigoDeBarras);
			System.out.println("\n\tPeça Removida Com Sucesso!\n");
		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMensagem());
		}

	}

	public static void buscaPecasPorCodigoDeBarras(Scanner teclado, PecaDAO pecasDAO, PecaPojo pecasDB,
			int codigoDeBarras) {
		System.out.println("\n\tDigite o Código De Barras Referente a Peça a Ser Ser Consultada!\n");
		System.out.print("\tCódigo De Barras: ");
		codigoDeBarras = teclado.nextInt();

		try {
			pecasDB = pecasDAO.buscaPecasPorCodigoDeBarras(codigoDeBarras);
			System.out.println("\n\t" + pecasDB);

		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMensagem());
		}

	}

	public static void buscaPecasPorLetraReferencia(Scanner teclado, PecaDAO pecasDAO) {
		System.out.println("\n\tDigite a Letra Referencia Da(s) Peça(s) A Ser(em) Buscada!\n");
		System.out.print("\tLetra Refencia: ");
		String letraRefencia = teclado.next();
		List<PecaPojo> pecasDB = new ArrayList<PecaPojo>();
		try {
			pecasDB = pecasDAO.buscaPecasPorLetraReferencia(letraRefencia);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void buscaPecasPorModeloDoCarro(Scanner teclado, PecaDAO pecasDAO) {
		System.out.println("\n\tDigite O Modelo Do Carro Pra Buscar Peças Referentes!\n");
		System.out.print("\tModelo Do Carro: ");
		String modeloDoCarro = teclado.next();
		List<PecaPojo> pecasDB = new ArrayList<PecaPojo>();
		try {
			pecasDB = pecasDAO.buscaPecasPorModeloDoCarro(modeloDoCarro);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void buscaPecasPorCategoria(Scanner teclado, PecaDAO pecasDAO) {
		System.out.println("\n\tDigite Uma Das Categoria Pra Buscar Peças Referentes!\n");
		System.out.print("\tCategoria: ");
		String categoria = teclado.next();
		List<PecaPojo> pecasDB = new ArrayList<PecaPojo>();
		try {
			pecasDB = pecasDAO.buscaPecasPorCategoria(categoria);
			percorrerEImprimirPecasDoEstoque(pecasDB);
		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void gerenciaVendas(Scanner teclado, VendaDAO vendaDAO, PecaDAO pecasDAO) {
		int opcaoInterna;
		do {
			System.out.println("\n\t(1) - Para Comprar Uma Peça;\n");
			System.out.println("\t(2) - Para Imprimir Relatório De Vendas;\n");
			System.out.println("\t(0) - Para Voltar Ao Menu Principal;\n");
			System.out.print("\n\tOpção: ");

			opcaoInterna = teclado.nextInt();

			switch (opcaoInterna) {

			case 1:
				buscaPecasPorLetraReferencia(teclado, pecasDAO);
				verificaCondicoesDeVenda(teclado, vendaDAO);
				break;

			case 2:
				imprimeRelatorioDeVendas(vendaDAO);
				break;

			case 0:
				break;

			default:
				System.out.println("\n\tOpção Inválida, Tente Novamente\n");
				break;
			}
		} while (opcaoInterna != 0);
	}

	public static void verificaCondicoesDeVenda(Scanner teclado, VendaDAO vendaDAO) {
		System.out.println("\n\tAgora Digite O Códito De Barras Referente A Peça Desejada!\n");
		System.out.print("\tCódigo De Barras: ");
		int codigoDeBarras = teclado.nextInt();

		System.out.println("\n\tAgora Digite a Quantidade De Peças!\n");
		System.out.print("\tQuantidade De Peças: ");
		int qtdPecasAComprar = teclado.nextInt();

		try {
			float valorDaCompra = vendaDAO.verificaPecaaSerVendida(qtdPecasAComprar, codigoDeBarras);
			System.out.printf("\n\tO valor Da Compra Ficou Em R$ %.2f\n", valorDaCompra);
			System.out.println("\n\tMuito Obrigado!\n");

		} catch (EstoqueDeCarrosException e) {
			System.err.println(e.getMessage());
		} catch (IOException f) {
			f.printStackTrace();
		}
	}

	public static void imprimeRelatorioDeVendas(VendaDAO vendaDAO) {
		try {
			vendaDAO.imprimeRelatorioDeVendas();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws SQLException, IOException {

		Scanner teclado = new Scanner(System.in);
		PecaDAO pecasDAO = new PecaDAO();
		PecaPojo pecasDB = new PecaPojo();
		VendaDAO vendasDAO = new VendaDAO();


		int opcaoExterna;
		do {
			System.out.println("\n\t(1) - Para Gerenciar Peças Do Estoque;\n");
			System.out.println("\t(2) - Para Gerenciar Vendas;\n");
			System.out.println("\t(0) - Para Sair;\n");

			System.out.print("\n\tOpção: ");

			opcaoExterna = teclado.nextInt();

			switch (opcaoExterna) {

			case 1:
				gerenciaEstoque(teclado, pecasDAO, pecasDB);
				break;

			case 2:
				gerenciaVendas(teclado, vendasDAO, pecasDAO);
				break;
			case 0:
				System.out.println("\n\tMuito Obrigado!\n");
				break;
			default:
				System.out.println("\n\tOpção Inválida, Tente Novamente!\n");
				break;
			}
		} while (opcaoExterna != 0);
		teclado.close();
	}
}
