package luminosidades;

public class Especular {
	
	private float superficieReflexiva;
	private float reflexividade;
	
	public Especular(){
		this.superficieReflexiva = 1;
		this.reflexividade = 0;
	}
	
	public Especular(float superficieReflexiva, float reflexividade){
		this.superficieReflexiva = superficieReflexiva;
		this.reflexividade = reflexividade;
	}

	public float getSuperficieReflexiva() {
		return superficieReflexiva;
	}

	public void setSuperficieReflexiva(float superficieReflexiva) {
		this.superficieReflexiva = superficieReflexiva;
	}

	public float getReflexividade() {
		return reflexividade;
	}

	public void setReflexividade(float reflexividade) {
		this.reflexividade = reflexividade;
	}
}
