package br.uefs.war.imple.war2;


import br.uefs.war.core.Game;

/**
 * Implementação da classe abstrata Game. Até então, só há de diferenciação
 * a seleção do mapa.
 * 
 * @author Matheus Borges
 *
 */
public class War2 extends Game {
//mudar o mapa do arquivo Json q vai ser chamado
	@Override
	protected String selecionarMapa() {
		return "/br/uefs/war/war2/mapa_2.json";
	}
}
