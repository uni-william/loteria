package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean trocada;

	public boolean isTrocada() {
		return trocada;
	}

	public void setTrocada(boolean trocada) {
		this.trocada = trocada;
	}
	
	public void inicializar() {
		if (this.trocada) {
			FacesUtil.addInfoMessage("Senha trocada com sucesso!");
		}
	}

}
