package br.com.sis.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sis.entity.Aposta;
import br.com.sis.entity.Usuario;
import br.com.sis.enuns.Jogo;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class ApostaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Aposta porId(Long id) {
		return manager.find(Aposta.class, id);
	}

	public List<Aposta> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aposta> criteriaQuery = builder.createQuery(Aposta.class);
		Root<Aposta> apostaRoot = criteriaQuery.from(Aposta.class);
		criteriaQuery.select(apostaRoot);

		TypedQuery<Aposta> query = manager.createQuery(criteriaQuery);
		List<Aposta> lista = query.getResultList();
		return lista;
	}

	public Aposta salvar(Aposta aposta) {
		return manager.merge(aposta);
	}

	@Transactional
	public boolean remover(Aposta aposta) {
		try {
			aposta = porId(aposta.getId());
			manager.remove(aposta);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Usuário de sistema não pode ser excluído.");
			return false;
		}
	}

	public List<Aposta> listarPorJogoEUsuario(Jogo jogo, Usuario usuario) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aposta> criteriaQuery = builder.createQuery(Aposta.class);
		Root<Aposta> apostaRoot = criteriaQuery.from(Aposta.class);
		apostaRoot.fetch("usuario", JoinType.INNER);
		criteriaQuery.select(apostaRoot);

		criteriaQuery.select(apostaRoot);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(apostaRoot.get("usuario"), usuario));
		predicates.add(builder.equal(apostaRoot.get("jogo"), jogo));

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Aposta> query = manager.createQuery(criteriaQuery);
		List<Aposta> lista = query.getResultList();
		return lista;
	}

}
