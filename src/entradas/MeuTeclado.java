package entradas;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class MeuTeclado {
	
	private static final int NUM_KEYCODES = 256;
	private static ArrayList<Integer> teclasQueForamPressionadas = new ArrayList<Integer>();
	private static ArrayList<Integer> teclasPressionadas = new ArrayList<Integer>();
	private static ArrayList<Integer> teclasSoltas = new ArrayList<Integer>();
	
	public static void tick(){ 
		
		
		teclasSoltas.clear();
		//Aqui caso a tecla não esta sendo pressionada e foi pressionada no quadro anterior
		//neste caso ela vai entrar em estado de solta.
		for (int i = 0; i < NUM_KEYCODES; i++)
			if (!estaPressionada(i) && teclasQueForamPressionadas.contains(i))
				teclasSoltas.add(i);
		
		teclasPressionadas.clear(); 

		for (int i = 0; i < NUM_KEYCODES; i++)
			if (estaPressionada(i) && !teclasQueForamPressionadas.contains(i))
				teclasPressionadas.add(i);
		
		teclasQueForamPressionadas.clear();
		for (int i = 0; i < NUM_KEYCODES; i++)
			if (estaPressionada(i))
				teclasQueForamPressionadas.add(i);
		
		
	}
	
	public static boolean estaPressionada(int tecla) { 
		
		return Keyboard.isKeyDown(tecla); 
	} 
	
	public static boolean foiPressionada(int tecla) { 
		
		return teclasPressionadas.contains(tecla);
	}
	public static boolean foiSolta(int tecla) { 
		
		return teclasSoltas.contains(tecla);
	}
}
