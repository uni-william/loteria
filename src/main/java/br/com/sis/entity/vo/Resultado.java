package br.com.sis.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sis.entity.Aposta;

public class Resultado implements Serializable {

	private static final long serialVersionUID = 1L;

	private Aposta aposta;
	private List<Integer> numerosAcertados = new ArrayList<Integer>();

	public Aposta getAposta() {
		return aposta;
	}

	public void setAposta(Aposta aposta) {
		this.aposta = aposta;
	}

	public List<Integer> getNumerosAcertados() {
		return numerosAcertados;
	}

	public void setNumerosAcertados(List<Integer> numerosAcertados) {
		this.numerosAcertados = numerosAcertados;
	}
	
	public String getNumerosFormatados() {
		List<String> listaFormadada = new ArrayList<String>();
		for (Integer num : this.getNumerosAcertados()) {
			if (num.intValue() < 10) {
				listaFormadada.add("0" + num.toString());
			} else {
				listaFormadada.add(num.toString());
			}
		}
		return listaFormadada.toString();
	}


}
