package renderizador;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.PixelFormat;

import entradas.MeuMouse;
import entradas.MeuTeclado;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GerenciadorDeJanela {
	
	//Tamanho da janela e FPS maxímo que irá rodas o programa
	private static final int WIDTH = 1366;
	private static final int HEIGHT = 768;
	private static final int FPS = 120;
	
	//Esse metodo cria a Janela no qual rodaremos nossa engine
	public static void criarDisplay(){
		//Aqui estão os atributos de como funcionará o Display
		//ContexAttribs(3,2) diz em qual versão deve rodar o OpenGL
		//withForwardCompatible diz que o display pode ser compativel com versões futuras do opengl
		//
		ContextAttribs args = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.create(new PixelFormat(),args);
			Display.setTitle("Tcc - Engine ");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//Tamanho da janela
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
		//Também aqui iniciaremos o Keyboard e o Mouse do LWJGL
		
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Mouse.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Utiliza-se o GerenciadorDeTempo para fazer a medição de FPS
		
		GerenciadorDeTempo.iniciar(); // inicar o Delta
		GerenciadorDeTempo.setUltimoFPS(GerenciadorDeTempo.getTempoAtual());
		
		
	}
	//A cada passo esse metodo atualizará a janela na qual rodamos nossa engine
	public static void atualizarDisplay(){
		GerenciadorDeTempo.atualizarDelta();		
		GerenciadorDeTempo.atualizarFPS();
		MeuTeclado.tick();
		MeuMouse.tick();
		//Limita a Quantidade de fps no qual a engine irá rodar
		Display.sync(FPS);
		//Atualiza o nosso Display
		Display.update();
	}
	//Quando fechar a janela rodará esse método.
	public static void fecharDisplay(){
		//Fechar display
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
}
