package renderizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import cameras.Camera;
import entidades.Entidade;
import gerenciadores.GerenciadorDeObjetos;
import gui.TexturaGUI;
import luminosidades.Difusa;
import objetos.ObjetoComTextura;
import shaders.ShaderTerreno;
import shaders.ShaderEntidade;
import shaders.ShaderGUI;
import terrenos.Terreno;

public class Renderizador {

	private static final float VERMELHO = 0.5f;
	private static final float VERDE = 0.5f;
	private static final float AZUL = 1;
	private static final float ALPHA = 1;
	
	private GerenciadorDeObjetos gerenciadorDeObj = new GerenciadorDeObjetos();
	private ShaderEntidade shader = new ShaderEntidade();
	private ShaderTerreno shaderTerreno = new ShaderTerreno();
	private ShaderGUI shaderGUI = new ShaderGUI();
	private RenderizadorDeObjetos renderizadorObjeto = new RenderizadorDeObjetos(shader);
	private RenderizadorDeTerrenos renderizadorTerreno = new RenderizadorDeTerrenos(shaderTerreno);
	private RenderizadorGUI renderizadorGUI = new RenderizadorGUI(shaderGUI, gerenciadorDeObj);
	
	private Map<ObjetoComTextura,List<Entidade>> entidades = new HashMap<ObjetoComTextura,List<Entidade>>();
	private List<Terreno> terrenos = new ArrayList<Terreno>();
	private List<TexturaGUI> guis = new ArrayList<TexturaGUI>();
	
	public void renderizar(Difusa sol, Camera camera){

		habilitarCullFace();

		limpar();
		
		shader.iniciarPrograma();
		shader.carregarLuminosidadeDifusa(sol);
		shader.carregarMatrizDeVisualizacao(camera);
		shader.carregarCorDoCeu(VERMELHO, VERDE, AZUL);
		renderizadorObjeto.renderizar(entidades);
		shader.fecharPrograma();
		
		shaderTerreno.iniciarPrograma();
		shaderTerreno.carregarLuminosidadeDifusa(sol);
		shaderTerreno.carregarMatrizDeVisualizacao(camera);
		shaderTerreno.carregarCorDoCeu(VERMELHO, VERDE, AZUL);
		renderizadorTerreno.renderizar(terrenos);
		shaderTerreno.fecharPrograma();
		
		shaderGUI.iniciarPrograma();
		renderizadorGUI.renderizar(guis);
		shaderGUI.fecharPrograma();
		
		
		guis.clear();
		terrenos.clear();
		entidades.clear();
	}
	
	public void processarTerrenos(Terreno terreno){
		terrenos.add(terreno);
	}
	
	public void processarEntidades(Entidade entidade){
		ObjetoComTextura obj = entidade.getObjetoComTextura();
		List<Entidade> lote =  entidades.get(obj);
		if(lote != null){
			lote.add(entidade);
		}else{
			List<Entidade> novoLote = new ArrayList<Entidade>();
			novoLote.add(entidade);
			entidades.put(obj, novoLote);
		}
	}
	
	public void processarGUI(TexturaGUI gui){
		guis.add(gui);
	}
	
	public void desalocar(){
		shader.desalocarPrograma();
		shaderTerreno.desalocarPrograma();
	}
	
	public static void habilitarCullFace(){
		
		//Cortar a face obscura o objeto
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void desabilitarCullFace(){
				
		GL11.glDisable(GL11.GL_CULL_FACE);
	}	
	
    
	/***
	 * Retira todas as cores do frame passado e coloca a nova cor de background
	 * que nós setarmos
	 * 
	 **/
	public void limpar() {

		//Necessito falar pro opengl verificar qual triangulo está na frente.
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(VERMELHO, VERDE, AZUL, ALPHA);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}
	
}
