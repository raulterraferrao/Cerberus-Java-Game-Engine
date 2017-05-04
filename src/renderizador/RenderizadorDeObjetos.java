package renderizador;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entidades.Entidade;
import estruturasDeDados.Matriz4f;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import shaders.StaticShader;
import texturas.Textura;

public class RenderizadorDeObjetos {
	
	private static final int UNBIND = 0;
	
	private static final int POSICAO = 0;
	private static final int TEXTURA = 1;
	private static final int NORMAL = 2;
	
	private StaticShader shader;
	    
	public RenderizadorDeObjetos(StaticShader shader){
		this.shader = shader;
		shader.iniciarPrograma();
		shader.carregarMatrizDeProjecao();
		shader.fecharPrograma();
	}
    


	public void renderizar(Map<ObjetoComTextura,List<Entidade>>  entidades){
		for(ObjetoComTextura modelo : entidades.keySet()){
			prepararObjetoComTextura(modelo);
			List<Entidade> lote = entidades.get(modelo);
			for(Entidade entidade : lote){
				prepararInstancia(entidade);
				GL11.glDrawElements(GL11.GL_TRIANGLES, modelo.getObjeto().getQtdVetices(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			unbindModeloDeTextura();
		}
	}
	
	public void prepararObjetoComTextura(ObjetoComTextura modelo){

		Objeto objComum = modelo.getObjeto();
		GL30.glBindVertexArray(objComum.getVaoID());
		GL20.glEnableVertexAttribArray(POSICAO);
		GL20.glEnableVertexAttribArray(TEXTURA);
		GL20.glEnableVertexAttribArray(NORMAL);
		
		Textura textura = modelo.getTextura();
		if(textura.isTransparente()){
			Renderizador.desabilitarCullFace();
		}
		shader.carregarIluminosidadeFalsa(textura);
		shader.carregarLuminosidadeEspecular(textura.getReflexo());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelo.getTextura().getTexturaID());
	}
	
	public void prepararInstancia(Entidade entidade){
		
		shader.carregarMatrizDeTransformacao(entidade);
	}
	
	public void unbindModeloDeTextura(){
		Renderizador.habilitarCullFace();
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
