package br.uefs.war.view.impl.cli;

import java.util.Scanner;
import br.uefs.war.model.Jogador;
import br.uefs.war.model.Mapa;
import br.uefs.war.model.Territorio;
import br.uefs.war.view.core.Menu;
import br.uefs.war.view.core.Tela;

public class MenuDeslocarExercitosCLI implements Menu<Void> {

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
		System.out.println("-------- Etapa: Deslocar Exércitos --------");

		Scanner scanner = new Scanner(System.in);
		int opcao = 0;

		do {
			tela.exibirMapa(mapa);
			System.out.println("1 - Encerrar Jogada");
			System.out.println("2 - Deslocar Exércitos");
			System.out.println("9 - Sair do Jogo");
			opcao = scanner.nextInt();

			switch (opcao) {
				case 1:
					jogador.getTerritorios().forEach((t) -> t.confirmarMovimentacoes());
					break;
				case 2:
					System.out.println();
					System.out.println();
					fortificarTerritorio();
					tela.exibirMapa(mapa);
					break;
				case 9:
					System.exit(0);
					break;
				default:
					System.out.println("Opção Inválida!");
					break;
			}
		} while (opcao != 1);

		return null;
	}

	private void fortificarTerritorio() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("1 -Cancelar");
		System.out.print("OO-DD-E - Para mover E Exércitos de OO para DD. (ex. BA-PE-5): ");

		String pattern = "([A-Za-z]{2})([\\-||\\s]?)([A-Za-z]{2})([\\-||\\s]?)(\\d+)";
		String input = null;
		String siglaOrigem;
		String siglaDestino;
		Territorio origem;
		Territorio destino;
		int exercitos;
		boolean exercitoDeslocado = false;

		do {
			input = scanner.nextLine().toUpperCase();
			if (input.equals("1"))
				return;

			if (input == null || !input.matches(pattern)) {
				System.out.println("Entrada Inválida.");
				System.out.println();
				System.out.println("1 -Cancelar");
				System.out.println("9 - Sair do Jogo");
				System.out.print("OO-DD-E - Para mover E tropas de OO para DD. (ex. BA-PE-5): ");
				continue;
			}

			siglaOrigem = input.replaceFirst(pattern, "$1");
			origem = jogador.getTerritorio(siglaOrigem);
			if (origem == null) {
				System.out.println("O território de origem informado não existe ou não pertence ao jogador.");
				System.out.println();
				System.out.println("1 -Cancelar");
				System.out.println("9 - Sair do Jogo");
				System.out.print("OO-DD-E - Para mover E tropas de OO para DD. (ex. BA-PE-5): ");
				continue;
			}

			siglaDestino = input.replaceFirst(pattern, "$3");
			destino = jogador.getTerritorio(siglaDestino);
			if (destino == null) {
				System.out.println("O território de destino informado não existe ou não pertence ao jogador.");
				System.out.println();
				System.out.println("1 -Cancelar");
				System.out.println("9 - Sair do Jogo");
				System.out.print("OO-DD-E - Para mover E tropas de OO para DD. (ex. BA-PE-5): ");
				continue;
			}

			exercitos = Integer.parseInt(input.replaceFirst(pattern, "$5"));
			if (exercitos > origem.getExercitosDisponiveis()) {
				System.out.println("A quantidade de exércitos informada excede o total disponível (Exércitos do território: " + origem.getExercitosDisponiveis() + ").");
				System.out.println();
				System.out.println("1 -Cancelar");
				System.out.println("9 - Sair do Jogo");
				System.out.print("OO-DD-E - Para mover E tropas de OO para DD. (ex. BA-PE-5): ");
				continue;
			}

			if (!origem.ehVizinho(destino)) {
				System.out.println("Os territórios informados devem fazer fronteira um com o outro.");
				System.out.println();
				System.out.println("1 -Cancelar");
				System.out.println("9 - Sair do Jogo");
				System.out.print("OO-DD-E - Para mover E tropas de OO para DD. (ex. BA-PE-5): ");
				continue;
			}

			origem.deslocarExercitos(exercitos);
			destino.receberExercitos(exercitos);
			exercitoDeslocado = true;
		} while (!exercitoDeslocado);
	}
}
