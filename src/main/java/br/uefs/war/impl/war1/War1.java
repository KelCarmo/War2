package br.uefs.war.impl.war1;

import br.uefs.war.core.Game;

/**
 * Implementação da classe abstrata Game. Até então, só há de diferenciação
 * a seleção do mapa.
 * 
 * @author Matheus Borges
 *
 */
public class War1 extends Game {

	@Override
	protected String selecionarMapa() {
		return "/br/uefs/war/war1/mapa.json";
	}
}
