package texturas;

import java.nio.ByteBuffer;

public class Textura {
	
	private int largura;
	private int altura;
	private ByteBuffer buffer;
	
	public Textura(ByteBuffer buffer, int largura, int altura){
		this.buffer = buffer;
		this.largura = largura;
		this.altura = altura;
	}
	
	public int getLargura(){
		return largura;
	}
	
	public int getAltura(){
		return altura;
	}
	
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
