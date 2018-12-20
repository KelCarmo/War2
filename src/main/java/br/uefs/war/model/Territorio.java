package br.uefs.war.model;

import java.util.LinkedList;
import java.util.List;

public class Territorio {

	private String nome;
	private String sigla;
	private List<Territorio> vizinhos;
	private Jogador dominador;
	private int exercitos;
	private int avioes;
	private int deslocados;
	private int recebidos;
	private int total_exercito_avioes;

	public Territorio(String nome, String sigla) {
		this.nome = nome;
		this.sigla = sigla;
		vizinhos = new LinkedList<>();
		dominador = null;
		exercitos = 0;
		avioes = 0;
	}

	
	public String getNome() {
		return nome;
	}

	public String getSigla() {
		return sigla;
	}

	public List<Territorio> getVizinhos() {
		return vizinhos;
	}

	public Jogador getDominador() {
		return dominador;
	}

	public void setDominador(Jogador dominador) {
		this.dominador = dominador;
	}

	public int getExercitos() {
		return exercitos;
	}
	public int getAvioes() {
		return avioes;
	}

	public int getExercitosDisponiveis() {
		return exercitos - deslocados;
	}
	public int getAvioesDisponiveis() {
		return avioes - deslocados;
	}

	public void atualizarAvioes(int quantidade) {
		avioes += quantidade;
	}
	public void atualizarExercitos(int quantidade) {
		exercitos += quantidade;
	}

	public void deslocarExercitos(int exercitos) {
		this.exercitos -= exercitos;
		deslocados += exercitos;
	}
	public void deslocarAvioess(int avioes) {
		this.avioes -= avioes;
		deslocados += avioes;
	}

	public void receberExercitos(int exercitos) {
		recebidos += exercitos;
	}
	public void receberAvioes(int avioes) {
		recebidos += avioes;
	}

	public void confirmarMovimentacoes() {
		exercitos -= deslocados;
		deslocados = 0;
		exercitos += recebidos;
		recebidos = 0;
	}
	public int getTotal_exercito_avioes() {
		return exercitos+avioes;
	}

	@Override
	public String toString() {
		if (deslocados > 0 && recebidos > 0)
			return String.format("%s(%s->%d [-%d +%d])", sigla, dominador.getCor(), exercitos, deslocados, recebidos);
		else if (deslocados > 0)
			return String.format("%s(%s->%d [-%d])", sigla, dominador.getCor(), exercitos, deslocados);
		else if (recebidos > 0)
			return String.format("%s(%s->%d [+%d])", sigla, dominador.getCor(), exercitos, recebidos);
		return String.format("%s(%s->%d)", sigla, dominador.getCor(), exercitos);
	}

	public boolean ehVizinho(Territorio territorio) {
		return vizinhos.contains(territorio);
	}
}
