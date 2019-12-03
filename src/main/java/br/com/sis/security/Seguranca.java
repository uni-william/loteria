package br.com.sis.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {

	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();

		}
		return usuario;
	}

	public String getEmailUsuario() {
		String nome = null;
		UsuarioSistema user = getUsuarioLogado();
		if (user != null) {
			nome = user.getUsuario().getEmail();
		}
		return nome;
	}
	
	public boolean isAdministrador () {
		UsuarioSistema user = getUsuarioLogado();
		return user.getUsuario().isAdministrador();
	}
	

	
}
