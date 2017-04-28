package estruturasDeDados;

public class Vetor2f {

	public float x;
	public float y;
	
	public Vetor2f(float x, float y) {
		
		this.x = x;
		this.y = y;
	}
	
	public float tamanho(){
		return (float)Math.sqrt((x*x)+(y*y));
	}
	
	public float produtoEscalar(Vetor2f vetor){
		return vetor.x * x + vetor.y * y;
	}
	
	public Vetor2f normalizar(){
		
		float tamanho = tamanho();
		
		if(tamanho != 0){
			x /= tamanho;
			y /= tamanho;
		}
		
		return this;
	}
	
	public Vetor2f rotacionar(float angulo){
		
		double radianos = Math.toRadians(angulo);
		double seno = Math.sin(radianos);
		double cosseno = Math.cos(radianos);
		
		return new Vetor2f((float)(x * cosseno - y * seno),(float)(x * seno + y * cosseno));
	}
	
	public Vetor2f somar(Vetor2f vetor){
		return new Vetor2f(x + vetor.x, y + vetor.y);
	}
	
	public Vetor2f somar(float valor){
		return new Vetor2f(x + valor, y + valor);
	}
	
	public Vetor2f subtrair(Vetor2f vetor){
		return new Vetor2f(x - vetor.x,y - vetor.y);
	}
	
	public Vetor2f subtrair(float valor){
		return new Vetor2f(x - valor, y - valor);
	}
	
	public Vetor2f multiplicar(Vetor2f vetor){
		return new Vetor2f(x * vetor.x, y * vetor.y);
	}
	
	public Vetor2f multiplicar(float valor){
		return new Vetor2f(x * valor, y * valor);
	}
	
	public Vetor2f dividir(Vetor2f vetor){
		if((vetor.x == 0) || (vetor.y == 0))
			return this;
		return new Vetor2f(x / vetor.x,y / vetor.y);
	}
	
	public Vetor2f dividir(float valor){
		if(valor == 0)
			return this;
		return new Vetor2f(x / valor, y / valor);
	}
	
	public String toString(){
		return "(" + x + " " + y + ")";
	}

}
