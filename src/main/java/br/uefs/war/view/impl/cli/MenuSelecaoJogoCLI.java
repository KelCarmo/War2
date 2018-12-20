package br.uefs.war.view.impl.cli;

import br.uefs.war.core.Game;
import br.uefs.war.impl.war1.War1;
import br.uefs.war.imple.war2.War2;
import br.uefs.war.view.core.Menu;

public class MenuSelecaoJogoCLI implements Menu<Game> {
	private int tipo_mapa = 0; 
	public Game exibir() {
		System.out.println("Digite a escolha do jogo:\n");
		System.out.println("1- WAR_1   ou 2- WAR_2 \n");
		//entrada 
		int entrada=2;
		if(entrada==1) {
			System.out.println("Jogo selecionado foi o war_1");
			tipo_mapa=1;
			return new War1();
		}
		else if(entrada==2) {
			System.out.println("Jogo selecionado foi o war_2");
			tipo_mapa=2;
			return new War2();
		}
		else {
			System.out.println("Escolha inválida ! tchau! \n");
			return null;	
		}
		
	}
	public int getTipo_mapa(){
		return tipo_mapa;
	}
	
}
