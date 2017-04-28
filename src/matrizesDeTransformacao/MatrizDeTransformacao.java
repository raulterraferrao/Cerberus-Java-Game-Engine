package matrizesDeTransformacao;

import estruturasDeDados.Matriz4f;
import estruturasDeDados.Vetor3f;

public class MatrizDeTransformacao {
	/***
	 * Matriz responsável por transformar uma entidade, movendo a sua posição,rotação ou escala
	 * @param posicao
	 * @param rx
	 * @param ry
	 * @param rz
	 * @param escala
	 * @return
	 */
	public static Matriz4f criarMatrizDeTransformacao(Vetor3f posicao,float rx,float ry,float rz,float escala){
		Matriz4f matrizDeTransformacao = new Matriz4f();
		matrizDeTransformacao.setIdentidade();
		Matriz4f.transladar(posicao, matrizDeTransformacao,matrizDeTransformacao);
		Matriz4f.rotacionar((float) Math.toRadians(rx),new Vetor3f(1,0,0), matrizDeTransformacao, matrizDeTransformacao);
		Matriz4f.rotacionar((float) Math.toRadians(ry),new Vetor3f(0,1,0), matrizDeTransformacao, matrizDeTransformacao);
		Matriz4f.rotacionar((float) Math.toRadians(rz),new Vetor3f(0,0,1), matrizDeTransformacao, matrizDeTransformacao);
		Matriz4f.escalar(new Vetor3f(escala,escala,escala),matrizDeTransformacao,matrizDeTransformacao);
		
		return matrizDeTransformacao;
	}
}
