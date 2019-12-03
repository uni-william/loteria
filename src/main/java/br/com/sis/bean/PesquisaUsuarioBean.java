package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Usuario;
import br.com.sis.repository.UsuarioRepository;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;

	private List<Usuario> lista = new ArrayList<Usuario>();
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getLista() {
		return lista;
	}
	
	public void pesquisar() {
		lista = usuarioRepository.listarTodos();
	}
	
	public void inicializar() {
		pesquisar();
	}
	
	public void excluir() {
		usuarioRepository.remover(usuario);
	}

}
