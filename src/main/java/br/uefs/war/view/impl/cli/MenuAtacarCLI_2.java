package br.uefs.war.view.impl.cli;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import br.uefs.war.model.Jogador;
import br.uefs.war.model.Territorio;

public class MenuAtacarCLI_2 extends MenuAtacarCLI{
	
	@Override

	public boolean atacar() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("1 - Cancelar");
		System.out.println("\n 2-Atacar com exercito");
		System.out.println("\n 2-Atacar com avi�o");
		System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");

		String pattern = "([A-Za-z]{2})([\\-||\\s]?)([A-Za-z]{2})";
		String input = null;
		String siglaAtacante;
		String siglaDefensor;
		Territorio atacante;
		Territorio defensor;
		Jogador jogadorDefensor;

		boolean conquista = false;
		while (true) {
			input = scanner.nextLine().toUpperCase();
			if (input.equals("1"))
				return conquista;

			if (input == null || !input.matches(pattern)) {
				System.out.println("Entrada Inválida.");
				System.out.println();
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				continue;
			}

			siglaAtacante = input.replaceFirst(pattern, "$1");
			atacante = jogador.getTerritorio(siglaAtacante);
			if (atacante == null) {
				System.out.println("O território atacante informado não existe ou não pertence ao jogador.");
				System.out.println();
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				continue;
			}

			siglaDefensor = input.replaceFirst(pattern, "$3");
			defensor = mapa.getTerritorio(siglaDefensor);
			if (defensor == null) {
				System.out.println("O território de destino informado não existe.");
				System.out.println();
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				continue;
			}

			defensor = mapa.getTerritorio(siglaDefensor);
			jogadorDefensor = defensor.getDominador();
			if (jogadorDefensor.equals(jogador)) {
				System.out.println("O território defensor não pode ser do jogador atacante.");
				System.out.println();
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				continue;
			}

			if (!atacante.ehVizinho(defensor)) {
				System.out.println("Os territórios informados devem fazer fronteira um com o outro.");
				System.out.println();
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				continue;
			}

			if (atacante.getExercitos() == 1) {
				System.out.println("Não há exércitos suficientes para efetuar o ataque");
				System.out.println();
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				continue;
			}
			int qtdAtacante = atacante.getExercitos() - 1;
			if (qtdAtacante > 3)
				qtdAtacante = 3;
			int qtdDefensor = defensor.getExercitos();
			if (qtdDefensor > 3)
				qtdDefensor = 3;

			Random rand = new Random();
			Integer[] dadosAtacante = new Integer[qtdAtacante];
			for (int x = 0; x < qtdAtacante; x++)
				dadosAtacante[x] = rand.nextInt(6) + 1;
			Integer[] dadosDefensor = new Integer[qtdDefensor];
			for (int x = 0; x < qtdDefensor; x++)
				dadosDefensor[x] = rand.nextInt(6) + 1;

			Arrays.sort(dadosAtacante, (i1, i2) -> Integer.compare(i2, i1));
			Arrays.sort(dadosDefensor, (i1, i2) -> Integer.compare(i2, i1));
			System.out.println("Atacante: " + Arrays.toString(dadosAtacante));
			System.out.println("Defensor: " + Arrays.toString(dadosDefensor));

			if (dadosAtacante.length > dadosDefensor.length)
				dadosAtacante = Arrays.copyOf(dadosAtacante, dadosDefensor.length);
			if (dadosDefensor.length > dadosAtacante.length)
				dadosDefensor = Arrays.copyOf(dadosDefensor, dadosAtacante.length);

			int perdasAtacante = 0;
			int perdasDefensor = 0;

			for (int x = 0; x < dadosAtacante.length; x++) {
				if (dadosAtacante[x] > dadosDefensor[x])
					perdasDefensor++;
				else
					perdasAtacante++;
			}

			if (perdasAtacante > 0)
				System.out.println("O jogador atacante perdeu " + perdasAtacante + " exércitos durante o ataque");
			if (perdasDefensor > 0)
				System.out.println("O jogador defensor perdeu " + perdasDefensor + " exércitos durante o ataque");

			atacante.atualizarExercitos(-perdasAtacante);
			defensor.atualizarExercitos(-perdasDefensor);

			System.out.println(atacante + " : " + defensor);

			if (defensor.getExercitos() == 0) {
				conquista = true;
				System.out.println("O território " + defensor.getNome().toUpperCase() + " foi conquistado por " + jogador.getNome());
				jogadorDefensor.getTerritorios().remove(defensor);
				jogador.getTerritorios().add(defensor);
				defensor.setDominador(jogador);
				moverTropas(qtdAtacante, atacante, defensor);
				if (jogadorDefensor.getTerritorios().isEmpty()) {
					System.out.println("O jogador " + jogadorDefensor.getNome() + " foi derrotado. Suas cartas serão transferidas para o " + jogador.getNome() + ".");
					while (jogador.getCartas().size() < 5 && !jogadorDefensor.getCartas().isEmpty())
						jogador.getCartas().add(jogadorDefensor.getCartas().remove(0));
					jogadorDefensor.setDerrotado(true);
				} else {
					System.out.println("1 - Cancelar");
					System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
				}
			} else {
				System.out.println("1 - Cancelar");
				System.out.print("OO-DD - Para atacar o território DD usando o território OO. (ex. BA-PE): ");
			}
		}
	}


}
