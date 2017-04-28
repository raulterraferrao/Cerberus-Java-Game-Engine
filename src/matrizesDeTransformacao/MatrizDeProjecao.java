package matrizesDeTransformacao;

import org.lwjgl.opengl.Display;

import estruturasDeDados.Matriz4f;

//Essa matriz é responsável por criar a projeção fazendo com que pareça mais real com o ponto de fuga etc.
//Ela também faz com que ao aumentar o eixo z, a imagem se afasta.

public class MatrizDeProjecao {
		
		private static final float FOV = 70;
	    private static final float NEAR_PLANE = 0.1f;
	    private static final float FAR_PLANE = 1000;
	    
	    private static Matriz4f matrizDeProjecao;
	    
	    public static void criarMatrizDeProjecao(){
	        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
	        float x_scale = y_scale / aspectRatio;
	        float frustum_length = FAR_PLANE - NEAR_PLANE;
	 
	        matrizDeProjecao = new Matriz4f();
	        matrizDeProjecao.m00 = x_scale;
	        matrizDeProjecao.m11 = y_scale;
	        matrizDeProjecao.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
	        matrizDeProjecao.m23 = -1;
	        matrizDeProjecao.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
	        matrizDeProjecao.m33 = 0;
	    }

		public static Matriz4f getMatrizDeProjecao() {
			return matrizDeProjecao;
		}

		public static void setMatrizDeProjecao(Matriz4f matrizDeProjecao) {
			MatrizDeProjecao.matrizDeProjecao = matrizDeProjecao;
		}
}
