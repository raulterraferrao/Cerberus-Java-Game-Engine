package fontMeshCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * During the loading of a text this represents one word in the text.
 * @author Karl
 *
 */
public class Palavra {
	
	private List<Letra> letras = new ArrayList<Letra>();
	private double largura = 0;
	private double tamanhoDaFonte;
	
	/**
	 * Create a new empty word.
	 * @param fontSize - the font size of the text which this word is in.
	 */
	protected Palavra(double tamanhoDaFonte){
		this.tamanhoDaFonte = tamanhoDaFonte;
	}
	
	/**
	 * Adds a character to the end of the current word and increases the screen-space width of the word.
	 * @param character - the character to be added.
	 */
	protected void adicionarLetra(Letra letra){
		letras.add(letra);
		largura += letra.getxAdvance() * tamanhoDaFonte;
	}
	
	/**
	 * @return The list of characters in the word.
	 */
	protected List<Letra> getLetras(){
		return letras;
	}
	
	/**
	 * @return The width of the word in terms of screen size.
	 */
	protected double getLarguraDaPalavra(){
		return largura;
	}

}
