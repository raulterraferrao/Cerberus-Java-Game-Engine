package estruturasDeDados;

public class Matriz4f {

	private float[][] matriz;
	
	public Matriz4f(){
		
		matriz = new float[4][4];
	}

	public Matriz4f setIdentidade(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(j==i)
					matriz[i][j] = 1;
				else
					matriz[i][j] = 0;
			}
		}
		
		return this;
	}
	
	public Matriz4f multiplicar(Matriz4f m){
		
		Matriz4f resultado = new Matriz4f();
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				resultado.setMatrizPos(i, j, matriz[i][0] * m.getMatrizPos(0, j) +
											 matriz[i][1] * m.getMatrizPos(1, j) +
											 matriz[i][2] * m.getMatrizPos(2, j) +
											 matriz[i][3] * m.getMatrizPos(3, j));
			}
		}
		
		return resultado;
	}
	
	public float[][] getMatriz() {
		return matriz;
	}
	
	public float getMatrizPos(int i, int j){
		return matriz[i][j];
	}

	public void setMatriz(float[][] matriz) {
		this.matriz = matriz;
	}
	
	public void setMatrizPos(int i, int j, float valor){
		matriz[i][j] = valor;
	}
	
}
