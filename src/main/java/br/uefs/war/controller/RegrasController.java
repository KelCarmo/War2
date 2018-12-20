package br.uefs.war.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Classe responsável por amazenar algumas informações de regras do jogo.
 * Ela carrega os valores presentes no arquivo regras.properties e os
 * disponibiliza como atributos. Esta classe implementa o padrão Singleton,
 * ele foi utilizado para evitar que se carregue o arquivo várias vezes
 * desnecessariamente.
 * 
 * @author Matheus Borges
 *
 */
public class RegrasController {

	/**
	 * Instancia da classe. Utilizada para implementação do padrão singleton
	 */
	private static RegrasController instance;

	public static RegrasController getInstance() {
		if (instance == null)
			instance = new RegrasController();
		return instance;
	}

	/**
	 * Número mínimo de jogadores por partida
	 */
	private int minJogadores;
	/**
	 * Número máximo de jogadores por partida
	 */
	private int maxJogadores;
	/**
	 * Número máximo de cartas que um jogador pode armazenar na mão
	 */
	private int maxCartas;
	/**
	 * Número mínimo de exércitos que um jogador deve receber por rodada
	 */
	private int minExercitosAReceber;
	/**
	 * Indica quanto cada território vale para se receber tropas na próxima partida
	 */
	private double exercitosPorTerritorioConquistado;
	/**
	 * Lista contendo todas as corres possíveis para se atribuir a um jogador
	 */
	private List<String> cores;

	/**
	 * Inicializa os atributos a partir da leitura do arquivo properties
	 * regras.properties.
	 */
	private RegrasController() {
		try {
			Properties p = new Properties();
			p.load(getClass().getResourceAsStream("/br/uefs/war/regras.properties"));

			minJogadores = Integer.parseInt(p.getProperty("minJogadores", "2"));
			maxJogadores = Integer.parseInt(p.getProperty("maxJogadores", "6"));
			maxCartas = Integer.parseInt(p.getProperty("maxCartas", "5"));
			minExercitosAReceber = Integer.parseInt(p.getProperty("minExercitosAReceber", "3"));
			exercitosPorTerritorioConquistado = Double.parseDouble(p.getProperty("exercitosPorTerritorioConquistado", "0.5"));

			String temp;
			temp = p.getProperty("cores", "BRANCO,PRETO,VERMELHO,AZUL,AMARELO,VERDE");
			cores = Arrays.asList(temp.split(","));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public int getMinJogadores() {
		return minJogadores;
	}

	public int getMaxJogadores() {
		return maxJogadores;
	}

	public int getMaxCartas() {
		return maxCartas;
	}

	public List<String> getCores() {
		return cores;
	}

	public int getMinExercitosAReceber() {
		return minExercitosAReceber;
	}

	public double getExercitosPorTerritorioConquistado() {
		return exercitosPorTerritorioConquistado;
	}
}
