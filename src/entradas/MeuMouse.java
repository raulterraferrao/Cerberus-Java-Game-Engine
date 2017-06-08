package entradas;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import cameras.Camera;
import estruturasDeDados.Matriz4f;
import estruturasDeDados.Quaternio;
import estruturasDeDados.Vetor2f;
import estruturasDeDados.Vetor3f;
import estruturasDeDados.Vetor4f;
import matrizesDeTransformacao.MatrizDeProjecao;
import matrizesDeTransformacao.MatrizDeVisualizacao;

public class MeuMouse {
	
	private static final int NUM_BOTOES_MOUSE = 5;
	private static ArrayList<Integer> botoesQueForamPressionados = new ArrayList<Integer>();
	private static ArrayList<Integer> botoesPressionados = new ArrayList<Integer>();
	private static ArrayList<Integer> botoesSoltos = new ArrayList<Integer>();
	
	public static void tick(){ 
		
		
		botoesSoltos.clear();
		//Aqui caso a tecla não esta sendo pressionada e foi pressionada no quadro anterior
		//neste caso ela vai entrar em estado de solta.
		for (int i = 0; i < NUM_BOTOES_MOUSE; i++)
			if (!estaPressionado(i) && botoesQueForamPressionados.contains(i))
				botoesSoltos.add(i);
		
		botoesPressionados.clear(); 

		for (int i = 0; i < NUM_BOTOES_MOUSE; i++)
			if (estaPressionado(i) && !botoesQueForamPressionados.contains(i))
				botoesPressionados.add(i);
		
		botoesQueForamPressionados.clear();
		for (int i = 0; i < NUM_BOTOES_MOUSE; i++)
			if (estaPressionado(i))
				botoesQueForamPressionados.add(i);
		
		
	}
	
	public static Vetor3f selecionar(Camera cam){
		Matriz4f visualizacao = new Matriz4f();
		Matriz4f projecao = new Matriz4f();
		
		visualizacao = MatrizDeVisualizacao.criarMatrizDeVisualizacao(cam);
		projecao = MatrizDeProjecao.getMatrizDeProjecao();
		
		//Viewport (e.g(788,344))
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		
		//Normalizada (e.g(-0.3, 0.9)
		Vetor2f normalizado = normalizar(mouseX, mouseY);
		
		//ClipSpace
		Vetor4f clipSpace = new Vetor4f(normalizado.x, normalizado.y, -1f, 1f);
		
		//EyeSpace
		Vetor4f eyeSpace = projecaoReversa(clipSpace, projecao);
		
		//WorldSpace
		Vetor3f worldSpace = visualizacaoReversa(eyeSpace, visualizacao);
		
		//Normalizar pois queremos só a direção
		worldSpace.normalizar();
		
		return worldSpace;
		
	}
	
	private static Vetor2f normalizar(float mouseX, float mouseY){
		
		float x = (mouseX * 2f) / Display.getWidth() -1f;
		float y = (mouseY * 2f) / Display.getHeight() -1f;
		
		
		return new Vetor2f(x, y);
	}
	
	private static Vetor4f projecaoReversa(Vetor4f clipspace, Matriz4f projecao){
		Matriz4f inversa = Matriz4f.inversa(projecao, null);
		Vetor4f eyeSpace = Matriz4f.transformar(inversa, clipspace, null);
		return new Vetor4f(eyeSpace.x, eyeSpace.y, -1f, 0f);
		
	}
	
	private static Vetor3f visualizacaoReversa(Vetor4f eyeSpace, Matriz4f visualizacao){
		Matriz4f inversa = Matriz4f.inversa(visualizacao, null);
		Vetor4f worldSpace = Matriz4f.transformar(inversa, eyeSpace, null);
		return new Vetor3f(worldSpace.x, worldSpace.y, worldSpace.z);
		
	}
	
	public static boolean estaPressionado(int botao) { 
		
		return Mouse.isButtonDown(botao); 
	} 
	
	public static boolean foiPressionado(int botao) { 
		
		return botoesPressionados.contains(botao);
	}
	public static boolean foiSolto(int botao) { 
		
		return botoesSoltos.contains(botao);
	}
	
	public static Vetor2f getPosicao(){
		
		return new Vetor2f(Mouse.getX(), Mouse.getY());
	}
	
	public static float getDeltaRodinha(){
		return Mouse.getDWheel() * 0.1f;
	}
	
	public static float getDeltaY(){
		return Mouse.getDY() * 0.1f;
	}
	public static float getDeltaX(){
		return Mouse.getDX() * 0.1f;
	}
	public static boolean isBotaoDireito(){
		return Mouse.isButtonDown(1);
	}
	public static boolean isBotaoEsquerdo(){
		return Mouse.isButtonDown(0);
	}
	
}
