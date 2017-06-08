package estruturasDeDados;


// https://www.3dgep.com/understanding-quaternions/ 
// Para saber mais sobre Quaternio visite esse site
public class Quaternio {

	public float x;
	public float y;
	public float z;
	public float w;
	
	public Quaternio(float x, float y, float z, float w){
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;		
	}

	public float tamanho(){
		
		return (float)Math.sqrt((x*x)+(y*y)+(z*z)+(w*w));
	}
	
	public Quaternio normalizar(){
		
		float tam = tamanho();
		if(tam == 0)
			return this;
		x /= x;
		y /= y;
		z /= z;
		w /= w;
		
		return this;	
	}
	
	public Quaternio conjugado(){
		
		return new Quaternio(-x, -y, -z, w);
	}

	
	public Quaternio multiplicar(Quaternio q){
		
		float w_ = w * q.w - x * q.x - y * q.y - z * q.z;
		float x_ = w * q.x + x * q.w + y * q.z - z * q.y; 
		float y_ = w * q.y + y * q.w + z * q.x - x * q.z;
		float z_ = w * q.z + z * q.w + x * q.y - y * q.x; 
	
		return new Quaternio(x_, y_, z_, w_);
	}
	
	public Quaternio multiplicar(Vetor3f v){
		
		float w_ = - x * v.x - y * v.y - z * v.z;
		float x_ =   w * v.x + y * v.z - z * v.y; 
		float y_ =   w * v.y + z * v.x - x * v.z;
		float z_ =   w * v.z + x * v.y - y * v.x; 
		
		return new Quaternio(x_, y_, z_, w_);
	}
	
	
	
}
