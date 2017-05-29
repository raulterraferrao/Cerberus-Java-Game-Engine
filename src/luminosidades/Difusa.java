package luminosidades;

import estruturasDeDados.Vetor3f;

public class Difusa {
	
	private Vetor3f posicao;
	private Vetor3f cor;
	private Vetor3f atenuacao = new Vetor3f(1, 0, 0);	

	public Difusa(Vetor3f pos,Vetor3f cor){
		this.posicao = pos;
		this.cor = cor;
	}
	
	public Difusa(Vetor3f pos,Vetor3f cor, Vetor3f atenuacao){
		this.posicao = pos;
		this.cor = cor;
		this.atenuacao = atenuacao;
	}

	public Vetor3f getPosicao() {
		return posicao;
	}

	public void setPosicao(Vetor3f posicao) {
		this.posicao = posicao;
	}

	public Vetor3f getCor() {
		return cor;
	}

	public void setCor(Vetor3f cor) {
		this.cor = cor;
	}

	public Vetor3f getAtenuacao() {
		return atenuacao;
	}

	public void setAtenuacao(Vetor3f atenuacao) {
		this.atenuacao = atenuacao;
	}
	
	
}
