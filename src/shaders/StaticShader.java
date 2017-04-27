package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entidades.Camera;
import ferramentas.Matematica;

public class StaticShader extends ProgramaShaderPadrao {

	private static final String ARQUIVO_VERTEX = "src/shaders/vertexShader.txt";
	private static final String ARQUIVO_FRAGMENT = "src/shaders/fragmentShader.txt";
	
	private int localidade_MatrizDeTransformacao;
	private int localidade_MatrizDeProjecao;
	private int localidade_MatrizDeVisualizacao;
	
	public StaticShader() {
		super(ARQUIVO_VERTEX, ARQUIVO_FRAGMENT);

	}

	@Override
	protected void conectarAtributos() {
		//Conecta o atributo de id x do VAO no shader com o nome passado como parametro
		//e o tem que ter o mesmo nome do 'in' do vertexShader
		super.conectarAtributo(0,"pos");
		super.conectarAtributo(1, "coordenadasDaTextura");
	}

	@Override
	protected void getLocalidadeDeTodosUniform() {
		localidade_MatrizDeTransformacao = super.getLocalidadeUniform("matrizDeTransformacao");
		localidade_MatrizDeProjecao = super.getLocalidadeUniform("matrizDeProjecao");
		localidade_MatrizDeVisualizacao = super.getLocalidadeUniform("matrizDeVisualizacao");
		
	}
	
	public void carregarMatrizDeTransformacao(Matrix4f matriz){
		super.carregarMatriz4f(localidade_MatrizDeTransformacao, matriz);
	}
	
	public void carregarMatrizDeProjecao(Matrix4f projecao){
		super.carregarMatriz4f(localidade_MatrizDeProjecao, projecao);
	}
	public void carregarMatrizDeVisualizacao(Camera camera){
		Matrix4f visualizacao = Matematica.criarMatrizDeVisualizacao(camera);
		super.carregarMatriz4f(localidade_MatrizDeVisualizacao, visualizacao);
	}

}
