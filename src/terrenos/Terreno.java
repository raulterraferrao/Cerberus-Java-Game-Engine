package terrenos;

import objetos.Objeto;
import renderizador.GerenciadorDeObjetos;
import texturas.PacoteDeTexturaDeTerreno;
import texturas.TexturaDeTerreno;

public class Terreno {

	private static final int TAMANHO = 800;
	private static final int QTD_VERTICES = 128;
	
	private float x;
	private float z;
	
	private Objeto modelo;
	private PacoteDeTexturaDeTerreno texturas;
	private TexturaDeTerreno texturaDeMistura;
	
	public Terreno(float malhaX, float malhaZ,GerenciadorDeObjetos gerenciador, PacoteDeTexturaDeTerreno texturas,
			TexturaDeTerreno texturaDeMistura) {
		this.x = malhaX * TAMANHO;
		this.z = malhaZ * TAMANHO;
		this.modelo = gerarTerreno(gerenciador);
		this.texturas = texturas;
		this.texturaDeMistura = texturaDeMistura;
	}
	
	private Objeto gerarTerreno(GerenciadorDeObjetos gerenciador){
		int count = QTD_VERTICES * QTD_VERTICES;
		float[] vertices = new float[count * 3];
		float[] normais = new float[count * 3];
		float[] coordenadasDaTextura = new float[count*2];
		int[] indices = new int[6*(QTD_VERTICES-1)*(QTD_VERTICES-1)];
		int vertexPointer = 0;
		for(int i=0;i<QTD_VERTICES;i++){
			for(int j=0;j<QTD_VERTICES;j++){
				vertices[vertexPointer*3] = (float)j/((float)QTD_VERTICES - 1) * TAMANHO;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)i/((float)QTD_VERTICES - 1) * TAMANHO;
				normais[vertexPointer*3] = 0;
				normais[vertexPointer*3+1] = 1;
				normais[vertexPointer*3+2] = 0;
				coordenadasDaTextura[vertexPointer*2] = (float)j/((float)QTD_VERTICES - 1);
				coordenadasDaTextura[vertexPointer*2+1] = (float)i/((float)QTD_VERTICES - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<QTD_VERTICES-1;gz++){
			for(int gx=0;gx<QTD_VERTICES-1;gx++){
				int topLeft = (gz*QTD_VERTICES)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*QTD_VERTICES)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return gerenciador.carregarParaVAO(vertices, coordenadasDaTextura, normais, indices);
	}
	
	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public Objeto getModelo() {
		return modelo;
	}

	public PacoteDeTexturaDeTerreno getTexturas() {
		return texturas;
	}

	public void setTexturas(PacoteDeTexturaDeTerreno texturas) {
		this.texturas = texturas;
	}

	public TexturaDeTerreno getTexturaDeMistura() {
		return texturaDeMistura;
	}

	public void setTexturaDeMistura(TexturaDeTerreno texturaDeMistura) {
		this.texturaDeMistura = texturaDeMistura;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void setModelo(Objeto modelo) {
		this.modelo = modelo;
	}
	
	
	
}
