package shaders;

import cameras.Camera;
import estruturasDeDados.Matriz4f;
import estruturasDeDados.Vetor3f;
import luminosidades.Difusa;
import luminosidades.Especular;
import matrizesDeTransformacao.MatrizDeProjecao;
import matrizesDeTransformacao.MatrizDeTransformacao;
import matrizesDeTransformacao.MatrizDeVisualizacao;
import terrenos.Terreno;

public class ShaderTerreno extends ProgramaShaderPadrao{
	
	private static final String ARQUIVO_VERTEX = "src/shaders/vertexShaderTerreno.txt";
	private static final String ARQUIVO_FRAGMENT = "src/shaders/fragmentShaderTerreno.txt";
	
	private static final int POSICAO = 0;
	private static final int TEXTURA = 1;
	private static final int NORMAL = 2;
	
	private int localidade_MatrizDeTransformacao;
	private int localidade_MatrizDeProjecao;
	private int localidade_MatrizDeVisualizacao;
	private int localidade_PosicaoDaLuz;
	private int localidade_CorDaLuz;
	private int localidade_SuperficieReflexiva;
	private int localidade_Reflexividade;
	private int localidade_CorDoCeu;
	private int localidade_TexturaVermelha;
	private int localidade_TexturaVerde;
	private int localidade_TexturaAzul;
	private int localidade_TexturaPreta;
	private int localidade_TexturaDeMistura;
	
	public ShaderTerreno() {
		super(ARQUIVO_VERTEX, ARQUIVO_FRAGMENT);

	}

	@Override
	protected void conectarAtributos() {
		//Conecta o atributo de id x do VAO no shader com o nome passado como parametro
		//e o tem que ter o mesmo nome do 'in' do vertexShader
		super.conectarAtributo(POSICAO,"pos");
		super.conectarAtributo(TEXTURA, "coordenadasDaTextura");
		super.conectarAtributo(NORMAL, "normais");
	}

	@Override
	protected void getLocalidadeDeTodosUniform() {
		localidade_MatrizDeTransformacao = super.getLocalidadeUniform("matrizDeTransformacao");
		localidade_MatrizDeProjecao = super.getLocalidadeUniform("matrizDeProjecao");
		localidade_MatrizDeVisualizacao = super.getLocalidadeUniform("matrizDeVisualizacao");
		localidade_PosicaoDaLuz = super.getLocalidadeUniform("posicaoDaLuz");
		localidade_CorDaLuz = super.getLocalidadeUniform("corDaLuz");
		localidade_SuperficieReflexiva = super.getLocalidadeUniform("superficieReflexiva");
		localidade_Reflexividade = super.getLocalidadeUniform("reflexividade");
		localidade_CorDoCeu = super.getLocalidadeUniform("corDoCeu");
		localidade_TexturaVermelha = super.getLocalidadeUniform("texturaVermelha");
		localidade_TexturaVerde = super.getLocalidadeUniform("texturaVerde");
		localidade_TexturaAzul= super.getLocalidadeUniform("texturaAzul");
		localidade_TexturaPreta = super.getLocalidadeUniform("texturaPreta");
		localidade_TexturaDeMistura = super.getLocalidadeUniform("texturaDeMistura");
	}
	
	public void carregarMatrizDeTransformacao(Terreno terreno){
		
		Matriz4f transformacao = MatrizDeTransformacao.criarMatrizDeTransformacao(new Vetor3f(terreno.getX(),0,terreno.getZ()),
				0, 0, 0, 1);
		
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
	
	public void carregarLuminosidadeDifusa(Difusa luz){
		super.carregarVetor3f(localidade_PosicaoDaLuz, luz.getPosicao());
		super.carregarVetor3f(localidade_CorDaLuz, luz.getCor());
	}
	
	public void carregarLuminosidadeEspecular(Especular reflexo){
		super.carregarFloat(localidade_SuperficieReflexiva, reflexo.getSuperficieReflexiva());
		super.carregarFloat(localidade_Reflexividade, reflexo.getReflexividade());	
	}
	
	public void carregarCorDoCeu(float vermelho, float verde, float azul){
		super.carregarVetor3f(localidade_CorDoCeu, new Vetor3f(vermelho, verde, azul));
	}
	
	public void carregarTexturasTerreno(){
		super.carregarInteiro(localidade_TexturaVermelha, 0);
		super.carregarInteiro(localidade_TexturaVerde, 1);
		super.carregarInteiro(localidade_TexturaAzul, 2);
		super.carregarInteiro(localidade_TexturaPreta, 3);
		super.carregarInteiro(localidade_TexturaDeMistura, 4);
	}
}
