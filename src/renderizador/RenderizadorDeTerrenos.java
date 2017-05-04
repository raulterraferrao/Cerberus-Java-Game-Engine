package renderizador;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import objetos.Objeto;
import shaders.ShaderTerreno;
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
	
}
