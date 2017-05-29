package renderizador;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gerenciadores.GerenciadorDeObjetos;
import gui.TexturaGUI;
import objetos.Objeto;
import shaders.ShaderGUI;

public class RenderizadorGUI {

	private static final int UNBIND = 0;	
	private static final int POSICAO = 0;
	
	private final Objeto quadro;
	private ShaderGUI shaderGUI;
	
	public RenderizadorGUI(ShaderGUI shaderGUI, GerenciadorDeObjetos gerenciador){

		this.shaderGUI = shaderGUI;
		float vertices[] = {-1, 1, -1, -1, 1, 1, 1, -1};
		quadro = gerenciador.carregarParaVAO(vertices, 2);
		
	}
	
	public void renderizar(List<TexturaGUI>  guis){
		
		prepararGUI();
		for(TexturaGUI gui: guis){
			bindTexturas(gui);
			transformarGUI(gui);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quadro.getQtdVetices());
		}
		unbindGUI();
		
	}

	private void prepararGUI(){
		GL30.glBindVertexArray(quadro.getVaoID());
		GL20.glEnableVertexAttribArray(POSICAO);
		
		//Funções utilizadas para transparencia de textura
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//Desabilitar depth test para que uma gui nao impessa a outra de aparecer
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private void transformarGUI(TexturaGUI gui){
		
		shaderGUI.carregarMatrizDeTransformacao(gui);
	}
	
	public void bindTexturas(TexturaGUI gui){
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexturaID());
		
	}
	
	
	private void unbindGUI(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(POSICAO);
		GL30.glBindVertexArray(UNBIND);
	}
}
