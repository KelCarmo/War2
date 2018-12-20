package br.uefs.war.view.core;

import br.uefs.war.view.impl.cli.TelaCLI;

public class TelaFactory {

	public Tela criar() {
		return new TelaCLI();
	}
}
