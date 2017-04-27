package ferramentas;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entidades.Camera;

public class Matematica {

	/***
	 * Matriz responsável por transformar uma entidade, movendo a sua posição,rotação ou escala
	 * @param posicao
	 * @param rx
	 * @param ry
	 * @param rz
	 * @param escala
	 * @return
	 */
	public static Matrix4f criarMatrizDeTransformacao(Vector3f posicao,float rx,float ry,float rz,float escala){
		Matrix4f matrizDeTransformacao = new Matrix4f();
		matrizDeTransformacao.setIdentity();
		Matrix4f.translate(posicao, matrizDeTransformacao,matrizDeTransformacao);
		Matrix4f.rotate((float) Math.toRadians(rx),new Vector3f(1,0,0), matrizDeTransformacao, matrizDeTransformacao);
		Matrix4f.rotate((float) Math.toRadians(ry),new Vector3f(0,1,0), matrizDeTransformacao, matrizDeTransformacao);
		Matrix4f.rotate((float) Math.toRadians(rz),new Vector3f(0,0,1), matrizDeTransformacao, matrizDeTransformacao);
		Matrix4f.scale(new Vector3f(escala,escala,escala),matrizDeTransformacao,matrizDeTransformacao);
		
		return matrizDeTransformacao;
	}
	/***
	 * Matriz responsável pela Visialização da Camera, recebe uma camera como parametro.
	 * @param camera
	 * @return
	 */
	public static Matrix4f criarMatrizDeVisualizacao(Camera camera){
		Matrix4f matrizDeVisualizacao = new Matrix4f();
		matrizDeVisualizacao.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()),new Vector3f(1,0,0), matrizDeVisualizacao, matrizDeVisualizacao);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()),new Vector3f(0,1,0), matrizDeVisualizacao, matrizDeVisualizacao);
		//Matrix4f.rotate((float) Math.toRadians(rz),new Vector3f(0,0,1), matrizDeTransformacao, matrizDeTransformacao);
		Vector3f pos = camera.getPosicao();
		Vector3f pos_negativo = new Vector3f(-pos.x,-pos.y,-pos.z);
		Matrix4f.translate(pos_negativo, matrizDeVisualizacao,matrizDeVisualizacao);
		return matrizDeVisualizacao;
	}
}
