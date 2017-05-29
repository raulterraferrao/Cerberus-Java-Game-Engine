package shaders;


import java.util.List;

import cameras.Camera;
import entidades.Entidade;
import estruturasDeDados.Matriz4f;
import estruturasDeDados.Vetor2f;
import estruturasDeDados.Vetor3f;
import luminosidades.Difusa;
import luminosidades.Especular;
import matrizesDeTransformacao.MatrizDeProjecao;
import matrizesDeTransformacao.MatrizDeTransformacao;
import matrizesDeTransformacao.MatrizDeVisualizacao;
import texturas.TexturaDeEntidade;

public class ShaderEntidade extends ProgramaShaderPadrao {

	private static final String ARQUIVO_VERTEX = "src/glsl/vertexShaderEntidade.glsl";
	private static final String ARQUIVO_FRAGMENT = "src/glsl/fragmentShaderEntidade.glsl";
	
	private static final int POSICAO = 0;
	private static final int TEXTURA = 1;
	private static final int NORMAL = 2;
	private static final int MAX_LUZ = 4;
	
	private int localidade_MatrizDeTransformacao;
	private int localidade_MatrizDeProjecao;
	private int localidade_MatrizDeVisualizacao;
	private int localidade_PosicaoDaLuz[];
	private int localidade_CorDaLuz[];
	private int localidade_Atenuacao[];
	private int localidade_SuperficieReflexiva;
	private int localidade_Reflexividade;
	private int localidade_IluminosidadeFalsa;
	private int localidade_CorDoCeu;
	private int localidade_QuantidadeDeLinhas;
	private int localidade_Offset;
	
	public ShaderEntidade() {
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
		localidade_SuperficieReflexiva = super.getLocalidadeUniform("superficieReflexiva");
		localidade_Reflexividade = super.getLocalidadeUniform("reflexividade");
		localidade_IluminosidadeFalsa = super.getLocalidadeUniform("iluminosidadeFalsa");
		localidade_CorDoCeu = super.getLocalidadeUniform("corDoCeu");
		localidade_QuantidadeDeLinhas = super.getLocalidadeUniform("quantidadeDeLinhas");
		localidade_Offset = super.getLocalidadeUniform("offset");
		
		localidade_PosicaoDaLuz = new int[MAX_LUZ];
		localidade_CorDaLuz = new int[MAX_LUZ];
		localidade_Atenuacao = new int[MAX_LUZ];
		
		for(int i = 0 ; i < MAX_LUZ; i++){
			localidade_PosicaoDaLuz[i] = super.getLocalidadeUniform("posicaoDaLuz[" + i + "]");
			localidade_CorDaLuz[i] = super.getLocalidadeUniform("corDaLuz[" + i + "]");
			localidade_Atenuacao[i] = super.getLocalidadeUniform("atenuacao[" + i + "]");
		}
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
	
	public void carregarLuminosidadesDifusa(List<Difusa> luzes){
		for(int i = 0; i < MAX_LUZ; i++){
			if(i < luzes.size()){
			super.carregarVetor3f(localidade_PosicaoDaLuz[i], luzes.get(i).getPosicao());
			super.carregarVetor3f(localidade_CorDaLuz[i], luzes.get(i).getCor());
			super.carregarVetor3f(localidade_Atenuacao[i], luzes.get(i).getAtenuacao());
			}else{
				super.carregarVetor3f(localidade_PosicaoDaLuz[i], new Vetor3f(0, 0, 0));
				super.carregarVetor3f(localidade_CorDaLuz[i], new Vetor3f(0, 0, 0));
				super.carregarVetor3f(localidade_Atenuacao[i], new Vetor3f(1, 0, 0));
			}
		}
	}
	
	public void carregarLuminosidadeEspecular(Especular reflexo){
		super.carregarFloat(localidade_SuperficieReflexiva, reflexo.getSuperficieReflexiva());
		super.carregarFloat(localidade_Reflexividade, reflexo.getReflexividade());	
	}
	
	public void carregarIluminosidadeFalsa(TexturaDeEntidade textura){
		super.carregarBooleano(localidade_IluminosidadeFalsa, textura.isIluminosidadeFalsa());
	}
	
	public void carregarCorDoCeu(float vermelho, float verde, float azul){
		super.carregarVetor3f(localidade_CorDoCeu, new Vetor3f(vermelho, verde, azul));
	}
	
	public void carregarOffset(float offsetX, float offsetY){
		super.carregarVetor2f(localidade_Offset, new Vetor2f(offsetX, offsetY));
	}
	public void carregarQtdLinhas(int linhas){
		super.carregarFloat(localidade_QuantidadeDeLinhas, linhas);
	}
}
