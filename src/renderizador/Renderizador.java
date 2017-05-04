package renderizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entidades.Camera;
import entidades.Entidade;
import luminosidades.Difusa;
import objetos.ObjetoComTextura;
import shaders.StaticShader;
import texturas.ModeloDeTextura;

public class Renderizador {

	private StaticShader shader = new StaticShader();
	private RenderizadorDeObjetos renderizador = new RenderizadorDeObjetos(shader);
	
	private Map<ObjetoComTextura,List<Entidade>> entidades = new HashMap<ObjetoComTextura,List<Entidade>>();
	
	public void renderizar(Difusa sol, Camera camera){
		
		renderizador.limpar();
		shader.iniciarPrograma();
		shader.carregarLuminosidadeDifusa(sol);
		shader.carregarMatrizDeVisualizacao(camera);
		renderizador.renderizar(entidades);
		
		shader.fecharPrograma();
		entidades.clear();
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
	}
	
}
