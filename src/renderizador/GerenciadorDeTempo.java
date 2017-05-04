package renderizador;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class GerenciadorDeTempo {
	
	private static long ultimoFrame;
	private static int fps;
	private static long ultimoFPS;

	public static void atualizarFPS() {
	    if (getTime() - ultimoFPS > 1000) {
	        Display.setTitle("FPS: " + fps); 
	        fps = 0; //reset the FPS counter
	        ultimoFPS += 1000; //add one second
	    }
	    fps++;
	}
	
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
    public static int getDelta() {
        long tempo = getTime();
        int delta = (int) (tempo - ultimoFrame);
        ultimoFrame = tempo;
      
        return delta;
    }

	public static long getUltimoFrame() {
		return ultimoFrame;
	}

	public static void setUltimoFrame(long ultimoFrame) {
		GerenciadorDeTempo.ultimoFrame = ultimoFrame;
	}

	public static int getFps() {
		return fps;
	}

	public static void setFps(int fps) {
		GerenciadorDeTempo.fps = fps;
	}

	public static long getUltimoFPS() {
		return ultimoFPS;
	}

	public static void setUltimoFPS(long ultimoFPS) {
		GerenciadorDeTempo.ultimoFPS = ultimoFPS;
	}
}
