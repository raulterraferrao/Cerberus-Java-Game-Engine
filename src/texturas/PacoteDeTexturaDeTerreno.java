package texturas;

public class PacoteDeTexturaDeTerreno {
	
	private TexturaDeTerreno texturaVermelha;
	private TexturaDeTerreno texturaVerde;
	private TexturaDeTerreno texturaAzul;
	private TexturaDeTerreno texturaPreta;
		
	public PacoteDeTexturaDeTerreno(TexturaDeTerreno texturaVermelha, TexturaDeTerreno texturaVerde,
			TexturaDeTerreno texturaAzul, TexturaDeTerreno texturaPreta) {

		this.texturaVermelha = texturaVermelha;
		this.texturaVerde = texturaVerde;
		this.texturaAzul = texturaAzul;
		this.texturaPreta = texturaPreta;
	}
	
	public TexturaDeTerreno getTexturaVermelha() {
		return texturaVermelha;
	}
	public void setTexturaVermelha(TexturaDeTerreno texturaVermelha) {
		this.texturaVermelha = texturaVermelha;
	}
	public TexturaDeTerreno getTexturaVerde() {
		return texturaVerde;
	}
	public void setTexturaVerde(TexturaDeTerreno texturaVerde) {
		this.texturaVerde = texturaVerde;
	}
	public TexturaDeTerreno getTexturaAzul() {
		return texturaAzul;
	}
	public void setTexturaAzul(TexturaDeTerreno texturaAzul) {
		this.texturaAzul = texturaAzul;
	}
	public TexturaDeTerreno getTexturaPreta() {
		return texturaPreta;
	}
	public void setTexturaPreta(TexturaDeTerreno texturaPreta) {
		this.texturaPreta = texturaPreta;
	}
	

	
}
