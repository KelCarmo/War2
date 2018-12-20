package br.uefs.war.view.impl.cli;

import java.util.List;
import br.uefs.war.core.Game;
import br.uefs.war.imple.war2.War2;
import br.uefs.war.model.Jogador;
import br.uefs.war.model.Mapa;
import br.uefs.war.view.core.Tela;

public class TelaCLI implements Tela {
	int war =  0;
	@Override
	public Game exibirEscolhaJogo() {
		MenuSelecaoJogoCLI m = new MenuSelecaoJogoCLI();
		war = m.getTipo_mapa();
		return m.exibir();
		//return new MenuSelecaoJogoCLI().exibir();
	}

	@Override
	public void exibirMapa(Mapa mapa) {
		System.out.println("Mapa:");
		mapa.getRegioes().forEach((r) -> System.out.println(r));
		System.out.println();
		System.out.println();
	}

	@Override
	public List<Jogador> exibirCriacaoJogadores() {
		return new MenuCriarJogadoresCLI().exibir();
	}

	@Override
	public void exibirFortificarTerritorios(Mapa mapa, Jogador jogador) {
		MenuFortificarTerritoriosCLI menu = new MenuFortificarTerritoriosCLI();
		menu.setMapa(mapa);
		menu.setTela(this);
		menu.setJogador(jogador);
		menu.exibir();
	}

	@Override
	public boolean exibirAtacar(Mapa mapa, Jogador jogador) {
		if(this.war==1){
			
			MenuAtacarCLI menu = new MenuAtacarCLI();
			menu.setMapa(mapa);
			menu.setTela(this);
			menu.setJogador(jogador);
			return menu.exibir();
			
		}
		else {
			
			MenuAtacarCLI_2 menu = new MenuAtacarCLI_2();
			menu.setMapa(mapa);
			menu.setTela(this);
			menu.setJogador(jogador);
			return menu.exibir();
			
		}
		
	}

	@Override
	public void exibirDeslocarExercitos(Mapa mapa, Jogador jogador) {
		MenuDeslocarExercitosCLI menu = new MenuDeslocarExercitosCLI();
		menu.setMapa(mapa);
		menu.setTela(this);
		menu.setJogador(jogador);
		menu.exibir();
	}

	@Override
	public void informarInicioRodada(int rodada, Jogador jogador) {
		System.out.println("------------------------------------------");
		System.out.format("Rodada %d. Inicio da vez do %s - %s\n", rodada, jogador.getNome(), jogador.getCor());
		System.out.println("------------------------------------------");
	}

	@Override
	public void informarFimRodada(int rodada, Jogador jogador) {
		System.out.println("------------------------------------------");
		System.out.format("Rodada %d. Fim da jogada do %s - %s\n", rodada, jogador.getNome(), jogador.getCor());
		System.out.println("------------------------------------------");
	}

	@Override
	public void exibirFim(Jogador vencedor) {
		if (vencedor != null)
			System.out.println("O jogador " + vencedor.getNome() + " venceu a partida!");
		else
			System.out.println("Jogo finalizado.");
	}
}
