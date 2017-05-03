package texturas;

import luminosidades.Especular;

public class ModeloDeTextura {

	private int texturaID;
	private Especular reflexo = new Especular();
	
	public ModeloDeTextura(int texturaID){
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
}
