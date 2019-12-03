package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;

import br.com.sis.entity.Usuario;
import br.com.sis.service.UsuarioService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario;

	private String confirmaSenha;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@NotBlank
	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public void inicializar() {
		if (usuario == null) {
			usuario = new Usuario();
		}
	}

	public void salvar() {
		usuarioService.salvar(usuario);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");
		usuario = new Usuario();
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

}
