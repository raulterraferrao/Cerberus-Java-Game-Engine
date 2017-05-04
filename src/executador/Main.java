package executador;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import entidades.Camera;
import entidades.Entidade;
import entradas.MeuMouse;
import entradas.MeuTeclado;
import estruturasDeDados.Vetor3f;
import luminosidades.Difusa;
import objetos.CarregarObjeto;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import renderizador.GerenciadorDeObjetos;
import renderizador.GerenciadorDeTempo;
import renderizador.Renderizador;
import terrenos.Terreno;
import renderizador.GerenciadorDeJanela;
import texturas.Textura;

public class Main {

	public static void main(String[] args) {

		GerenciadorDeJanela.criarDisplay();
		
		GerenciadorDeObjetos gerenciadorDeobj = new GerenciadorDeObjetos();
		 
		//Carrego o modelo do Objeto de uma fonte externa(e.g Blender)
		
		Objeto obj = CarregarObjeto.carregarObjeto("dragon",gerenciadorDeobj);
		
		//Carrego a textura do Objeto de uma fonte externa(Deve ser .png)
		
		Textura textura = new Textura(gerenciadorDeobj.carregarTextura("dragonTexture"));
	
		//Coloco o quanto a superficie da textura é reflexiva
		
		textura.setReflexo(10,1);
		
		//Faço a conexão entre o Modelo do Objeto e a Textura dele
		
		ObjetoComTextura objTextura = new ObjetoComTextura(obj,textura); 
		
		
		Camera camera = new Camera();
		
		Difusa luz = new Difusa(new Vetor3f(0,40,0),new Vetor3f(1,1,1));
		
		Terreno terreno1 = new Terreno(0, -1, gerenciadorDeobj, new Textura(gerenciadorDeobj.carregarTextura("grass")));
		Terreno terreno2 = new Terreno(-1, -1, gerenciadorDeobj, new Textura(gerenciadorDeobj.carregarTextura("grass1")));
		Terreno terreno3 = new Terreno(-1, 0, gerenciadorDeobj, new Textura(gerenciadorDeobj.carregarTextura("grass2")));
		Terreno terreno4 = new Terreno(0, 0, gerenciadorDeobj, new Textura(gerenciadorDeobj.carregarTextura("grass3")));
		
		
		List<Entidade> alcateia = new ArrayList<Entidade>();
		Random aleatorio = new Random();
		
		for(int i = 0; i < 2 ; i++){
			
			float x = aleatorio.nextFloat() * 50 - 25;
			float y = 10;
			float z = aleatorio.nextFloat() * - 150 + 20;
			
			alcateia.add(new Entidade(objTextura,new Vetor3f(x, y, z), aleatorio.nextFloat() * 180f, aleatorio.nextFloat() * 180f, 0f, 1f));
		}
		
		//Utiliza-se o GerenciadorDeTempo para fazer a medição de FPS
		
		GerenciadorDeTempo.getDelta(); // inicar o Delta
		GerenciadorDeTempo.setUltimoFPS(GerenciadorDeTempo.getTime());
		
		Renderizador renderizador = new Renderizador();
		
		//Loop principal da Engine que contém a lógica do Jogo
	
		while(!Display.isCloseRequested()){
			
			GerenciadorDeTempo.atualizarFPS();						
			
			camera.mover();
			
			for(Entidade dragao : alcateia){
					dragao.aumentarRotacao(0, 2, 0);
					renderizador.processarEntidades(dragao);
			}
			renderizador.processarTerrenos(terreno1);
			renderizador.processarTerrenos(terreno2);
			renderizador.processarTerrenos(terreno3);
			renderizador.processarTerrenos(terreno4);
			
			renderizador.renderizar(luz, camera);

			MeuTeclado.tick();
			MeuMouse.tick();
			
			if(MeuTeclado.foiPressionada(Keyboard.KEY_UP))
				System.out.println("seta apertada");
			if(MeuTeclado.foiSolta(Keyboard.KEY_UP))
				System.out.println("seta solta");
			if(MeuMouse.foiPressionado(0))
				System.out.println("botao apertado hein" + MeuMouse.getMousePos().toString());
			if(MeuMouse.foiSolto(0))
				System.out.println("botao solto");
			
			GerenciadorDeJanela.atualizarDisplay();
		}		//Aqui somente será alcançado se fizermos uma requisição de fechar a janela

		renderizador.desalocar();
		gerenciadorDeobj.desalocar();

		GerenciadorDeJanela.fecharDisplay();
	}

}
