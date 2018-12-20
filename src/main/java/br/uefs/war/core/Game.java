package br.uefs.war.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import br.uefs.war.controller.RegrasController;
import br.uefs.war.model.Carta;
import br.uefs.war.model.Jogador;
import br.uefs.war.model.Mapa;
import br.uefs.war.model.Regiao;
import br.uefs.war.model.Territorio;
import br.uefs.war.view.core.Tela;

/**
 * Classe abstrata responsável por manter toda a lógica e regras de negócio do
 * jogo centralizados. Para o War 1, foi necessário apenas alterar o mapa
 * utilizado.
 * 
 * @author Matheus Borges
 *
 */
public abstract class Game {

	private Tela tela;
	private List<Jogador> jogadores;
	private Mapa mapa;
	private Iterator<Carta> cartas;
	private Jogador vencedor;

	public void setTela(Tela tela) {
		this.tela = tela;
	}

	public void iniciar() {
		criarMapa();
		criarCartas();
		criarJogadores();
		distribuirTerritorios();

		int rodada = 1;
		boolean dominouTerritorio;
		while (!verificarFimDeJogo()) {
			for (Jogador jogador : jogadores) {
				if (jogador.isDerrotado())
					continue;

				informarInicioRodada(rodada, jogador);
				distribuirTropas(jogador);
				fortificarTerritorios(jogador);
				exibirMapa();

				if (rodada != 1) {
					// Trocar carta
					dominouTerritorio = atacar(jogador);
					deslocarExercitos(jogador);
					distribuirCarta(jogador, dominouTerritorio);
				}

				informarFimRodada(rodada, jogador);
			}

			rodada++;
		}

		if (vencedor != null)
			tela.exibirFim(vencedor);
	}

	private void criarMapa() {
		mapa = new Mapa();
		String caminhoMapa = selecionarMapa();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(caminhoMapa)))) {
			String json = "";
			while (reader.ready())
				json += reader.readLine() + "\n";

			Regiao regiao;
			Territorio territorio;
			Map<String, Territorio> territorios = new HashMap<>();
			for (Object r : new JSONArray(json)) { // Criar regiões e seus territórios
				regiao = new Regiao(((JSONObject) r).getString("nome"));
				for (Object t : ((JSONObject) r).getJSONArray("territorios")) {
					territorio = new Territorio(((JSONObject) t).getString("nome"), ((JSONObject) t).getString("sigla"));
					regiao.getTerritorios().add(territorio);
					territorios.put(territorio.getSigla(), territorio);
				}

				mapa.getRegioes().add(regiao);
			}

			String sigla;
			for (Object r : new JSONArray(json)) { // Configurar as fornteiras
				for (Object t : ((JSONObject) r).getJSONArray("territorios")) {
					sigla = ((JSONObject) t).getString("sigla");
					for (Object v : ((JSONObject) t).getJSONArray("vizinhos"))
						territorios.get(sigla).getVizinhos().add(territorios.get(((JSONObject) v).getString("sigla")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void criarCartas() {
		List<Carta> c = new LinkedList<>();
		mapa.getTerritorios().forEach((t) -> c.add(new Carta(t.getNome())));
		Collections.shuffle(c);
		cartas = c.iterator();
	}

	private void exibirMapa() {
		tela.exibirMapa(mapa);
	}

	private void criarJogadores() {
		jogadores = tela.exibirCriacaoJogadores();
	}

	private void distribuirTerritorios() {
		List<Territorio> territorios = new LinkedList<>(mapa.getTerritorios());
		List<Jogador> _jogadores = new LinkedList<>(jogadores);

		Collections.shuffle(territorios);
		Collections.shuffle(_jogadores);

		int jSize = _jogadores.size();
		Jogador j;
		Territorio t;
		for (int x = 0; x < territorios.size(); x++) {
			j = _jogadores.get(x % jSize);
			t = territorios.get(x);

			t.atualizarExercitos(1);
			t.setDominador(j);
			j.getTerritorios().add(t);
		}
	}

	private void distribuirTropas(Jogador j) {
		int territoriosConquistados = j.getTerritorios().size();
		int exercitos = (int) (territoriosConquistados * RegrasController.getInstance().getExercitosPorTerritorioConquistado());

		REGIAO: for (Regiao r : mapa.getRegioes()) {
			for (Territorio t : r.getTerritorios()) {
				if (!j.equals(t.getDominador()))
					continue REGIAO;
			}
			// Região dominada completamente
			exercitos++;
		}

		if (exercitos < RegrasController.getInstance().getMinExercitosAReceber())
			exercitos = RegrasController.getInstance().getMinExercitosAReceber();
		j.atualizarExercitosNaoPosicionados(exercitos);
	}

	private void fortificarTerritorios(Jogador jogador) {
		tela.exibirFortificarTerritorios(mapa, jogador);
	}

	private boolean atacar(Jogador jogador) {
		return tela.exibirAtacar(mapa, jogador);
	}

	private void deslocarExercitos(Jogador jogador) {
		tela.exibirDeslocarExercitos(mapa, jogador);
	}

	private void distribuirCarta(Jogador jogador, boolean dominouTerritorio) {
		if (dominouTerritorio) {
			if (cartas == null || !cartas.hasNext())
				criarCartas();
			if (jogador.getCartas().size() < RegrasController.getInstance().getMaxCartas())
				jogador.getCartas().add(cartas.next());
		}

	}

	private void informarInicioRodada(int rodada, Jogador jogador) {
		tela.informarInicioRodada(rodada, jogador);
	}

	private void informarFimRodada(int rodada, Jogador jogador) {
		tela.informarFimRodada(rodada, jogador);
	}

	private boolean verificarFimDeJogo() {
		List<Territorio> territoriosMapa = mapa.getTerritorios();
		Jogador jogador = territoriosMapa.get(0).getDominador();
		List<Territorio> territoriosJogador = jogador.getTerritorios();
		if (territoriosMapa.size() == territoriosJogador.size())
			vencedor = jogador;
		return territoriosMapa.size() == territoriosJogador.size();
	}

	protected abstract String selecionarMapa();
}
