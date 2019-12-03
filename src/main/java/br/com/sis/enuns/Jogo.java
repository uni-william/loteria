package br.com.sis.enuns;

public enum Jogo {

	MEGASENA("Mega Sena", 6, 15, 6, 1, 60),
	QUINA("Quina", 5, 15, 5, 1, 80),
	LOTOFACIL("Lotofácil", 15, 18, 15, 1, 25),
	LOTOMANIA("Lotomania", 50, 50, 20, 0, 99),
	DIASORTE("Dia da sorte", 7, 15, 7, 1, 31);

	Jogo(String descricao, int minimo, int maximo, int qtdSorteio, int primeiraDezena, int ultimaDezena) {
		this.descricao = descricao;
		this.minino = minimo;
		this.maximo = maximo;
		this.qtdSorteio = qtdSorteio;
		this.primeiraDezena = primeiraDezena;
		this.ultimaDezena = ultimaDezena;

	}

	private String descricao;
	private int minino;
	private int maximo;
	private int qtdSorteio;
	private int primeiraDezena;
	private int ultimaDezena;

	public String getDescricao() {
		return descricao;
	}

	public int getMinino() {
		return minino;
	}

	public int getMaximo() {
		return maximo;
	}

	public int getQtdSorteio() {
		return qtdSorteio;
	}

	public int getPrimeiraDezena() {
		return primeiraDezena;
	}

	public int getUltimaDezena() {
		return ultimaDezena;
	}

}
