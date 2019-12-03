package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aposta;
import br.com.sis.enuns.Jogo;
import br.com.sis.enuns.Mes;
import br.com.sis.security.Seguranca;
import br.com.sis.service.ApostaService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroApostaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Seguranca seguranca;

	@Inject
	private ApostaService apostaService;
	private Aposta aposta;
	private Integer numero = new Integer(0);
	private String tipo;
	private int numCarousel;

	public Aposta getAposta() {
		return aposta;
	}

	public void setAposta(Aposta aposta) {
		this.aposta = aposta;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumCarousel() {
		return numCarousel;
	}

	public void setNumCarousel(int numCarousel) {
		this.numCarousel = numCarousel;
	}

	public Mes[] getMeses() {
		return Mes.values();
	}

	public void inicializar() {
		if (aposta == null) {
			aposta = new Aposta();
			aposta.setJogo(Jogo.valueOf(tipo));
			aposta.setUsuario(seguranca.getUsuarioLogado().getUsuario());

			if (aposta.getJogo().equals(Jogo.DIASORTE)) {
				aposta.setMes(Mes.JANEIRO);
			}
		}

		if (aposta.getJogo().getMinino() > 15) {
			numCarousel = 10;
		} else {
			numCarousel = aposta.getJogo().getMinino();
		}

	}

	public void salvar() {
		apostaService.salvar(aposta);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");

	}

	public boolean isEditando() {
		return this.aposta.getId() != null;
	}

	public void removerNumero() {
		this.aposta.getNumeros().remove(numero);
		numero = new Integer(0);
	}

	public void adicionarNumero() {
		if (this.getNumero().intValue() >= this.aposta.getJogo().getPrimeiraDezena()
				&& this.getNumero().intValue() <= this.aposta.getJogo().getUltimaDezena()) {
			if (aposta.getNumeros().contains(numero)) {
				FacesUtil.addErroMessage("Número já consta na aposta!");
			} else {
				this.aposta.getNumeros().add(numero);
				numero = new Integer(0);
			}
		} else {
			FacesUtil.addErroMessage("Só é permitido números entre " + this.aposta.getJogo().getPrimeiraDezena() + " e " + this.aposta.getJogo().getUltimaDezena());
		}
	}

	public boolean isHabilitarSalvar() {
		return this.aposta.getNumeros().size() >= this.aposta.getJogo().getMinino();
	}

	public boolean isHabilitarAdicionar() {
		return this.aposta.getNumeros().size() <= this.aposta.getJogo().getMaximo() - 1;
	}

	public boolean isMesSorte() {
		return this.aposta.getJogo().equals(Jogo.DIASORTE);
	}
}
