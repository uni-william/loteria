package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Aposta;
import br.com.sis.repository.ApostaRepository;
import br.com.sis.util.jpa.Transactional;

public class ApostaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ApostaRepository apostaRepository;

	@Transactional
	public Aposta salvar(Aposta aposta) {
		return apostaRepository.salvar(aposta);
	}

}
