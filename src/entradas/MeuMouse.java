package entradas;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;

import estruturasDeDados.Vector2f;

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
	
	public static boolean estaPressionado(int botao) { 
		
		return Mouse.isButtonDown(botao); 
	} 
	
	public static boolean foiPressionado(int botao) { 
		
		return botoesPressionados.contains(botao);
	}
	public static boolean foiSolto(int botao) { 
		
		return botoesSoltos.contains(botao);
	}
	
	public static Vector2f getMousePos(){
		
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
}
