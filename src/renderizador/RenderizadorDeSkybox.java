package renderizador;



import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gerenciadores.GerenciadorDeObjetos;
import gerenciadores.GerenciadorDeTempo;
import objetos.Objeto;
import shaders.ShaderSkybox;


public class RenderizadorDeSkybox {
	
	private static final int UNBIND = 0;
	
	private static final int POSICAO = 0;
	
	private static final float TAMANHO = 500f;
	
	private static final float[] VERTICES = {        
	    -TAMANHO,  TAMANHO, -TAMANHO,
	    -TAMANHO, -TAMANHO, -TAMANHO,
	    TAMANHO, -TAMANHO, -TAMANHO,
	     TAMANHO, -TAMANHO, -TAMANHO,
	     TAMANHO,  TAMANHO, -TAMANHO,
	    -TAMANHO,  TAMANHO, -TAMANHO,

	    -TAMANHO, -TAMANHO,  TAMANHO,
	    -TAMANHO, -TAMANHO, -TAMANHO,
	    -TAMANHO,  TAMANHO, -TAMANHO,
	    -TAMANHO,  TAMANHO, -TAMANHO,
	    -TAMANHO,  TAMANHO,  TAMANHO,
	    -TAMANHO, -TAMANHO,  TAMANHO,

	     TAMANHO, -TAMANHO, -TAMANHO,
	     TAMANHO, -TAMANHO,  TAMANHO,
	     TAMANHO,  TAMANHO,  TAMANHO,
	     TAMANHO,  TAMANHO,  TAMANHO,
	     TAMANHO,  TAMANHO, -TAMANHO,
	     TAMANHO, -TAMANHO, -TAMANHO,

	    -TAMANHO, -TAMANHO,  TAMANHO,
	    -TAMANHO,  TAMANHO,  TAMANHO,
	     TAMANHO,  TAMANHO,  TAMANHO,
	     TAMANHO,  TAMANHO,  TAMANHO,
	     TAMANHO, -TAMANHO,  TAMANHO,
	    -TAMANHO, -TAMANHO,  TAMANHO,

	    -TAMANHO,  TAMANHO, -TAMANHO,
	     TAMANHO,  TAMANHO, -TAMANHO,
	     TAMANHO,  TAMANHO,  TAMANHO,
	     TAMANHO,  TAMANHO,  TAMANHO,
	    -TAMANHO,  TAMANHO,  TAMANHO,
	    -TAMANHO,  TAMANHO, -TAMANHO,

	    -TAMANHO, -TAMANHO, -TAMANHO,
	    -TAMANHO, -TAMANHO,  TAMANHO,
	     TAMANHO, -TAMANHO, -TAMANHO,
	     TAMANHO, -TAMANHO, -TAMANHO,
	    -TAMANHO, -TAMANHO,  TAMANHO,
	     TAMANHO, -TAMANHO,  TAMANHO
	};
	
	private static String[] ARQUIVOS_SKYBOX = {"skyboxDiaDireita", "skyboxDiaEsquerda", "skyboxDiaCima", "skyboxDiaBaixo", "skyboxDiaTras", "skyboxDiaFrente"};
	private static String[] ARQUIVOS_SKYBOX_NOITE = {"skyboxNoiteDireita", "skyboxNoiteEsquerda", "skyboxNoiteCima", "skyboxNoiteBaixo", "skyboxNoiteTras", "skyboxNoiteFrente"};
	
	private Objeto cubo;
	private int texturaDia;
	private int texturaNoite;
	private int dia;
	private int noite;
	private float tempo;
	private float mistura;
	private ShaderSkybox shaderSkybox;
	
	public RenderizadorDeSkybox(ShaderSkybox shaderSkybox, GerenciadorDeObjetos gerenciador){
		cubo = gerenciador.carregarParaVAO(VERTICES, 3);
		texturaDia = gerenciador.carregarCubeMap(ARQUIVOS_SKYBOX);
		texturaNoite = gerenciador.carregarCubeMap(ARQUIVOS_SKYBOX_NOITE);
		this.shaderSkybox = shaderSkybox;
		shaderSkybox.iniciarPrograma();
		shaderSkybox.carregarCubeMap();
		shaderSkybox.carregarMatrizDeProjecao();
		shaderSkybox.fecharPrograma();
		
	}
	
	public void renderizar(){

		prepararSkybox();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cubo.getQtdVetices());
		unbindSkybox();

	}
	
	private void prepararSkybox(){
		GL30.glBindVertexArray(cubo.getVaoID());
		GL20.glEnableVertexAttribArray(POSICAO);
		cicloDiaNoite();
		bindTexturas();
		
	}
	
	private void unbindSkybox(){
		GL20.glDisableVertexAttribArray(POSICAO);
		GL30.glBindVertexArray(UNBIND);
	}
	
	public void bindTexturas(){
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, dia);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, noite);
		shaderSkybox.carregarMistura(mistura);
		
	}
	
	public void cicloDiaNoite(){
		tempo += GerenciadorDeTempo.getDelta() * 1000;
		tempo %= 24000;		
			if(tempo >= 0 && tempo < 5000){
				dia = texturaNoite;
				noite = texturaNoite;
				mistura = (tempo - 0)/(5000 - 0);
			}else if(tempo >= 5000 && tempo < 8000){
				dia = texturaNoite;
				noite = texturaDia;
				mistura = (tempo - 5000)/(8000 - 5000);
			}else if(tempo >= 8000 && tempo < 21000){
				dia = texturaDia;
				noite = texturaDia;
				mistura = (tempo - 8000)/(21000 - 8000);
			}else{
				dia = texturaDia;
				noite = texturaNoite;
				mistura = (tempo - 21000)/(24000 - 21000);
			}		
	}

}
