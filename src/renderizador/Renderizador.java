package renderizador;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entidades.Entidade;
import ferramentas.Matematica;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import shaders.StaticShader;

public class Renderizador {
	
	private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    
    private Matrix4f matrizDeProjecao;
    
	public Renderizador(StaticShader shader){
		criarMatrizDeProjecao();
		shader.iniciarPrograma();
		shader.carregarMatrizDeProjecao(matrizDeProjecao);
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
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		//Toda entidade tem sua matriz de transformação na qual terá a sua posição,rotação e escala.
		Matrix4f matrizDeTransformacao = Matematica.criarMatrizDeTransformacao(entidade.getPosicao(),
				entidade.getRotx(), entidade.getRoty(), entidade.getRotz(), entidade.getEscala());
		shader.carregarMatrizDeTransformacao(matrizDeTransformacao);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, objComTextura.getTextura().getTexturaID());
		// Função que desenha na tela, iremos desenhar triangulos,Os dados do
		// VBO começa na pos 0
		// GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, obj.getQtdVetices());
		// Nova fun��o utilizada para que o VBO de Indices funcione
		GL11.glDrawElements(GL11.GL_TRIANGLES, objComum.getQtdVetices(), GL11.GL_UNSIGNED_INT, 0);
		// Desativamos a posição 0 do VAO onde contém os dados do VBO pois
		// paramos de mexer
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		// Terminamos de usar o VAO logo devemos dar unbind nele
		GL30.glBindVertexArray(0);
	}
	//Essa matriz é responsável por criar a projeção fazendo com que pareça mais real com o ponto de fuga etc.
	//Ela também faz com que ao aumentar o eixo z, a imagem se afasta.
    private void criarMatrizDeProjecao(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        matrizDeProjecao = new Matrix4f();
        matrizDeProjecao.m00 = x_scale;
        matrizDeProjecao.m11 = y_scale;
        matrizDeProjecao.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        matrizDeProjecao.m23 = -1;
        matrizDeProjecao.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        matrizDeProjecao.m33 = 0;
    }
}
