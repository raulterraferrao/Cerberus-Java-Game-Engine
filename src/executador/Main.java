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
import texturas.PacoteDeTexturaDeTerreno;
import texturas.TexturaDeEntidade;
import texturas.TexturaDeTerreno;

public class Main {

	public static void main(String[] args) {

		//Preparações iniciais 
		GerenciadorDeJanela.criarDisplay();
		
		Camera camera = new Camera();
		
		Difusa luz = new Difusa(new Vetor3f(0,40,0),new Vetor3f(1,1,1));
		
		GerenciadorDeObjetos gerenciadorDeobj = new GerenciadorDeObjetos();
		 
		//Carrego o modelo do Objeto de uma fonte externa(e.g Blender)
		
		Objeto modeloDragao = CarregarObjeto.carregarObjeto("dragon",gerenciadorDeobj);
		Objeto modeloPlanta = CarregarObjeto.carregarObjeto("grassModel",gerenciadorDeobj);
		Objeto modeloArvore = CarregarObjeto.carregarObjeto("lowPolyTree", gerenciadorDeobj);
		
		//=========================TEXTURAS========================//
		
		//Carrego a textura do Objeto de uma fonte externa(Deve ser .png)
		
		TexturaDeEntidade texturaDragao = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("dragonTexture"));
		TexturaDeEntidade texturaPlanta = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("grassTexture"));
		TexturaDeEntidade texturaArvore = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("lowPolyTree"));
		
		//Carrego a textura do Terreno de uma fonte externa(Deve ser .png)
		
		TexturaDeTerreno texturaGrama =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("grass"));
		TexturaDeTerreno texturaGramaComFlor =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("gramaComFlor"));
		TexturaDeTerreno texturaDeserto =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("deserto"));
		TexturaDeTerreno texturaCaminho =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("caminho"));
		TexturaDeTerreno texturaDeMistura = new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("mistura"));
		
		PacoteDeTexturaDeTerreno pacoteDeTextura = new PacoteDeTexturaDeTerreno(texturaDeserto, texturaGramaComFlor, texturaCaminho, texturaGrama);	
		
		//==========================================================//
		
		//Coloco o quanto a superficie da textura é reflexiva
		
		texturaDragao.setReflexo(10,1);
		
		//Coloco se a textura tem partes transparentes (Alpha)
		
		texturaPlanta.setTransparente(true);
		
		//Coloca se a textura precisa de uma iluminosidade Falsa
		
		texturaPlanta.setIluminosidadeFalsa(true);
		
		//Faço a conexão entre o Modelo do Objeto e a Textura dele
		
		ObjetoComTextura objetoDragao = new ObjetoComTextura(modeloDragao,texturaDragao);
		ObjetoComTextura objetoPlanta = new ObjetoComTextura(modeloPlanta,texturaPlanta);
		ObjetoComTextura objetoArvore = new ObjetoComTextura(modeloArvore,texturaArvore); 
		
		//Faço a transformação dos Objetos e o transformo em Entidades
		
		List<Entidade> floresta = new ArrayList<Entidade>();
		List<Entidade> capinzal = new ArrayList<Entidade>();
		Random aleatorio = new Random();
		
		for(int i = 0; i < 100 ; i++){
			
			float x = aleatorio.nextFloat()*100 - 50;
			float y = 0;
			float z = aleatorio.nextFloat() * - 1000 -20;
			
			capinzal.add(new Entidade(objetoPlanta,new Vetor3f((x+30), y, (z-15)),0f, 0f, 0f, 1f));
			floresta.add(new Entidade(objetoArvore,new Vetor3f(x, y, z),0f, 0f, 0f, 1f));
		}
		
		Entidade dragao = new Entidade(objetoDragao,new Vetor3f(0, 0, -50), 0f, 0f, 0f, 1f);
		Entidade planta = new Entidade(objetoPlanta,new Vetor3f(0, 0, -30), 0f, 0f, 0f, 1f);

		//Criação de Terrenos
		
		Terreno terreno1 = new Terreno(-1, -1, gerenciadorDeobj, pacoteDeTextura, texturaDeMistura);
		Terreno terreno2 = new Terreno(0, -1, gerenciadorDeobj, pacoteDeTextura, texturaDeMistura);
		//Terreno terreno3 = new Terreno(-1, 0, gerenciadorDeobj, new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("grass2")));
		//Terreno terreno4 = new Terreno(0, 0, gerenciadorDeobj, new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("grass3")));
		
		
		//Utiliza-se o GerenciadorDeTempo para fazer a medição de FPS
		
		GerenciadorDeTempo.getDelta(); // inicar o Delta
		GerenciadorDeTempo.setUltimoFPS(GerenciadorDeTempo.getTime());
		
		Renderizador renderizador = new Renderizador();
		
		//Loop principal da Engine que contém a lógica do Jogo
	
		while(!Display.isCloseRequested()){
			
			GerenciadorDeTempo.atualizarFPS();						
			
			camera.mover();
			
			renderizador.renderizar(luz, camera);
			
			//Renderizar Entidades
			
			for(Entidade aArvore : floresta){
					renderizador.processarEntidades(aArvore);
			}
			for(Entidade aGrama : capinzal){
				renderizador.processarEntidades(aGrama);
		}
			dragao.aumentarRotacao(0, 2, 0);
			renderizador.processarEntidades(dragao);
			renderizador.processarEntidades(planta);
			
			//Renderizar Terrenos
			
			renderizador.processarTerrenos(terreno1);
			renderizador.processarTerrenos(terreno2);
			//renderizador.processarTerrenos(terreno3);
			//renderizador.processarTerrenos(terreno4);
			
			

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
