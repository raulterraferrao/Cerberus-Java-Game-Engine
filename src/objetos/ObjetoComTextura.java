package objetos;

import texturas.ModeloDeTextura;

public class ObjetoComTextura {
	
	ModeloDeTextura textura;
	Objeto objeto;
	
	public ObjetoComTextura(Objeto objeto,ModeloDeTextura textura){
		this.objeto = objeto;
		this.textura = textura;
	}

	public ModeloDeTextura getTextura() {
		return textura;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	
}
