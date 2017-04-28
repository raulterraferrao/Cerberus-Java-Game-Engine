package shaders;


import entidades.Camera;
import entidades.Entidade;
import estruturasDeDados.Matriz4f;
import matrizesDeTransformacao.MatrizDeProjecao;
import matrizesDeTransformacao.MatrizDeTransformacao;
import matrizesDeTransformacao.MatrizDeVisualizacao;

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
	
	public void carregarMatrizDeTransformacao(Entidade entidade){
		
		Matriz4f transformacao = MatrizDeTransformacao.criarMatrizDeTransformacao(entidade.getPosicao(),
				entidade.getRotx(), entidade.getRoty(), entidade.getRotz(), entidade.getEscala());
		
		super.carregarMatriz4f(localidade_MatrizDeTransformacao, transformacao);
	}
	
	public void carregarMatrizDeProjecao(){
		MatrizDeProjecao.criarMatrizDeProjecao();
		Matriz4f projecao = new Matriz4f();
		projecao = MatrizDeProjecao.getMatrizDeProjecao();
		super.carregarMatriz4f(localidade_MatrizDeProjecao, projecao);
	}
	public void carregarMatrizDeVisualizacao(Camera camera){
		Matriz4f visualizacao = MatrizDeVisualizacao.criarMatrizDeVisualizacao(camera);
		super.carregarMatriz4f(localidade_MatrizDeVisualizacao, visualizacao);
	}

}
