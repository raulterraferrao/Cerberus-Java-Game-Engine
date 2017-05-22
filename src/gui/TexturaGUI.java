package gui;

import estruturasDeDados.Vetor2f;

public class TexturaGUI {

	private Vetor2f posicao;
	private Vetor2f escala;
	private int texturaID;
	
	
	public TexturaGUI(Vetor2f posicao, Vetor2f escala, int texturaID) {
		this.posicao = posicao;
		this.escala = escala;
		this.texturaID = texturaID;
	}
	
	public Vetor2f getPosicao() {
		return posicao;
	}
	public void setPosicao(Vetor2f posicao) {
		this.posicao = posicao;
	}
	public Vetor2f getEscala() {
		return escala;
	}
	public void setEscala(Vetor2f escala) {
		this.escala = escala;
	}
	public int getTexturaID() {
		return texturaID;
	}
	public void setTexturaID(int texturaID) {
		this.texturaID = texturaID;
	}
	
	
}
