package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.sis.entity.Usuario;
import br.com.sis.repository.UsuarioRepository;
import br.com.sis.util.jpa.Transactional;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		String senhaCripto = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCripto);
		return usuarioRepository.salvar(usuario);
	}

}
