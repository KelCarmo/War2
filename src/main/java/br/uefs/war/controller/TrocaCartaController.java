package br.uefs.war.controller;

import br.uefs.war.model.Jogador;

/**
 * Classe responsável pela instanciação do controlador de trocas de carta.
 * Esse controlador é responsável por ralizar toda a lógica do processo
 * de troca de cartas. Implementa o padrão singleton para manter consistente
 * o acréscimo de exércitos a cada troca.
 * 
 * @author Matheus Borges
 *
 */
public class TrocaCartaController {

	/**
	 * Instancia da classe. Utilizada para implementação do padrão singleton
	 */
	private static TrocaCartaController instance;

	public static TrocaCartaController getInstance() {
		if (instance == null)
			instance = new TrocaCartaController();
		return instance;
	}

	/**
	 * Armazena a quantidade de trocas já efetuadas na partida
	 */
	private int trocasEfetuadas;

	private TrocaCartaController() {
		trocasEfetuadas = 0;
	}

	/**
	 * Reinicia a contagem de trocas efetuadas
	 */
	public void limpar() {
		trocasEfetuadas = 0;
	}

	/**
	 * Efetua a troca de carta para um jogador, atribuindo-lhe a
	 * quantidade de exércitos devida e removendo, em seguida, a carta
	 * utilizada no processo. Caso o jogador não possua cartas, o
	 * o processo não é feito.
	 * 
	 * @param jogador
	 *            Jogador que terá uma carta trocada.
	 * 
	 * @return A quantidade de exércitos recebidos durante a troca
	 */
	public int efetuarTroca(Jogador jogador) {
		if (!jogador.getCartas().isEmpty()) {
			trocasEfetuadas++;
			jogador.getCartas().remove(0);
			jogador.atualizarExercitosNaoPosicionados(trocasEfetuadas);
			return trocasEfetuadas;
		}
		return 0;
	}
}
