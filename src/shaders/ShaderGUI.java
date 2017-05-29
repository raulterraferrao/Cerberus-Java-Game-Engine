package shaders;


import estruturasDeDados.Matriz4f;
import estruturasDeDados.Vetor2f;
import gui.TexturaGUI;
import matrizesDeTransformacao.MatrizDeTransformacao;

public class ShaderGUI extends ProgramaShaderPadrao{
	
	private static final String ARQUIVO_VERTEX = "src/glsl/vertexShaderGUI.glsl";
	private static final String ARQUIVO_FRAGMENT = "src/glsl/fragmentShaderGUI.glsl";
	
	private static final int POSICAO = 0;
	
	private int localidade_MatrizDeTransformacao;

	public ShaderGUI() {
		super(ARQUIVO_VERTEX, ARQUIVO_FRAGMENT);
	}
	
	protected void conectarAtributos() {
		super.conectarAtributo(POSICAO, "pos");
	}	
	
	protected void getLocalidadeDeTodosUniform() {
		localidade_MatrizDeTransformacao = super.getLocalidadeUniform("matrizDeTransformacao");
	}
	
	public void carregarMatrizDeTransformacao(TexturaGUI gui){
		Matriz4f transformacao = MatrizDeTransformacao.criarMatrizDeTransformacao(new Vetor2f(gui.getPosicao().x, gui.getPosicao().y),
				new Vetor2f(gui.getEscala().x, gui.getEscala().y));
		super.carregarMatriz4f(localidade_MatrizDeTransformacao, transformacao);
	}

	
	
	

}
