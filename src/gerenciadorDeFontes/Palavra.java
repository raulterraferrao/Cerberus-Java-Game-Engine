package gerenciadorDeFontes;

import java.util.ArrayList;
import java.util.List;


public class Palavra {
	
	private List<Letra> letras = new ArrayList<Letra>();
	private double largura = 0;
	private double tamanhoDaFonte;
	

	protected Palavra(double tamanhoDaFonte){
		this.tamanhoDaFonte = tamanhoDaFonte;
	}
	

	protected void adicionarLetra(Letra letra){
		letras.add(letra);
		largura += letra.getxAdvance() * tamanhoDaFonte;
	}
	

	protected List<Letra> getLetras(){
		return letras;
	}
	

	protected double getLarguraDaPalavra(){
		return largura;
	}

}
