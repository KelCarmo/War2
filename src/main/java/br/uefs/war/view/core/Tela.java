package br.uefs.war.view.core;

import java.util.List;
import br.uefs.war.core.Game;
import br.uefs.war.model.Jogador;
import br.uefs.war.model.Mapa;

public interface Tela {

	Game exibirEscolhaJogo();

	void exibirMapa(Mapa mapa);

	List<Jogador> exibirCriacaoJogadores();

	void exibirFortificarTerritorios(Mapa mapa, Jogador jogador);

	boolean exibirAtacar(Mapa mapa, Jogador jogador);

	void exibirDeslocarExercitos(Mapa mapa, Jogador jogador);

	void informarInicioRodada(int rodada, Jogador jogador);

	void informarFimRodada(int rodada, Jogador jogador);

	void exibirFim(Jogador vencedor);

}
