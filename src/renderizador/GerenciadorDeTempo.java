package renderizador;

import org.lwjgl.Sys;

public class GerenciadorDeTempo {
	
	private static long ultimoFrame;
	private static int fps;
	private static long ultimoFPS;
	private static float delta;

	public static void iniciar(){
		ultimoFrame = getTempoAtual();
	}
	
	public static void atualizarFPS() {
	    if (getTempoAtual() - ultimoFPS > 1000) {
	    	//System.out.println(getFps());
	        fps = 0; //reset the FPS counter
	        ultimoFPS += 1000; //add one second
	    }
	    ultimoFrame = getTempoAtual();
	    fps++;
	}
	
	public static long getTempoAtual() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
    public static void atualizarDelta() {
        long tempo = getTempoAtual();
        delta = (tempo - ultimoFrame)/1000f;
    }
    
    public static float getDelta() {
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
