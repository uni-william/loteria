package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aposta;
import br.com.sis.entity.vo.Resultado;
import br.com.sis.enuns.Jogo;
import br.com.sis.enuns.Mes;
import br.com.sis.repository.ApostaRepository;
import br.com.sis.security.Seguranca;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ResultadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ApostaRepository apostaRepository;

	@Inject
	private Seguranca seguranca;

	private String tipo;
	private List<Integer> numerosSorteados = new ArrayList<Integer>();
	private Integer numero;
	private Mes mesSorteado;
	private List<Resultado> resultados = new ArrayList<Resultado>();
	private String numerosSite;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Integer> getNumerosSorteados() {
		return numerosSorteados;
	}

	public void setNumerosSorteados(List<Integer> numerosSorteados) {
		this.numerosSorteados = numerosSorteados;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Mes getMesSorteado() {
		return mesSorteado;
	}

	public void setMesSorteado(Mes mesSorteado) {
		this.mesSorteado = mesSorteado;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public Jogo getJogo() {
		return Jogo.valueOf(tipo);
	}

	public Mes[] getMeses() {
		return Mes.values();
	}

	public String getNumerosSite() {
		return numerosSite;
	}

	public void setNumerosSite(String numerosSite) {
		this.numerosSite = numerosSite;
	}

	public boolean isDiaSorte() {
		return this.getJogo().equals(Jogo.DIASORTE);
	}

	public void inicializar() {
		this.numero = new Integer(0);
	}

	public String getNumerosSorteadosFormatados() {
		List<String> listaFormadada = new ArrayList<String>();
		for (Integer num : this.numerosSorteados) {
			if (num.intValue() < 10) {
				listaFormadada.add("0" + num.toString());
			} else {
				listaFormadada.add(num.toString());
			}
		}
		return listaFormadada.toString();

	}

	public void adicionarNumero() {
		if (this.getNumerosSorteados().contains(this.numero)) {
			FacesUtil.addErroMessage("Número já consta nos números sorteados!");
		} else {
			this.getNumerosSorteados().add(this.numero);
			numero = new Integer(0);
		}
	}

	public void conferirResultado() {
		resultados.clear();
		List<Aposta> listaApostas = apostaRepository.listarPorJogoEUsuario(this.getJogo(),
				seguranca.getUsuarioLogado().getUsuario());
		for (Aposta aposta : listaApostas) {
			Resultado resultado = new Resultado();
			resultado.setAposta(aposta);
			for (Integer num : aposta.getNumeros()) {
				if (numerosSorteados.contains(num)) {
					resultado.getNumerosAcertados().add(num);
				}
			}
			resultados.add(resultado);

		}
	}

	public boolean isHabilitarSalvar() {
		boolean resultado = false;
		if (this.getJogo().equals(Jogo.DIASORTE)) {
			resultado = this.getJogo().getQtdSorteio() == this.getNumerosSorteados().size() && this.mesSorteado != null;
		} else {
			resultado = this.getJogo().getQtdSorteio() == this.getNumerosSorteados().size();
		}
		return resultado;
	}

	public boolean isHabilitarAdicionar() {
		return this.getNumerosSorteados().size() < this.getJogo().getQtdSorteio();
	}

	public boolean isHabilitarCarregar() {
		return this.numerosSite != null && !this.numerosSite.isEmpty();
	}

	public void carregar() {
		this.getNumerosSorteados().clear();
		int indice, indice_mais1;
		String somenteNumeros = "";
		String caracter;
		indice_mais1 = 1;
		for (indice = 0; indice < this.numerosSite.length(); indice++) {
			indice_mais1 = indice + 1;
			caracter = this.numerosSite.substring(indice, indice_mais1);
			if ("0123456789".contains(caracter)) {
				somenteNumeros = somenteNumeros + caracter;
			}
		}
		for (indice = 0; indice < somenteNumeros.length(); indice += 2) {
			indice_mais1 = indice + 2;
			caracter = somenteNumeros.substring(indice, indice_mais1);
			this.numerosSorteados.add(Integer.parseInt(caracter));
		}

	}

}
