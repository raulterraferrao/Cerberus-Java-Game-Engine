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
import estruturasDeDados.Vetor3f;
import luminosidades.Difusa;
import objetos.CarregarObjeto;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import renderizador.GerenciadorDeJanela;
import renderizador.GerenciadorDeObjetos;
import renderizador.Renderizador;
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
		
		GerenciadorDeObjetos gerenciadorDeobj = new GerenciadorDeObjetos();
	
		
		//=========================OBJETOS========================//
		
		//Carrego o modelo do Objeto de uma fonte externa(e.g Blender)
		
		Objeto modeloDragao = CarregarObjeto.carregarObjeto("bunny",gerenciadorDeobj);
		Objeto modeloPlanta = CarregarObjeto.carregarObjeto("pine",gerenciadorDeobj);
		Objeto modeloCapim = CarregarObjeto.carregarObjeto("grassModel",gerenciadorDeobj);
		Objeto modeloArvore = CarregarObjeto.carregarObjeto("lowPolyTree", gerenciadorDeobj);
		
		//=========================TEXTURAS========================//
		
		//Carrego a textura do Objeto de uma fonte externa(Deve ser .png)
		
		TexturaDeEntidade texturaDragao = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("dragonTexture"));
		TexturaDeEntidade texturaPlanta = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("pine"));
		TexturaDeEntidade texturaCapim = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("capim"));
		TexturaDeEntidade texturaArvore = new TexturaDeEntidade(gerenciadorDeobj.carregarTextura("lowPolyTree"));
		
		//Carrego a textura do Terreno de uma fonte externa(Deve ser .png)
		
		TexturaDeTerreno texturaGrama =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("grass"));
		TexturaDeTerreno texturaGramaComFlor =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("gramaComFlor"));
		TexturaDeTerreno texturaDeserto =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("deserto"));
		TexturaDeTerreno texturaCaminho =  new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("caminho"));
		TexturaDeTerreno texturaDeMistura = new TexturaDeTerreno(gerenciadorDeobj.carregarTextura("mistura"));
		
		PacoteDeTexturaDeTerreno pacoteDeTextura = new PacoteDeTexturaDeTerreno(texturaDeserto, texturaGramaComFlor, texturaCaminho, texturaGrama);	
		
		//Coloco o quanto a superficie da textura é reflexiva
		
		texturaDragao.setReflexo(10,2);
		
		//Coloco se a textura tem partes transparentes (Alpha)
		
		texturaPlanta.setTransparente(true);
		
		//Coloca se a textura precisa de uma iluminosidade Falsa
		
		//texturaPlanta.setIluminosidadeFalsa(true);
		texturaCapim.setIluminosidadeFalsa(true);
		
		//=========================TERRENOS==========================//
		
		Terreno terreno1 = new Terreno(-0.5f,-0.5f , gerenciadorDeobj, pacoteDeTextura, texturaDeMistura, "mapaDeAltura1");
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
		Random aleatorio = new Random();
		
		for(int i = 0; i < 40 ; i++){
			
			float x = aleatorio.nextFloat()*1000;
			float z = aleatorio.nextFloat() *1000 - 500;
			float y = terreno1.getAlturaDoTerreno(x, z);
			
			capinzal.add(new Entidade(objetoCapim,new Vetor3f(x, y, z),0f, 0f, 0f, 1f));
			floresta.add(new Entidade(objetoArvore,new Vetor3f(x, y, z),0f, 0f, 0f, 1f));
		}
		
		Jogador dragao = new Jogador(objetoDragao,new Vetor3f(20, 0, -50), 0f, 0f, 0f, 1f);
		Entidade planta = new Entidade(objetoPlanta,new Vetor3f(0, terreno1.getAlturaDoTerreno(0, -30), -30), 0f, 0f, 0f, 2f);

		//=========================CAMERAS===========================//
		
		CameraTerceiraPessoa camera = new CameraTerceiraPessoa(dragao);
		//Camera camera = new Camera();
		
			
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
			//dragao.aumentarRotacao(0, 2, 0);
			renderizador.processarEntidades(dragao);
			renderizador.processarEntidades(planta);
			
			
			renderizador.processarTerrenos(terreno1);
			//renderizador.processarTerrenos(terreno2);
			//renderizador.processarTerrenos(terreno3);
			//renderizador.processarTerrenos(terreno4);
			
			//****************ATUALIZAR DISPLAY****************//
			
			GerenciadorDeJanela.atualizarDisplay();
		}		//Aqui somente será alcançado se fizermos uma requisição de fechar a janela

		//======================DESALOCAR RECURSOS=======================//
		
		renderizador.desalocar();
		gerenciadorDeobj.desalocar();
		
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

