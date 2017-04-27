package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
/***
 * Essa � uma classe abstrata que retrata o que todo Shader precisa ter
 * 
 *
 */
public abstract class ProgramaShaderPadrao {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	//Matriz 4 por 4 com 16 elementos
	private static FloatBuffer matriz4fBuffer = BufferUtils.createFloatBuffer(16);
	
	
	public ProgramaShaderPadrao(String arquivoVertex,String arquivoFragment){
		//Carregamos o vertex e fragment shader na GPU e retornamos os seus respectivos IDs
		vertexShaderID = carregarShader(arquivoVertex, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = carregarShader(arquivoFragment, GL20.GL_FRAGMENT_SHADER);
		//Criamos o programa que ir� rodar o shader e recebemos o seu ID
		programID = GL20.glCreateProgram();
		//Conectamos o vertex e fragment shader no programa
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		//Aqui conectamos o programa com o VAO em que queremos utilizar os shaders
		conectarAtributos();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getLocalidadeDeTodosUniform();
		
	}
	
	protected abstract void conectarAtributos();
	
	protected int getLocalidadeUniform(String nomeUniform){
		return GL20.glGetUniformLocation(programID, nomeUniform);
	}
	
	protected abstract void getLocalidadeDeTodosUniform();
	
	
	
	protected void conectarAtributo(int atributo,String nomeDaVariavel){
		GL20.glBindAttribLocation(programID, atributo, nomeDaVariavel);
	}
	
	public void iniciarPrograma(){
		GL20.glUseProgram(programID);
	}
	
	public void fecharPrograma(){
		GL20.glUseProgram(0);
	}
	
	public void freePrograma(){
		fecharPrograma();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	/***
	 * Metodo respons�vem em carregar o caminho(Path) dos shaders para a gpu e retorna o seu respectivo ID
	 * @param arquivo caminho do arquivo
	 * @param tipo type OpenGL
	 * @return shaderID
	 */
	private static int carregarShader(String arquivo,int tipo){
		  StringBuilder shaderSource = new StringBuilder();
		  try{
		   BufferedReader caminho = new BufferedReader(new FileReader(arquivo));
		   String line;
		   while((line = caminho.readLine())!=null){
		    shaderSource.append(line).append("//\n");
		   }
		   caminho.close();
		  }catch(IOException e){
		   e.printStackTrace();
		   System.exit(-1);
		  }
		  int shaderID = GL20.glCreateShader(tipo);
		  GL20.glShaderSource(shaderID, shaderSource);
		  GL20.glCompileShader(shaderID);
		  if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
		   System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
		   System.err.println("N�o pode compilar o arquivo shader!");
		   System.exit(-1);
		  }
		  return shaderID;
		 
	}
	
	//Métodos usados para carregar variáveis para os Uniforms equivalentes
	protected void carregarFloat(int localidade,float valor){
		GL20.glUniform1f(localidade, valor);
	}
	protected void carregarVetor3f(int localidade,Vector3f vetor){
		GL20.glUniform3f(localidade, vetor.x, vetor.y, vetor.z);
	}
	protected void carregarBooleano(int localidade,boolean bool){
		float valor = 0;
		if(bool){
			valor = 1;
		}
		GL20.glUniform1f(localidade, valor);
	}
	protected void carregarMatriz4f(int localidade,Matrix4f matriz){
		matriz.store(matriz4fBuffer);
		matriz4fBuffer.flip();
		GL20.glUniformMatrix4(localidade, false, matriz4fBuffer);
	}
	
}
