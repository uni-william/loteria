package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aposta;
import br.com.sis.enuns.Jogo;
import br.com.sis.repository.ApostaRepository;
import br.com.sis.security.Seguranca;

@Named
@ViewScoped
public class PesquisaApostaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Seguranca seguranca;
	
	@Inject
	private ApostaRepository apostaRepository;

	private List<Aposta> listaApostas = new ArrayList<Aposta>();
	private Aposta apostaSelecionada;
	private String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Aposta getApostaSelecionada() {
		return apostaSelecionada;
	}

	public void setApostaSelecionada(Aposta apostaSelecionada) {
		this.apostaSelecionada = apostaSelecionada;
	}

	public Jogo getJogo() {
		return Jogo.valueOf(tipo);
	}

	public void pequisar() {		
		listaApostas = apostaRepository.listarPorJogoEUsuario(this.getJogo(), seguranca.getUsuarioLogado().getUsuario());
	}

	public List<Aposta> getListaApostas() {
		return listaApostas;
	}

	public void inicializar() {
		pequisar();
	}

	public void excluir() {
		apostaRepository.remover(apostaSelecionada);
	}	
	
	public boolean isDiaSorte() {
		return this.getJogo().equals(Jogo.DIASORTE);
	}

}
