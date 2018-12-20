package br.uefs.war;

import br.uefs.war.core.Game;
import br.uefs.war.view.core.Tela;
import br.uefs.war.view.core.TelaFactory;

public class App {

	public static void main(String[] args) {
		TelaFactory telaFactory = new TelaFactory();
		Tela tela = telaFactory.criar();

		Game game = tela.exibirEscolhaJogo();
		game.setTela(tela);
		game.iniciar();
	}
}
