package br.com.zup.pecadao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.zup.estoqueexcepition.EstoqueDeCarrosException;
import br.com.zup.pojo.PecaPojo;

public class PecaDAO {

	EntityManager manager;

	public PecaDAO() {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("carros");
		this.manager = managerFactory.createEntityManager();

	}

	public void adicionaPeca(PecaPojo peca) throws SQLException {

		manager.getTransaction().begin();
		manager.persist(peca);
		manager.getTransaction().commit();

	}

	public List<PecaPojo> consultaPecas() throws EstoqueDeCarrosException {

		Query query = manager.createQuery("select c from PecaPojo as c");

		List<PecaPojo> pecasDB = query.getResultList();

		if (pecasDB != null) {
			return pecasDB;
		} else {
			throw new EstoqueDeCarrosException("\n\tNÃO HÁ PEÇAS NO BANCO DE DADOS!\n");
		}

	}

	public void removePecaPorCodigoDeBarras(int codigoDeBarras) throws EstoqueDeCarrosException {

		// - remover uma peça do estoque;
		PecaPojo peca = manager.find(PecaPojo.class, codigoDeBarras);

		if (peca != null) {

			manager.getTransaction().begin();
			manager.remove(peca);
			manager.getTransaction().commit();
		} else {
			throw new EstoqueDeCarrosException("\n\tPEÇA NÃO ENCONTRADA PELO CÓDIGO DE BARRAS!\n");
		}

	}

	public PecaPojo buscaPecasPorCodigoDeBarras(int codigoDeBarras) throws EstoqueDeCarrosException {

		// - consultar uma peça pelo seu código de barras;

		PecaPojo pecaASerConsultada = manager.find(PecaPojo.class, codigoDeBarras);

		if (pecaASerConsultada != null) {
			return pecaASerConsultada;
		} else {
			throw new EstoqueDeCarrosException("\n\tPEÇA NÃO ENCONTRADA PELO CÓDIGO DE BARRAS!\n");
		}

	}

	public List<PecaPojo> buscaPecasPorLetraReferencia(String letraReferencia) throws EstoqueDeCarrosException {

		// - listar todas as peças começadas com o nome começado por um determinado
		// texto;

		Query query = manager.createQuery("select p from PecaPojo as p where p.nome like :letraReferencia");

		query.setParameter("letraReferencia", letraReferencia + "%");

		List<PecaPojo> pecaConsultadaPorLetraReferencia = query.getResultList();

		if (pecaConsultadaPorLetraReferencia != null) {
			return pecaConsultadaPorLetraReferencia;
		} else {
			throw new EstoqueDeCarrosException("\n\tNÃO HÁ PEÇAS QUE SE INICIAL POR ESSA(S) LETRA(S)\n");
		}

	}

	public List<PecaPojo> buscaPecasPorModeloDoCarro(String modeloDoCarro) throws EstoqueDeCarrosException {

		// - listar todas as peças para um determinado modelo de carro ;

		Query query = manager.createQuery("select p from PecaPojo as p where p.modeloDoCarro = :modeloDoCarro");

		query.setParameter("modeloDoCarro", modeloDoCarro);

		List<PecaPojo> pecasCnsultadasPeloModeloDoCarro = query.getResultList();

		if (pecasCnsultadasPeloModeloDoCarro != null) {
			return pecasCnsultadasPeloModeloDoCarro;
		} else {
			throw new EstoqueDeCarrosException("\n\tNÃO HÁ PEÇAS PARA ESTE CARRO NO ESTOQUE\n");
		}

	}

	public List<PecaPojo> buscaPecasPorCategoria(String categoria) throws EstoqueDeCarrosException {

		// - listar todas as peças de uma determinada categoria;

		Query query = manager.createQuery("select p from PecaPojo as p where p.categoria = :categoria");

		query.setParameter("categoria", categoria);

		List<PecaPojo> pecaConsultadaPorCategoria = query.getResultList();

		if (pecaConsultadaPorCategoria != null) {
			return pecaConsultadaPorCategoria;
		} else {
			throw new EstoqueDeCarrosException("\n\tNÃO HÁ PEÇAS REFERENTE A CATEGORIA " + categoria + "\n");
		}

	}

}
