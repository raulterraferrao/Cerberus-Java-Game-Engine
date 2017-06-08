package fontRendering;


import estruturasDeDados.Vetor2f;
import estruturasDeDados.Vetor3f;
import shaders.ProgramaShaderPadrao;

public class FontShader extends ProgramaShaderPadrao{

	private static final String ARQUIVO_VERTEX = "src/glsl/vertexShaderTexto.glsl";
	private static final String ARQUIVO_FRAGMENT = "src/glsl/fragmentShaderTexto.glsl";
	
	private static final int POSICAO = 0;
	private static final int TEXTURA = 1;
	
	private int localidade_Cor;
	private int localidade_Translacao;
	
	public FontShader() {
		super(ARQUIVO_VERTEX, ARQUIVO_FRAGMENT);
	}

	@Override
	protected void getLocalidadeDeTodosUniform() {
		localidade_Cor = super.getLocalidadeUniform("cor");
		localidade_Translacao = super.getLocalidadeUniform("translacao");
	}

	@Override
	protected void conectarAtributos() {
		super.conectarAtributo(POSICAO, "pos");
		super.conectarAtributo(TEXTURA, "coordenadasDaTextura");
	}
	
	protected void loadColour(Vetor3f cor){
		super.carregarVetor3f(localidade_Cor, cor);
	}
	
	protected void loadTranslation(Vetor2f translacao){
		super.carregarVetor2f(localidade_Translacao, translacao);
	}


}
