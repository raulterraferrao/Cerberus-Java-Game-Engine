package renderizador;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entidades.Entidade;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import shaders.StaticShader;

public class Renderizador {
	
	private static final int UNBIND = 0;
	
	private static final int POSICAO = 0;
	private static final int TEXTURA = 1;
	private static final int NORMAL = 2;
	    
	public Renderizador(StaticShader shader){
		shader.iniciarPrograma();
		shader.carregarMatrizDeProjecao();
		shader.fecharPrograma();
	}
    
    
	/***
	 * Retira todas as cores do frame passado e coloca a nova cor de background
	 * que nós setarmos
	 * 
	 **/
	public void limpar() {

		//Necessito falar pro opengl verificar qual triangulo está na frente.
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(1, 1, 1, 0.7f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
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
