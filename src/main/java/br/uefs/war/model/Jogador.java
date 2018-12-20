package br.uefs.war.model;

import java.util.LinkedList;
import java.util.List;

public class Jogador {

	private String nome;
	private String cor;
	private List<Carta> cartas;
	private List<Territorio> territorios;
	private int exercitosNaoPosicionados;
	private boolean derrotado;

	public Jogador(String nome, String cor) {
		this.nome = nome;
		this.cor = cor;
		cartas = new LinkedList<>();
		territorios = new LinkedList<>();
		derrotado = false;
	}

	public String getNome() {
		return nome;
	}

	public String getCor() {
		return cor;
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public List<Territorio> getTerritorios() {
		return territorios;
	}

	public Territorio getTerritorio(String sigla) {
		for (Territorio t : territorios)
			if (t.getSigla().equals(sigla))
				return t;
		return null;
	}

	public int getExercitosNaoPosicionados() {
		return exercitosNaoPosicionados;
	}

	public void atualizarExercitosNaoPosicionados(int exercitosNaoPosicionados) {
		this.exercitosNaoPosicionados += exercitosNaoPosicionados;
	}

	public boolean isDerrotado() {
		return derrotado;
	}

	public void setDerrotado(boolean derrotado) {
		this.derrotado = derrotado;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogador other = (Jogador) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
