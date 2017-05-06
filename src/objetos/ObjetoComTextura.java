package objetos;

import texturas.TexturaDeEntidade;

public class ObjetoComTextura {
	
	TexturaDeEntidade textura;
	Objeto objeto;
	
	public ObjetoComTextura(Objeto objeto,TexturaDeEntidade textura){
		this.objeto = objeto;
		this.textura = textura;
	}

	public TexturaDeEntidade getTextura() {
		return textura;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	
}
