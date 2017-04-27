package objetos;

public class Objeto {

	private int vaoID;
	private int qtdVetices;
	
	public Objeto(int vaoID,int qtdVertices){
		
		this.vaoID = vaoID;
		this.qtdVetices = qtdVertices;
		
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getQtdVetices() {
		return qtdVetices;
	}

}
