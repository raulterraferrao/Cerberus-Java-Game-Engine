package executador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import cameras.Camera;
import cameras.CameraTerceiraPessoa;
import entidades.Entidade;
import entidades.Jogador;
import entradas.MeuTeclado;
import estruturasDeDados.Vetor2f;
import estruturasDeDados.Vetor3f;
import gerenciadores.GerenciadorDeJanela;
import gerenciadores.GerenciadorDeObjetos;
import gui.TexturaGUI;
import luminosidades.Difusa;
import objetos.CarregarObjeto;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import renderizador.Renderizador;
import renderizador.RenderizadorGUI;
import terrenos.Terreno;
import texturas.PacoteDeTexturaDeTerreno;
import texturas.TexturaDeEntidade;
import texturas.TexturaDeTerreno;

public class Jogo {

	public void iniciar(){
		//==========================MACROS=========================//
		
		final Vetor3f COR_BRANCA = new Vetor3f(1,1,1);
				
		//==========================SETUP=========================//
		
		GerenciadorDeJanela.criarDisplay();
		
		Renderizador renderizador = new Renderizador();
		
		Difusa luz = new Difusa(new Vetor3f(100,400,200),COR_BRANCA);
		
		GerenciadorDeObjetos gerenciadorDeObj = new GerenciadorDeObjetos();
	
		
		//=========================OBJETOS========================//
		
		//Carrego o modelo do Objeto de uma fonte externa(e.g Blender)
		
		Objeto modeloDragao = CarregarObjeto.carregarObjeto("bunny",gerenciadorDeObj);
		Objeto modeloPlanta = CarregarObjeto.carregarObjeto("planta",gerenciadorDeObj);
		Objeto modeloCapim = CarregarObjeto.carregarObjeto("grassModel",gerenciadorDeObj);
		Objeto modeloArvore = CarregarObjeto.carregarObjeto("lowPolyTree", gerenciadorDeObj);
		
		//=========================TEXTURAS========================//
		
		//Carrego a textura do Objeto de uma fonte externa(Deve ser .png)
		
		TexturaDeEntidade texturaDragao = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("dragonTexture"));
		TexturaDeEntidade texturaPlanta = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("planta"));
		TexturaDeEntidade texturaCapim = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("capim"));
		TexturaDeEntidade texturaArvore = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("lowPolyTree"));
		
		//Carrego a textura do Terreno de uma fonte externa(Deve ser .png)
		
		TexturaDeTerreno texturaGrama =  new TexturaDeTerreno(gerenciadorDeObj.carregarTextura("grass"));
		TexturaDeTerreno texturaGramaComFlor =  new TexturaDeTerreno(gerenciadorDeObj.carregarTextura("gramaComFlor"));
		TexturaDeTerreno texturaDeserto =  new TexturaDeTerreno(gerenciadorDeObj.carregarTextura("deserto"));
		TexturaDeTerreno texturaCaminho =  new TexturaDeTerreno(gerenciadorDeObj.carregarTextura("caminho"));
		TexturaDeTerreno texturaDeMistura = new TexturaDeTerreno(gerenciadorDeObj.carregarTextura("mistura"));
		
		PacoteDeTexturaDeTerreno pacoteDeTextura = new PacoteDeTexturaDeTerreno(texturaDeserto, texturaGrama, texturaCaminho, texturaGramaComFlor);	
		
		//Coloco o quanto a superficie da textura é reflexiva
		
		texturaDragao.setReflexo(10,2);
		
		//Coloco se a textura tem partes transparentes (Alpha)
		
		texturaPlanta.setTransparente(true);
		
		//Coloca se a textura precisa de uma iluminosidade Falsa
		
		//texturaPlanta.setIluminosidadeFalsa(true);
		texturaCapim.setIluminosidadeFalsa(true);
		//texturaPlanta.setIluminosidadeFalsa(true);
		
		//Coloco se a textura é dividida em grids de texturas
		texturaPlanta.setQuantidadeDeLinhas(2);
		
		//=========================TERRENOS==========================//
		
		Terreno terreno1 = new Terreno(-0.5f,-0.5f , gerenciadorDeObj, pacoteDeTextura, texturaDeMistura, "mapaDeAltura1");
		//Terreno terreno2 = new Terreno(0, -1, gerenciadorDeobj, pacoteDeTextura, texturaDeMistura, "mapaDeAltura");
		//Terreno terreno3 = new Terreno(-1, 0, gerenciadorDeobj, pacoteDeTextura, texturaDeMistura, "mapaDeAltura");
		//Terreno terreno4 = new Terreno(0, 0, gerenciadorDeobj, pacoteDeTextura, texturaDeMistura, "mapaDeAltura");
	
		
		//=======================ENTIDADES=========================//
		
		//Faço a conexão entre o Modelo do Objeto e a Textura dele
		
		ObjetoComTextura objetoDragao = new ObjetoComTextura(modeloDragao,texturaDragao);
		ObjetoComTextura objetoPlanta = new ObjetoComTextura(modeloPlanta,texturaPlanta);
		ObjetoComTextura objetoCapim = new ObjetoComTextura(modeloCapim,texturaCapim);
		ObjetoComTextura objetoArvore = new ObjetoComTextura(modeloArvore,texturaArvore); 
		
		//Faço a transformação dos Objetos e o transformo em Entidades
		
		List<Entidade> floresta = new ArrayList<Entidade>();
		List<Entidade> capinzal = new ArrayList<Entidade>();
		List<Entidade> plantio = new ArrayList<Entidade>();
		Random aleatorio = new Random();
		
		for(int i = 0; i < 40 ; i++){
			
			float x = terreno1.getX() + aleatorio.nextFloat() * Terreno.getTamanho();
			float z = terreno1.getZ() + aleatorio.nextFloat() * Terreno.getTamanho();
			float y = terreno1.getAlturaDoTerreno(x, z);
			
			capinzal.add(new Entidade(objetoCapim,new Vetor3f(x, y, z),0f, 0f, 0f, 1f));
			floresta.add(new Entidade(objetoArvore,new Vetor3f(x, y, z),0f, 0f, 0f, 1f));
		}
		for(int i = 0; i < 80 ; i++){
			
			float x = terreno1.getX() + aleatorio.nextFloat() * Terreno.getTamanho();
			float z = terreno1.getZ() + aleatorio.nextFloat() * Terreno.getTamanho();
			float y = terreno1.getAlturaDoTerreno(x, z);
			
			plantio.add(new Entidade(objetoPlanta,aleatorio.nextInt(4),new Vetor3f(x, y, z),0f, 0f, 0f, 1f));
		}
		
		Jogador dragao = new Jogador(objetoDragao,new Vetor3f(20, 0, -50), 0f, 0f, 0f, 1f);
		
		
		//============================CAMERAS==============================//
		
		//CameraTerceiraPessoa camera = new CameraTerceiraPessoa(dragao);
		Camera camera = new Camera();
		
		
		//==============================GUI================================//
		
		
		List<TexturaGUI> guis = new ArrayList<TexturaGUI>();
		//TexturaGUI gui = new TexturaGUI(new Vetor2f(0.5f, 0.5f), new Vetor2f (0.25f, 0.25f), 
		//										gerenciadorDeObj.carregarTextura("health"));
		TexturaGUI gui2 = new TexturaGUI(new Vetor2f(0.85f, 0.6f), new Vetor2f (0.3f, 0.3f), 
												gerenciadorDeObj.carregarTextura("gta")); 
		
		guis.add(gui2);
		//guis.add(gui);
		

		
			
		//=========================LOOP PRINCIPAL==========================//
	
		while(!Display.isCloseRequested() && !MeuTeclado.foiPressionada(Keyboard.KEY_ESCAPE)){
			if(MeuTeclado.foiPressionada(Keyboard.KEY_F2)){
				pausar();
			}
												
			//****************MOVER ENTIDADES****************//
			
			camera.mover();
			dragao.mover(terreno1);
			
			//****************RENDERIZAR ENTIDADES****************//
			
			renderizador.renderizar(luz, camera);
			
			for(Entidade aArvore : floresta){
				renderizador.processarEntidades(aArvore);
			}
			for(Entidade aGrama : capinzal){
				renderizador.processarEntidades(aGrama);
			}
			for(Entidade aPlanta : plantio){
				renderizador.processarEntidades(aPlanta);
			}
			for(TexturaGUI aGUI : guis){
				renderizador.processarGUI(aGUI);
			}
			
			dragao.aumentarRotacao(0, 2, 0);
			renderizador.processarEntidades(dragao);
			

			
			
			renderizador.processarTerrenos(terreno1);
			//renderizador.processarTerrenos(terreno2);
			//renderizador.processarTerrenos(terreno3);
			//renderizador.processarTerrenos(terreno4);
			
			
			//****************ATUALIZAR DISPLAY****************//
			
			GerenciadorDeJanela.atualizarDisplay();
		}		//Aqui somente será alcançado se fizermos uma requisição de fechar a janela

		//======================DESALOCAR RECURSOS=======================//
		
		renderizador.desalocar();
		gerenciadorDeObj.desalocar();
		
		//========================FECHAR DISPLAY=========================//

		GerenciadorDeJanela.fecharDisplay();
	}
	
	public boolean pausar(){
		int flag = 2;
		while(flag > 0){
			if(MeuTeclado.foiPressionada(Keyboard.KEY_F2))
					flag--;
			GerenciadorDeJanela.atualizarDisplay();
		}
		return true;
	}
}

