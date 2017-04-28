package estruturasDeDados;

public class Vetor3f {
	public float x;
	public float y;
	public float z;
	
	public Vetor3f(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float tamanho(){
		return (float)Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	public float produtoEscalar(Vetor3f vetor){
		return vetor.x * x + vetor.y * y + vetor.z * z;
	}
	
	public Vetor3f produtoVetorial(Vetor3f vetor){
		
		float x_ = y * vetor.z - z * vetor.y;
		float y_ = z * vetor.x - x * vetor.z;
		float z_ = x * vetor.y - y * vetor.x;
		
		return new Vetor3f(x_, y_ , z_);
	}
	
	public Vetor3f normalizar(){
		
		float tamanho = tamanho();
		
		if(tamanho != 0){
			x /= tamanho;
			y /= tamanho;
			z /= tamanho;
		}
		
		return this;
	}
	
	public Vetor2f rotacionar(float angulo){
		
		double radianos = Math.toRadians(angulo);
		double seno = Math.sin(radianos);
		double cosseno = Math.cos(radianos);
		
		return new Vetor2f((float)(x * cosseno - y * seno),(float)(x * seno + y * cosseno));
	}
	
	public Vetor3f somar(Vetor3f vetor){
		return new Vetor3f(x + vetor.x, y + vetor.y, z + vetor.z);
	}
	
	public Vetor3f somar(float valor){
		return new Vetor3f(x + valor, y + valor, z + valor);
	}
	
	public Vetor3f subtrair(Vetor3f vetor){
		return new Vetor3f(x - vetor.x, y - vetor.y, z - vetor.z);
	}
	
	public Vetor3f subtrair(float valor){
		return new Vetor3f(x - valor, y - valor, z - valor);
	}
	
	public Vetor3f multiplicar(Vetor3f vetor){
		return new Vetor3f(x * vetor.x, y * vetor.y, z * vetor.z);
	}
	
	public Vetor3f multiplicar(float valor){
		return new Vetor3f(x * valor, y * valor, z * valor);
	}
	
	public Vetor3f dividir(Vetor3f vetor){
		if((vetor.x == 0) || (vetor.y == 0) || (vetor.z == 0))
			return this;
		return new Vetor3f(x / vetor.x,y / vetor.y, z / vetor.z);
	}
	
	public Vetor3f dividir(float valor){
		if(valor == 0)
			return this;
		return new Vetor3f(x / valor, y / valor, z / valor);
	}
	
	
	
	
	public String toString(){
		return "(" + x + " " + y + " " + z + ")";
	}
}
