package terrenos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import estruturasDeDados.Vetor3f;
import objetos.Objeto;
import renderizador.GerenciadorDeObjetos;
import texturas.PacoteDeTexturaDeTerreno;
import texturas.TexturaDeTerreno;

public class Terreno {

	private static final String LOCAL = "img/";
	private static final String EXTENSAO = ".png";
	
	private static final int TAMANHO = 800;
	private static final int ALTURA_MAX = 40;
	private static final int COR_MAX = 256 * 256 * 256;
	
	private float x;
	private float z;
	
	private Objeto modelo;
	private PacoteDeTexturaDeTerreno texturas;
	private TexturaDeTerreno texturaDeMistura;
	
	public Terreno(float malhaX, float malhaZ,GerenciadorDeObjetos gerenciador, PacoteDeTexturaDeTerreno texturas,
			TexturaDeTerreno texturaDeMistura, String mapaDeAltura) {
		this.x = malhaX * TAMANHO;
		this.z = malhaZ * TAMANHO;
		this.modelo = gerarTerreno(gerenciador, mapaDeAltura);
		this.texturas = texturas;
		this.texturaDeMistura = texturaDeMistura;
	}
	
	private Objeto gerarTerreno(GerenciadorDeObjetos gerenciador, String mapaDeAltura){
		
		BufferedImage imagem = null;
		try {
			imagem = ImageIO.read(new File(LOCAL + mapaDeAltura + EXTENSAO));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int qtdVertices = imagem.getHeight();
		
		int count = qtdVertices * qtdVertices;
		float[] vertices = new float[count * 3];
		float[] normais = new float[count * 3];
		float[] coordenadasDaTextura = new float[count*2];
		int[] indices = new int[6*(qtdVertices-1)*(qtdVertices-1)];
		int vertexPointer = 0;
		for(int i=0;i<qtdVertices;i++){
			for(int j=0;j<qtdVertices;j++){
				vertices[vertexPointer*3] = (float)j/((float)qtdVertices - 1) * TAMANHO;
				vertices[vertexPointer*3+1] = getAltura(i, j, imagem);
				vertices[vertexPointer*3+2] = (float)i/((float)qtdVertices - 1) * TAMANHO;
				Vetor3f normal = calcularNormal(i,j,imagem);
				normais[vertexPointer*3] = normal.x;
				normais[vertexPointer*3+1] = normal.y;
				normais[vertexPointer*3+2] = normal.z;
				coordenadasDaTextura[vertexPointer*2] = (float)j/((float)qtdVertices - 1);
				coordenadasDaTextura[vertexPointer*2+1] = (float)i/((float)qtdVertices - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<qtdVertices-1;gz++){
			for(int gx=0;gx<qtdVertices-1;gx++){
				int topLeft = (gz*qtdVertices)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*qtdVertices)+gx;
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
	
	public float getAltura(int x, int z, BufferedImage imagem){
		
		if(z < 0 || z >= imagem.getHeight() || x < 0 || x >= imagem.getWidth())
			return 0;
		
		float altura = imagem.getRGB(x, z);
		altura += COR_MAX / 2f;
		altura /= COR_MAX / 2f;
		altura *= ALTURA_MAX ;
		
		return altura;
		
	}
	
	public Vetor3f calcularNormal(int x, int z, BufferedImage imagem){
		float alturaO, alturaL, alturaS, alturaN;
		
		if(x==0){
			alturaO = getAltura(x, z, imagem);		
		}else{
			alturaO = getAltura(x-1, z, imagem);		
		}
		
		if(x>=imagem.getHeight()){
			alturaL = getAltura(x, z, imagem);
		}else{
			alturaL = getAltura(x+1, z, imagem);
		}
		
		if(z==0){
			alturaN = getAltura(x, z, imagem);
		}else{
			alturaN = getAltura(x, z-1, imagem);
		}
		
		if(z>=imagem.getHeight()){
			alturaS = getAltura(x, z, imagem);
		}else{
			alturaS = getAltura(x, z+1, imagem);
		}
		
		Vetor3f normal = new Vetor3f(alturaL - alturaO, 2f ,alturaS - alturaN);
		normal.normalizar();
		
		return normal;
		
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
