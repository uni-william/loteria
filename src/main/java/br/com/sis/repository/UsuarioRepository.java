package br.com.sis.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sis.entity.Usuario;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;	
	
	
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}
	
	public List<Usuario> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(usuarioRoot);

		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		List<Usuario> lista = query.getResultList();
		return lista;
	}	

	public Usuario salvar(Usuario usuario) {
		return manager.merge(usuario);
	}	
	
	@Transactional
	public boolean remover(Usuario usuario) {
		try { 
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Usuário de sistema não pode ser excluído.");
			return false;
		}
	}
	
	public Usuario porLogin(String email) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		predicates.add(builder.equal(usuarioRoot.get("email"), email));
		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

}
