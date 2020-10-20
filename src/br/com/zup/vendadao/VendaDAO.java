package br.com.zup.vendadao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.zup.estoqueexcepition.EstoqueDeCarrosException;
import br.com.zup.pojo.PecaPojo;

public class VendaDAO {

	int diaDaVenda = 1;

	File file;

	EntityManager manager;

	public VendaDAO() {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("carros");
		this.manager = managerFactory.createEntityManager();

		file = new File("Estoque Dia " + diaDaVenda + ".txt");
	}

	public float verificaPecaaSerVendida(int qtdPecasAComprar, int codigoDeBarras)
			throws IOException, EstoqueDeCarrosException {

		int totalDePecas = 0;
		float valorDaPeca = 0;
		String nomeDaPeca = "";

		PecaPojo pecaASerComprada = manager.find(PecaPojo.class, codigoDeBarras);

		if (pecaASerComprada != null) {
			totalDePecas = pecaASerComprada.getQuantidadeEmEstoque();

			valorDaPeca = pecaASerComprada.getPrecoDeVenda();

			nomeDaPeca = pecaASerComprada.getNome();
		}

		float valorDaCompra = alterarEstoqueAposVenda(totalDePecas, valorDaPeca, qtdPecasAComprar, codigoDeBarras,
				nomeDaPeca);

		return valorDaCompra;
	}

	public float alterarEstoqueAposVenda(int totalDePecas, float valorDaPeca, int qtdPecasAComprar, int codigoDeBarras,
			String nomeDaPeca) throws IOException, EstoqueDeCarrosException {

		if (totalDePecas > qtdPecasAComprar) {

			float valorDaCompra = valorDaPeca * qtdPecasAComprar;
			int qtdPecasRestantes = totalDePecas - qtdPecasAComprar;

			PecaPojo pecaVendida = manager.find(PecaPojo.class, codigoDeBarras);
			pecaVendida.setQuantidadeEmEstoque(qtdPecasRestantes);

			manager.getTransaction().begin();
			manager.merge(pecaVendida);
			manager.getTransaction().commit();

			if (file.exists()) {
				diaDaVenda++;
				file.renameTo(file);
			}

			FileWriter escrita = new FileWriter(file, true);

			escrita.append(String.format("\nCódigo: %d\t\tNome: %s\t\tQuantidade: %d\t\tValor: R$ %.2f", codigoDeBarras,
					nomeDaPeca, qtdPecasAComprar, valorDaCompra));
			escrita.close();

			return valorDaCompra;
		} else {
			throw new EstoqueDeCarrosException("\n\tNÃO HÁ PEÇAS SUFICIENTES NO ESTOQUE!\n");
		}
	}

	public void imprimeRelatorioDeVendas() throws IOException {
		FileReader estrutura = new FileReader(file);
		BufferedReader leitor = new BufferedReader(estrutura);

		String lerLinhas;

		while ((lerLinhas = leitor.readLine()) != null) {
			if (lerLinhas != null) {
				System.out.println("\n\t" + lerLinhas);
			}
		}

		leitor.close();
		estrutura.close();

	}

}
