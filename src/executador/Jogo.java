package executador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.security.auth.callback.TextOutputCallback;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import cameras.Camera;
import cameras.CameraTerceiraPessoa;
import entidades.Entidade;
import entidades.Jogador;
import entradas.MeuMouse;
import entradas.MeuTeclado;
import estruturasDeDados.Vetor2f;
import estruturasDeDados.Vetor3f;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gerenciadores.GerenciadorDeJanela;
import gerenciadores.GerenciadorDeObjetos;
import gui.TexturaGUI;
import luminosidades.Difusa;
import objetos.CarregarObjeto;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import renderizador.Renderizador;
import terrenos.Terreno;
import texturas.PacoteDeTexturaDeTerreno;
import texturas.TexturaDeEntidade;
import texturas.TexturaDeTerreno;

public class Jogo {

	public void iniciar(){
		//==========================MACROS=========================//
		
		final Vetor3f COR_BRANCA = new Vetor3f(1,1,1);
		final Vetor3f COR_VERDE = new Vetor3f(0,2,0);
		final Vetor3f COR_VERMELHA = new Vetor3f(2,0,0);
		final Vetor3f COR_BRANCA_ESCURO = new Vetor3f(0.4f, 0.4f, 0.4f);
				
		//==========================SETUP=========================//
		
		GerenciadorDeJanela.criarDisplay();
		
		Renderizador renderizador = new Renderizador();
		
		GerenciadorDeObjetos gerenciadorDeObj = new GerenciadorDeObjetos();
		
		TextMaster.init(gerenciadorDeObj);
		
		FontType font = new FontType(gerenciadorDeObj.carregarTextura("barrio"),new File("img/barrio.fnt"));
		GUIText texto = new GUIText("solução", 1, font , new Vetor2f(0,0), 1f, true);
		

		//=========================OBJETOS========================//
		
		//Carrego o modelo do Objeto de uma fonte externa(e.g Blender)
		
		Objeto modeloDragao = CarregarObjeto.carregarObjeto("bunny",gerenciadorDeObj);
		Objeto modeloPlanta = CarregarObjeto.carregarObjeto("planta",gerenciadorDeObj);
		Objeto modeloCapim = CarregarObjeto.carregarObjeto("grassModel",gerenciadorDeObj);
		Objeto modeloArvore = CarregarObjeto.carregarObjeto("lowPolyTree", gerenciadorDeObj);
		Objeto modeloPoste = CarregarObjeto.carregarObjeto("poste", gerenciadorDeObj);
		
		//=========================TEXTURAS========================//
		
		//Carrego a textura do Objeto de uma fonte externa(Deve ser .png)
		
		TexturaDeEntidade texturaDragao = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("dragonTexture"));
		TexturaDeEntidade texturaPlanta = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("planta"));
		TexturaDeEntidade texturaCapim = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("capim"));
		TexturaDeEntidade texturaArvore = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("lowPolyTree"));
		TexturaDeEntidade texturaPoste = new TexturaDeEntidade(gerenciadorDeObj.carregarTextura("poste"));
		
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
		texturaPoste.setIluminosidadeFalsa(true);
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
		ObjetoComTextura objetoPoste = new ObjetoComTextura(modeloPoste,texturaPoste); 
		
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
		
		Jogador poste = new Jogador(objetoPoste,new Vetor3f(0, terreno1.getAlturaDoTerreno(0, -20), -20), 0f, 0f, 0f, 1f);
		Jogador poste2 = new Jogador(objetoPoste,new Vetor3f(0, terreno1.getAlturaDoTerreno(0, -100), -100), 0f, 0f, 0f, 1f);
		
	
		//========================CAMERAS=========================//
		
		CameraTerceiraPessoa camera = new CameraTerceiraPessoa(poste);
		//Camera camera = new Camera();
		
		
		//==========================LUZES=========================//
		
		List<Difusa> luzes = new ArrayList<Difusa>();
		
		Difusa sol = new Difusa(new Vetor3f(1000000, 400000, 20000),COR_BRANCA_ESCURO);
		Difusa verde = new Difusa(new Vetor3f(poste.getPosicao().x, poste.getPosicao().y + 14.8f, poste.getPosicao().z),COR_VERDE,new Vetor3f(1f, 0.01f, 0.002f));
		Difusa vermelha = new Difusa(new Vetor3f(poste2.getPosicao().x, poste2.getPosicao().y + 14.8f, poste2.getPosicao().z),COR_VERMELHA,new Vetor3f(1f, 0.005f, 0.001f));
		
		luzes.add(sol);
		luzes.add(verde);
		luzes.add(vermelha);
		
		//============================GUI=========================//
		
		
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
				texto.remove();
				pausar();
			}
												
			//****************MOVER ENTIDADES****************//
			
			camera.mover();
			poste.mover(terreno1);
			luzes.get(1).setPosicao(new Vetor3f(poste.getPosicao().x, poste.getPosicao().y + 20f, poste.getPosicao().z));
			
			//****************RENDERIZAR ENTIDADES****************//
			
			//System.out.println(MeuMouse.selecionar(camera));
			
			renderizador.renderizar(luzes, camera);
			
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
			
			//dragao.aumentarRotacao(0, 2, 0);
			renderizador.processarEntidades(poste);
			renderizador.processarEntidades(poste2);
			

			
			
			renderizador.processarTerrenos(terreno1);
			//renderizador.processarTerrenos(terreno2);
			//renderizador.processarTerrenos(terreno3);
			//renderizador.processarTerrenos(terreno4);
			
			TextMaster.render();
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

