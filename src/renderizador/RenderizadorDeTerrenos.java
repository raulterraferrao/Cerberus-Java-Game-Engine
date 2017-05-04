package renderizador;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entidades.Entidade;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import shaders.ShaderTerreno;
import shaders.StaticShader;
import terrenos.Terreno;
import texturas.Textura;

public class RenderizadorDeTerrenos {

	private static final int UNBIND = 0;
	
	private static final int POSICAO = 0;
	private static final int TEXTURA = 1;
	private static final int NORMAL = 2;
	
	private ShaderTerreno shaderTerreno;
    
	public RenderizadorDeTerrenos(ShaderTerreno shaderTerreno){
		this.shaderTerreno = shaderTerreno;
		shaderTerreno.iniciarPrograma();
		shaderTerreno.carregarMatrizDeProjecao();
		shaderTerreno.fecharPrograma();
	}
	
	public void renderizar(List<Terreno>  terrenos){
		for(Terreno terreno: terrenos){
			prepararTerreno(terreno);
			transformarTerreno(terreno);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terreno.getModelo().getQtdVetices(), GL11.GL_UNSIGNED_INT, 0);
			unbindTerreno();
		}
	}
	
	public void prepararTerreno(Terreno terreno){

		Objeto modeloTerreno = terreno.getModelo();
		GL30.glBindVertexArray(modeloTerreno.getVaoID());
		GL20.glEnableVertexAttribArray(POSICAO);
		GL20.glEnableVertexAttribArray(TEXTURA);
		GL20.glEnableVertexAttribArray(NORMAL);
		
		Textura textura = terreno.getTextura();
		shaderTerreno.carregarLuminosidadeEspecular(textura.getReflexo());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terreno.getTextura().getTexturaID());
	}
	
	public void transformarTerreno(Terreno terreno){
		
		shaderTerreno.carregarMatrizDeTransformacao(terreno);
	}
	
	public void unbindTerreno(){
		GL20.glDisableVertexAttribArray(POSICAO);
		GL20.glDisableVertexAttribArray(TEXTURA);
		GL20.glDisableVertexAttribArray(NORMAL);
		// Terminamos de usar o VAO logo devemos dar unbind nele
		GL30.glBindVertexArray(UNBIND);
	}
	
	public void renderizar(Entidade entidade, StaticShader shader) {

		
		ObjetoComTextura objComTextura = entidade.getObjetoComTextura();
		Objeto objComum = objComTextura.getObjeto();
		// Como vamos mexer no VAO devemos usar o Bind
		GL30.glBindVertexArray(objComum.getVaoID());
		// Nós ativamos a posição 0 do VAO onde contém os dados do VBO
		GL20.glEnableVertexAttribArray(POSICAO);
		GL20.glEnableVertexAttribArray(TEXTURA);
		GL20.glEnableVertexAttribArray(NORMAL);
		
		
		//Toda entidade tem sua matriz de transformação na qual terá a sua posição,rotação e escala.
		
		shader.carregarMatrizDeTransformacao(entidade);

		//Carregar iluminosidade Especular
		
		Textura textura = objComTextura.getTextura();
		shader.carregarLuminosidadeEspecular(textura.getReflexo());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, objComTextura.getTextura().getTexturaID());
		// Função que desenha na tela, iremos desenhar triangulos,Os dados do
		// Nova fun��o utilizada para que o VBO de Indices funcione
		GL11.glDrawElements(GL11.GL_TRIANGLES, objComum.getQtdVetices(), GL11.GL_UNSIGNED_INT, 0);
		// Desativamos a posição 0 do VAO onde contém os dados do VBO pois
		// paramos de mexer
		GL20.glDisableVertexAttribArray(POSICAO);
		GL20.glDisableVertexAttribArray(TEXTURA);
		GL20.glDisableVertexAttribArray(NORMAL);
		// Terminamos de usar o VAO logo devemos dar unbind nele
		GL30.glBindVertexArray(UNBIND);
	}
}
