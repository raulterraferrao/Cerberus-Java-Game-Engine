package matrizesDeTransformacao;

import entidades.Camera;
import estruturasDeDados.Matriz4f;
import estruturasDeDados.Vetor3f;

public class MatrizDeVisualizacao {
	/***
	 * Matriz responsável pela Visialização da Camera, recebe uma camera como parametro.
	 * @param camera
	 * @return
	 */
	public static Matriz4f criarMatrizDeVisualizacao(Camera camera){
		Matriz4f matrizDeVisualizacao = new Matriz4f();
		matrizDeVisualizacao.setIdentidade();
		Matriz4f.rotacionar((float) Math.toRadians(camera.getPitch()),new Vetor3f(1,0,0), matrizDeVisualizacao, matrizDeVisualizacao);
		Matriz4f.rotacionar((float) Math.toRadians(camera.getYaw()),new Vetor3f(0,1,0), matrizDeVisualizacao, matrizDeVisualizacao);
		//Matrix4f.rotate((float) Math.toRadians(rz),new Vector3f(0,0,1), matrizDeTransformacao, matrizDeTransformacao);
		Vetor3f pos = camera.getPosicao();
		Vetor3f pos_negativo = new Vetor3f(-pos.x,-pos.y,-pos.z);
		Matriz4f.transladar(pos_negativo, matrizDeVisualizacao,matrizDeVisualizacao);
		return matrizDeVisualizacao;
	}
}
