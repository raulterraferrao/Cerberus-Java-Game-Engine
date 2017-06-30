package gerenciadorDeFontes;

import java.util.ArrayList;
import java.util.List;


public class Linha {

	private double tamanhoLinhaMaximo;
	private double spaceSize;

	private List<Palavra> words = new ArrayList<Palavra>();
	private double currentLineLength = 0;


	protected Linha(double larguraDoEspaco, double tamanhoDaFonte, double tamanhoLinhaMaximo) {
		this.spaceSize = larguraDoEspaco * tamanhoDaFonte;
		this.tamanhoLinhaMaximo = tamanhoLinhaMaximo;
	}

	protected boolean podeAdicionarPalavra(Palavra word) {
		double additionalLength = word.getLarguraDaPalavra();
		additionalLength += !words.isEmpty() ? spaceSize : 0;
		if (currentLineLength + additionalLength <= tamanhoLinhaMaximo) {
			words.add(word);
			currentLineLength += additionalLength;
			return true;
		} else {
			return false;
		}
	}


	protected double getTamanhoLinhaMaximo() {
		return tamanhoLinhaMaximo;
	}


	protected double getLineLength() {
		return currentLineLength;
	}


	protected List<Palavra> getWords() {
		return words;
	}

}
