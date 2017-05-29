package shaders;

import cameras.Camera;
import estruturasDeDados.Matriz4f;
import estruturasDeDados.Vetor3f;
import gerenciadores.GerenciadorDeJanela;
import gerenciadores.GerenciadorDeTempo;
import matrizesDeTransformacao.MatrizDeProjecao;
import matrizesDeTransformacao.MatrizDeVisualizacao;

public class ShaderSkybox extends ProgramaShaderPadrao{

	private static final String ARQUIVO_VERTEX = "src/glsl/vertexShaderSkybox.glsl";
	private static final String ARQUIVO_FRAGMENT = "src/glsl/fragmentShaderSkybox.glsl";
	
	private static final int POSICAO = 0;

	
	private static final float ROTACAO = 1f;
	private static float rotacionar = 0f;
	
	private int localidade_MatrizDeProjecao;
	private int localidade_MatrizDeVisualizacao;
	private int localidade_CorDoCeu;
	private int localidade_CubeMap1;
	private int localidade_CubeMap2;
	private int localidade_Mistura;
	
	public ShaderSkybox() {
		super(ARQUIVO_VERTEX, ARQUIVO_FRAGMENT);
	}
	
	protected void conectarAtributos() {
		//Conecta o atributo de id x do VAO no shader com o nome passado como parametro
		//e o tem que ter o mesmo nome do 'in' do vertexShader
		super.conectarAtributo(POSICAO,"pos");
	}
	
	@Override
	protected void getLocalidadeDeTodosUniform() {
		localidade_MatrizDeProjecao = super.getLocalidadeUniform("matrizDeProjecao");
		localidade_MatrizDeVisualizacao = super.getLocalidadeUniform("matrizDeVisualizacao");
		localidade_CorDoCeu = super.getLocalidadeUniform("corDoCeu");
		localidade_CubeMap1 = super.getLocalidadeUniform("cubeMap1");
		localidade_CubeMap2 = super.getLocalidadeUniform("cubeMap2");
		localidade_Mistura = super.getLocalidadeUniform("mistura");
	}
	
	public void carregarMatrizDeProjecao(){
		MatrizDeProjecao.criarMatrizDeProjecao();
		Matriz4f projecao = new Matriz4f();
		projecao = MatrizDeProjecao.getMatrizDeProjecao();
		super.carregarMatriz4f(localidade_MatrizDeProjecao, projecao);
	}
	public void carregarMatrizDeVisualizacao(Camera camera){
		Matriz4f visualizacao = MatrizDeVisualizacao.criarMatrizDeVisualizacao(camera);
		visualizacao.m30 = 0;
		visualizacao.m31 = 0;
		visualizacao.m32 = 0;
		rotacionar += ROTACAO * GerenciadorDeTempo.getDelta();
		Matriz4f.rotacionar((float)Math.toRadians(rotacionar), new Vetor3f(0,1,0), visualizacao, visualizacao);
		super.carregarMatriz4f(localidade_MatrizDeVisualizacao, visualizacao);
	}
	
	public void carregarCorDoCeu(float vermelho, float verde, float azul){
		super.carregarVetor3f(localidade_CorDoCeu, new Vetor3f(vermelho, verde, azul));
	}
	public void carregarCubeMap(){
		super.carregarInteiro(localidade_CubeMap1, 0);
		super.carregarInteiro(localidade_CubeMap2, 1);
	}
	
	public void carregarMistura(float mistura){
		super.carregarFloat(localidade_Mistura, mistura);
	}

	


}
