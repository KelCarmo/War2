package br.uefs.war.view.impl.cli;

import java.util.Scanner;
import br.uefs.war.controller.TrocaCartaController;
import br.uefs.war.model.Jogador;
import br.uefs.war.model.Mapa;
import br.uefs.war.model.Territorio;
import br.uefs.war.view.core.Menu;
import br.uefs.war.view.core.Tela;

public class MenuFortificarTerritoriosCLI implements Menu<Void> {

	private Tela tela;
	private Mapa mapa;
	private Jogador jogador;

	public void setTela(Tela tela) {
		this.tela = tela;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	@Override
	public Void exibir() {
		System.out.println("------ Etapa: Fortificar Territórios ------");

		Scanner scanner = new Scanner(System.in);
		int opcao = 0;
		do {
			tela.exibirMapa(mapa);
			System.out.format("%s (%s), você tem %d exércitos para distribuir. Digite a sigla do território e o número de exércitos (ex. BA-5): \n", jogador.getNome(), jogador.getCor(),
					jogador.getExercitosNaoPosicionados());
			if (!jogador.getCartas().isEmpty())
				System.out.println("Você possui " + jogador.getCartas().size() + " carta(s) para troca.");
			System.out.println("1 - Fortificar Território");
			if (!jogador.getCartas().isEmpty())
				System.out.println("2 - Trocar Carta-Estado");
			System.out.println("9 - Sair do Jogo");
			opcao = scanner.nextInt();

			switch (opcao) {
				case 1:
					System.out.println();
					System.out.println();
					tela.exibirMapa(mapa);
					fortificarTerritorio();
					break;
				case 2:
					if (!jogador.getCartas().isEmpty()) {
						System.out.println();
						System.out.println();
						trocarCartaEstado();
					} else {
						System.out.println("Opção Inválida!");
					}
					break;
				case 9:
					System.exit(0);
					break;
				default:
					System.out.println("Opção Inválida!");
					break;
			}
		} while (jogador.getExercitosNaoPosicionados() > 0);
		System.out.println("------------------------------------------");
		return null;
	}

	private void trocarCartaEstado() {
		if (jogador.getCartas().isEmpty()) {
			System.out.println("Não há cartas para serem trocadas");
			return;
		}

		int exercitos = TrocaCartaController.getInstance().efetuarTroca(jogador);
		System.out.println("Você recebeu " + exercitos + " exército(s) pela troca de uma carta.");
		if (jogador.getCartas().isEmpty())
			System.out.println("Você não possui mais cartas para troca.");
		else
			System.out.println("Você ainda possui " + jogador.getCartas().size() + " carta(s) para troca.");
	}

	private String fortificarTerritorio() {
		String pattern = "([A-Za-z]{2})([\\-||\\s]?)(\\d+)";
		String input = null;
		String sigla;
		Territorio territorio;
		int exercitos;
		boolean exercitoPosicionado = false;
		Scanner scanner = new Scanner(System.in);
		do {
			input = scanner.nextLine().toUpperCase();
			if (input == null || !input.matches(pattern)) {
				System.out.println("Entrada inválida. Digite a sigla do território e o número de exércitos (ex. BA5): ");
				continue;
			}

			sigla = input.replaceFirst(pattern, "$1");
			territorio = jogador.getTerritorio(sigla);
			if (territorio == null) {
				System.out.println("O território informado não existe ou não pertence ao jogador.");
				continue;
			}

			exercitos = Integer.parseInt(input.replaceFirst(pattern, "$3"));
			if (exercitos > jogador.getExercitosNaoPosicionados()) {
				System.out.println("A quantidade de exércitos informada excede o total disponível.");
				continue;
			}

			exercitoPosicionado = true;
			territorio.atualizarExercitos(exercitos);
			jogador.atualizarExercitosNaoPosicionados(-exercitos);
		} while (!exercitoPosicionado);

		return input;
	}
}
