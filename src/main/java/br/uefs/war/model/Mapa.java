package br.uefs.war.model;

import java.util.LinkedList;
import java.util.List;

public class Mapa {

	private List<Regiao> regioes;

	public Mapa() {
		regioes = new LinkedList<>();
	}

	public List<Regiao> getRegioes() {
		return regioes;
	}

	public List<Territorio> getTerritorios() {
		List<Territorio> territorios = new LinkedList<>();
		regioes.forEach((r) -> territorios.addAll(r.getTerritorios()));
		return territorios;
	}

	public Territorio getTerritorio(String sigla) {
		for (Territorio t : getTerritorios())
			if (t.getSigla().equals(sigla))
				return t;
		return null;
	}
}
