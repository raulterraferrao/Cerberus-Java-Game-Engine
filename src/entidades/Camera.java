package entidades;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	Vector3f posicao = new Vector3f(0,0,1);
	float pitch;
	float yaw = 0;
	float roll;
	
	public Camera(){
		
	}
	
	public void mover(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			posicao.z -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			posicao.z += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			posicao.x += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			posicao.x -= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			yaw += 0.60f; 
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_G)){
			yaw -= 0.60f; 
		}
	}
	
	public Vector3f getPosicao() {
		return posicao;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	
	
	
}
