package estruturasDeDados;


// https://www.3dgep.com/understanding-quaternions/ 
// Para saber mais sobre Quaternio visite esse site
public class Quaternio {

	private float x;
	private float y;
	private float z;
	private float w;
	
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
		
		float w_ = w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ();
		float x_ = w * q.getX() + x * q.getW() + y * q.getZ() - z * q.getY(); 
		float y_ = w * q.getY() + y * q.getW() + z * q.getX() - x * q.getZ();
		float z_ = w * q.getZ() + z * q.getW() + x * q.getY() - y * q.getX(); 
	
		return new Quaternio(x_, y_, z_, w_);
	}
	
	public Quaternio multiplicar(Vetor3f v){
		
		float w_ = - x * v.x - y * v.y - z * v.z;
		float x_ =   w * v.x + y * v.z - z * v.y; 
		float y_ =   w * v.y + z * v.x - x * v.z;
		float z_ =   w * v.z + x * v.y - y * v.x; 
		
		return new Quaternio(x_, y_, z_, w_);
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
	
	
}
