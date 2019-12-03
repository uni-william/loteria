package br.com.sis.enuns;

public enum Mes {

	JANEIRO("Janeiro"),
	FEVEREIRO("Fevereiro"),
	MARCO("Março"),
	ABRIL("Abril"),
	MAIO("Maio"),
	JUNHO("Junho"),
	JULHO("Julho"),
	AGOSTO("Agosto"),
	SETEMBRO("Setembro"),
	OUTUBRO("Outubro"),
	NOVEMBRO("Novembro"),
	DEZEMBRO("Dezembro");

	Mes(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}
