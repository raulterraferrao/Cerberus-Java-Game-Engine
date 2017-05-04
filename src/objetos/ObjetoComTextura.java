package objetos;

import texturas.Textura;

public class ObjetoComTextura {
	
	Textura textura;
	Objeto objeto;
	
	public ObjetoComTextura(Objeto objeto,Textura textura){
		this.objeto = objeto;
		this.textura = textura;
	}

	public Textura getTextura() {
		return textura;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	
}
