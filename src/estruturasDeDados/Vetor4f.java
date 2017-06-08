package estruturasDeDados;

public class Vetor4f {
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vetor4f(float x, float y, float z, float w) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public Vetor4f() {
		
		this.x = 1;
		this.y = 1;
		this.z = 1;
		this.w = 1;
	}
	
	public float tamanho(){
		return (float)Math.sqrt((x*x)+(y*y)+(z*z)+(w*w));
	}
	
	
	public static Vetor4f somar(Vetor4f esquerda, Vetor4f direita) {
		
		return new Vetor4f(esquerda.x + direita.x, esquerda.y + direita.y, esquerda.z + direita.z, esquerda.w + direita.w);
	}
	public static Vetor4f subtrair(Vetor4f esquerda, Vetor4f direita) {
		
		return new Vetor4f(esquerda.x - direita.x, esquerda.y - direita.y, esquerda.z - direita.z, esquerda.w - direita.w);
	}
	public static Vetor4f multiplicar(Vetor4f esquerda, Vetor4f direita) {
		
		return new Vetor4f(esquerda.x * direita.x, esquerda.y - direita.y, esquerda.z - direita.z, esquerda.w - direita.w);
	}
	public Vetor4f negar() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		return this;
	}
	public Vetor4f normalizar() {
		float l = tamanho();
		return new Vetor4f(x / l, y / l, z / l, w / l);
	}
	public static float produtoEscalar(Vetor4f esquerda, Vetor4f direita) {
		return esquerda.x * direita.x + esquerda.y * direita.y + esquerda.z * direita.z + esquerda.w * direita.w;
	}
	
}
