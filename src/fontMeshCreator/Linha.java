package fontMeshCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a line of text during the loading of a text.
 * 
 * @author Karl
 *
 */
public class Linha {

	private double tamanhoLinhaMaximo;
	private double spaceSize;

	private List<Palavra> words = new ArrayList<Palavra>();
	private double currentLineLength = 0;

	/**
	 * Creates an empty line.
	 * 
	 * @param spaceWidth
	 *            - the screen-space width of a space character.
	 * @param fontSize
	 *            - the size of font being used.
	 * @param maxLength
	 *            - the screen-space maximum length of a line.
	 */
	protected Linha(double larguraDoEspaco, double tamanhoDaFonte, double tamanhoLinhaMaximo) {
		this.spaceSize = larguraDoEspaco * tamanhoDaFonte;
		this.tamanhoLinhaMaximo = tamanhoLinhaMaximo;
	}

	/**
	 * Attempt to add a word to the line. If the line can fit the word in
	 * without reaching the maximum line length then the word is added and the
	 * line length increased.
	 * 
	 * @param word
	 *            - the word to try to add.
	 * @return {@code true} if the word has successfully been added to the line.
	 */
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

	/**
	 * @return The max length of the line.
	 */
	protected double getTamanhoLinhaMaximo() {
		return tamanhoLinhaMaximo;
	}

	/**
	 * @return The current screen-space length of the line.
	 */
	protected double getLineLength() {
		return currentLineLength;
	}

	/**
	 * @return The list of words in the line.
	 */
	protected List<Palavra> getWords() {
		return words;
	}

}
