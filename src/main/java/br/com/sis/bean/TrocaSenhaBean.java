package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.sis.entity.Usuario;
import br.com.sis.repository.UsuarioRepository;
import br.com.sis.security.Seguranca;
import br.com.sis.service.UsuarioService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TrocaSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Seguranca seguranca;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private UsuarioService usuarioService;

	private String novaSenha;
	private String confirmaSenha;

	private Usuario usuario;

	@NotEmpty
	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	@NotEmpty
	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public void inicializar() {
		usuario = seguranca.getUsuarioLogado().getUsuario();
	}

	public void trocarSenha() {
		usuario = usuarioRepository.porId(usuario.getId());
		usuario.setSenha(novaSenha);
		usuario = usuarioService.salvar(usuario);
		if (usuario != null) {
			FacesUtil.redirecionarPagina("/Home.xhtml?trocada=true");
		}

	}

}
