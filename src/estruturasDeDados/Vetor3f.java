package estruturasDeDados;

public class Vetor3f {
	private float x;
	private float y;
	private float z;
	
	public Vetor3f(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float tamanho(){
		return (float)Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	public float produtoEscalar(Vetor3f vetor){
		return vetor.getX() * x + vetor.getY() * y + vetor.getZ() * z;
	}
	
	public Vetor3f produtoVetorial(Vetor3f vetor){
		
		float x_ = y * vetor.getZ() - z * vetor.getY();
		float y_ = z * vetor.getX() - x * vetor.getZ();
		float z_ = x * vetor.getY() - y * vetor.getX();
		
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
		return new Vetor3f(x + vetor.getX(), y + vetor.getY(), z + vetor.getZ());
	}
	
	public Vetor3f somar(float valor){
		return new Vetor3f(x + valor, y + valor, z + valor);
	}
	
	public Vetor3f subtrair(Vetor3f vetor){
		return new Vetor3f(x - vetor.getX(), y - vetor.getY(), z - vetor.getZ());
	}
	
	public Vetor3f subtrair(float valor){
		return new Vetor3f(x - valor, y - valor, z - valor);
	}
	
	public Vetor3f multiplicar(Vetor3f vetor){
		return new Vetor3f(x * vetor.getX(), y * vetor.getY(), z * vetor.getZ());
	}
	
	public Vetor3f multiplicar(float valor){
		return new Vetor3f(x * valor, y * valor, z * valor);
	}
	
	public Vetor3f dividir(Vetor3f vetor){
		if((vetor.getX() == 0) || (vetor.getY() == 0) || (vetor.getZ() == 0))
			return this;
		return new Vetor3f(x / vetor.getX(),y / vetor.getY(), z / vetor.getZ());
	}
	
	public Vetor3f dividir(float valor){
		if(valor == 0)
			return this;
		return new Vetor3f(x / valor, y / valor, z / valor);
	}
	
	
	
	
	public String toString(){
		return "(" + x + " " + y + " " + z + ")";
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}
