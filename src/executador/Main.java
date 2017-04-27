package executador;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entidades.Camera;
import entidades.Entidade;
import objetos.CarregarObjeto;
import objetos.Objeto;
import objetos.ObjetoComTextura;
import renderizador.GerenciadorDeObjetos;
import renderizador.GerenciadorDeJanela;
import renderizador.Renderizador;
import shaders.StaticShader;
import texturas.ModeloDeTextura;

public class Main {

	public static void main(String[] args) {
		//Ao iniciar a nossa Engine criamos a Janela 
		//Do renderizador
		GerenciadorDeJanela.criarDisplay();
		
		GerenciadorDeObjetos gerenciadorDeobj = new GerenciadorDeObjetos();
		StaticShader shader = new StaticShader();
		Renderizador renderizador = new Renderizador(shader);
		
		 
		Objeto obj = CarregarObjeto.carregarObjeto("stall",gerenciadorDeobj);
		ModeloDeTextura textura = new ModeloDeTextura(gerenciadorDeobj.carregarTextura("stallTexture"));
		ObjetoComTextura objTextura = new ObjetoComTextura(obj,textura); 
		
		Entidade entidade = new Entidade(objTextura,new Vector3f(0,0,-50),0,0,0,1);
		Camera camera = new Camera();
		
		
		
		//Verificamos se o X da janela foi clicado
		while(!Display.isCloseRequested()){
			//logica do jogo
			camera.mover();
			entidade.aumentarRotacao(0, 1, 0);
			renderizador.limpar();
			shader.iniciarPrograma();
			shader.carregarMatrizDeVisualizacao(camera);

			renderizador.renderizar(entidade,shader);
			shader.fecharPrograma();
			
			GerenciadorDeJanela.atualizarDisplay();
		}		//Aqui somente será alcançado se fizermos uma requisição de fechar a janela
		shader.freePrograma();
		gerenciadorDeobj.Free();

		GerenciadorDeJanela.fecharDisplay();
	}

}
