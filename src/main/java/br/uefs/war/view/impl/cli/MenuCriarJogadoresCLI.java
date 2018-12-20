package br.uefs.war.view.impl.cli;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import br.uefs.war.controller.RegrasController;
import br.uefs.war.model.Jogador;
import br.uefs.war.view.core.Menu;

public class MenuCriarJogadoresCLI implements Menu<List<Jogador>> {

	private List<String> coresRestantes;

	@Override
	public List<Jogador> exibir() {
		System.out.println("-------------------------------- Criação de Jogadores --------------------------------");

		Scanner scanner = new Scanner(System.in);
		coresRestantes = new LinkedList<>(RegrasController.getInstance().getCores());
		List<Jogador> jogadores = new LinkedList<>();

		int quantidade = solicitarQuantidade();
		String nomeJogador;
		String cor;
		for (int x = 1; x <= quantidade; x++) {
			nomeJogador = "Jogador " + x;
			cor = solicitarCor(nomeJogador);
			jogadores.add(new Jogador(nomeJogador, cor));
			coresRestantes.remove(cor);
		}

		System.out.println("--------------------------------------------------------------------------------------");
		return jogadores;
	}

	private int solicitarQuantidade() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Digite o número de jogadores: ");
		int quantidade = 0;

		do {
			quantidade = scanner.nextInt();
			if (quantidade < RegrasController.getInstance().getMinJogadores() || quantidade > RegrasController.getInstance().getMaxJogadores())
				System.out.print("Informe um valor entre " + RegrasController.getInstance().getMinJogadores() + " e " + RegrasController.getInstance().getMaxJogadores() + ": ");
		} while (quantidade < RegrasController.getInstance().getMinJogadores() || quantidade > RegrasController.getInstance().getMaxJogadores());

		return quantidade;
	}

	private String solicitarCor(String jogador) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Associe uma cor ao " + jogador + ": ");
		String cor;

		do {
			cor = scanner.next().toUpperCase();
			if (!RegrasController.getInstance().getCores().contains(cor))
				System.out.print("Informe uma cor válida (" + Arrays.toString(coresRestantes.toArray()) + "): ");
		} while (!RegrasController.getInstance().getCores().contains(cor));

		return cor;
	}
}
