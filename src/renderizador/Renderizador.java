package renderizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import entidades.Camera;
import entidades.Entidade;
import luminosidades.Difusa;
import objetos.ObjetoComTextura;
import shaders.ShaderTerreno;
import shaders.StaticShader;
import terrenos.Terreno;
import texturas.Textura;

public class Renderizador {

	private StaticShader shader = new StaticShader();
	private ShaderTerreno shaderTerreno = new ShaderTerreno();
	private RenderizadorDeObjetos renderizador = new RenderizadorDeObjetos(shader);
	private RenderizadorDeTerrenos renderizadorTerreno = new RenderizadorDeTerrenos(shaderTerreno);
	
	private Map<ObjetoComTextura,List<Entidade>> entidades = new HashMap<ObjetoComTextura,List<Entidade>>();
	private List<Terreno> terrenos = new ArrayList<Terreno>();
	
	public void renderizar(Difusa sol, Camera camera){

		//Cortar a face obscura o objeto
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		limpar();
		
		shader.iniciarPrograma();
		shader.carregarLuminosidadeDifusa(sol);
		shader.carregarMatrizDeVisualizacao(camera);
		renderizador.renderizar(entidades);
		shader.fecharPrograma();
		
		shaderTerreno.iniciarPrograma();
		shaderTerreno.carregarLuminosidadeDifusa(sol);
		shaderTerreno.carregarMatrizDeVisualizacao(camera);
		renderizadorTerreno.renderizar(terrenos);
		shaderTerreno.fecharPrograma();
		
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
	
	public void desalocar(){
		shader.desalocarPrograma();
		shaderTerreno.desalocarPrograma();
	}
	
    
	/***
	 * Retira todas as cores do frame passado e coloca a nova cor de background
	 * que nós setarmos
	 * 
	 **/
	public void limpar() {

		//Necessito falar pro opengl verificar qual triangulo está na frente.
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(1, 1, 1, 0.7f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}
	
}
