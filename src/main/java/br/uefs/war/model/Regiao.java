package br.uefs.war.model;

import java.util.LinkedList;
import java.util.List;

public class Regiao {

	private String nome;
	private List<Territorio> territorios;

	public Regiao(String nome) {
		this.nome = nome;
		territorios = new LinkedList<>();
	}

	public String getNome() {
		return nome;
	}

	public List<Territorio> getTerritorios() {
		return territorios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append(nome)
				.append(" - ");
		getTerritorios().forEach((t) -> builder.append(t.toString()).append(" "));
		return builder.toString();
	}
}