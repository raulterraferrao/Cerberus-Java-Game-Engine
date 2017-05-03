package objetos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderizador.GerenciadorDeObjetos;

public class CarregarObjeto {

	public static Objeto carregarObjeto(String nomeArquivo,GerenciadorDeObjetos gerenciador){
			
			//Leitor do arquivo parecido com o getc ele le somente 2 Bytes do arquivo por vez
			//Nesse caso é necessário usar o BufferReader para armazenar maior quantidade de dados
			FileReader arq = null;
			try{
				arq = new FileReader(new File("img/"+nomeArquivo+".obj"));
			}catch(FileNotFoundException e){
				System.err.println("Não foi possivel abrir o arquivo dentro do CarregarObjetos");
				e.printStackTrace();
			}
			
			//Leitor que guardará o que ler no FileReader
			BufferedReader leitor = new BufferedReader(arq);
			//Responsável por armazenar uma linha
			String linha;
			
			List<Vector3f> vertices = new ArrayList<Vector3f>();
			List<Vector2f> texturas = new ArrayList<Vector2f>();
			List<Vector3f> normais = new ArrayList<Vector3f>();
			List<Integer> indices = new ArrayList<Integer>();
			
			//O GerenciadorDeObjetos recebe os dados como sendo float então teremos que criar os respectivos floats de nossos dados
			
			float[] vetorDeVertices = null;
			float[] vetorDeTexturas = null;
			float[] vetorDeNormais = null;
			int[] vetorDeIndices = null;
			
			try{
				
				while(true){
					linha = leitor.readLine();
					String[] dados = linha.split(" ");
					if(linha.startsWith("v ")){
						Vector3f vertice = new Vector3f(Float.parseFloat(dados[1]),Float.parseFloat(dados[2]),Float.parseFloat(dados[3]));
						vertices.add(vertice);
					}
					else if(linha.startsWith("vt ")){
						Vector2f textura = new Vector2f(Float.parseFloat(dados[1]),Float.parseFloat(dados[2]));
						texturas.add(textura);
					}
					else if(linha.startsWith("vn ")){
						Vector3f normal = new Vector3f(Float.parseFloat(dados[1]),Float.parseFloat(dados[2]),Float.parseFloat(dados[3]));
						normais.add(normal);
					}
					else if(linha.startsWith("f ")){
						vetorDeTexturas = new float[vertices.size() * 2];
						vetorDeNormais = new float[vertices.size() * 3];
						break;
					}
				}
				
				while(linha!= null){
					if(!linha.startsWith("f ")){
						linha = leitor.readLine();
						continue;
					}
					//Recebos os dados assim "f 10/2/9 2/3/4 3/4/1"
					String[] dados = linha.split(" ");
					
					//Os dados estão assim [f][10/2/9][2/3/4][3/4/1]
					//Cada vertice recebe os seguintes dados
					//v1 [10][2][9]...
					String[] vertice1 = dados[1].split("/");
					String[] vertice2 = dados[2].split("/");
					String[] vertice3 = dados[3].split("/");
					
					processarVertices(vertice1,indices,normais,texturas,vetorDeTexturas,vetorDeNormais);
					processarVertices(vertice2,indices,normais,texturas,vetorDeTexturas,vetorDeNormais);
					processarVertices(vertice3,indices,normais,texturas,vetorDeTexturas,vetorDeNormais);
					linha = leitor.readLine();
					
				}
				leitor.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}

			vetorDeVertices = new float[vertices.size()*3];
			vetorDeIndices = new int[indices.size()];
			
			
			int count = 0;
			
			for(Vector3f vertice : vertices){
				vetorDeVertices[count++] = vertice.x;
				vetorDeVertices[count++] = vertice.y;
				vetorDeVertices[count++] = vertice.z;
			}
			
			for(int i=0;i<indices.size();i++){
				vetorDeIndices[i] = indices.get(i);
			}
			
			return gerenciador.carregarParaVAO(vetorDeVertices, vetorDeTexturas,vetorDeNormais, vetorDeIndices);
	}
	
	
	private static void processarVertices(String[] dadosDosVertices,List<Integer> listaIndices,List<Vector3f> listaNormais,List<Vector2f> listaTexturas,float[] vetorTexturas,float[] vetorNormais){
		//Retiro o primeiro dado que correspondo ao numero do vertice, como o .obj numera o primeiro vertice com 1 e temos o array começando com 0 necessitamos subtrair 1 do resultado
		int verticeReal = (Integer.parseInt(dadosDosVertices[0]) - 1);
		//Coloca o verticeReal na lista de indices.
		listaIndices.add(verticeReal);
		//
		Vector2f texturaAtual = listaTexturas.get(Integer.parseInt(dadosDosVertices[1]) -1);
		vetorTexturas[verticeReal*2] = texturaAtual.x;
		//-1 antes da texturaAtual quer dizer que o blender o (0,0) começa no canto superior esquerdo e o opengl canto inferior direito
		vetorTexturas[verticeReal*2+1] = 1 - texturaAtual.y;
		
		Vector3f normaisAtual = listaNormais.get(Integer.parseInt(dadosDosVertices[2]) - 1);
		vetorNormais[verticeReal*3] = normaisAtual.x;
		vetorNormais[verticeReal*3+1] = normaisAtual.y;
		vetorNormais[verticeReal*3+2] = normaisAtual.z;
		
		
	}
					
			
		
}
