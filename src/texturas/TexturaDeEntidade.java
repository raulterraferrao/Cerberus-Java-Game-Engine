package texturas;

import luminosidades.Especular;

public class TexturaDeEntidade {

	private int texturaID;
	private Especular reflexo = new Especular();
	private boolean transparente = false;
	private boolean iluminosidadeFalsa = false;
	
	private int quantidadeDeLinhas = 1;
	
	public TexturaDeEntidade(int texturaID){
		this.texturaID = texturaID;
	}
	
	public int getTexturaID(){
		return texturaID;
	}

	public Especular getReflexo() {
		return reflexo;
	}

	public void setReflexo(Especular reflexo) {
		this.reflexo = reflexo;
	}
	
	public void setReflexo(float superficieReflexiva,float reflexividade) {
		this.reflexo.setSuperficieReflexiva(superficieReflexiva); 
		this.reflexo.setReflexividade(reflexividade);
	}

	public boolean isTransparente() {
		return transparente;
	}

	public void setTransparente(boolean transparente) {
		this.transparente = transparente;
	}

	public boolean isIluminosidadeFalsa() {
		return iluminosidadeFalsa;
	}

	public void setIluminosidadeFalsa(boolean iluminosidadeFalse) {
		this.iluminosidadeFalsa = iluminosidadeFalse;
	}

	public int getQuantidadeDeLinhas() {
		return quantidadeDeLinhas;
	}

	public void setQuantidadeDeLinhas(int quantidadeDeLinhas) {
		this.quantidadeDeLinhas = quantidadeDeLinhas;
	}
}
