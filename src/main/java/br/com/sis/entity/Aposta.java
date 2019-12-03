package br.com.sis.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.sis.enuns.Jogo;
import br.com.sis.enuns.Mes;

@Entity
@Table(name = "aposta")
public class Aposta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Jogo jogo;
	private List<Integer> numeros = new ArrayList<Integer>();
	private Usuario usuario;
	private Mes mes;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 25, nullable = false)
	@NotNull
	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	@ElementCollection
	@CollectionTable(name = "aposta_numeros", joinColumns = @JoinColumn(name = "aposta_id"))
	@Column(name = "numero")
	public List<Integer> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Integer> numeros) {
		this.numeros = numeros;
	}

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20)	
	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}

	@Transient
	public String getDescricao() {
		String sId = "";
		if (this.id != null) {
			if (this.id.intValue() < 10) {
				sId = "0" + this.id.toString();
			} else {
				sId = this.id.toString();
			}
		}
		return "Jogo - " + sId;
	}

	@Transient
	public String getNumerosLista() {
		List<String> listaFormadada = new ArrayList<String>();
		for (Integer num : this.getNumeros()) {
			if (num.intValue() < 10) {
				listaFormadada.add("0" + num.toString());
			} else {
				listaFormadada.add(num.toString());
			}
		}
		return listaFormadada.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aposta other = (Aposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
