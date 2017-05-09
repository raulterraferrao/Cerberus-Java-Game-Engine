package entidades;

import org.lwjgl.input.Keyboard;

import entradas.MeuTeclado;
import estruturasDeDados.Vetor3f;
import objetos.ObjetoComTextura;
import renderizador.GerenciadorDeTempo;

public class Jogador extends Entidade{
	
	private int velocidadeDeMovimento = 20;
	private int velocidadeDeRotacao = 160;
	private float gravidade = -50;
	private float velocidadeY = 0;
	private float pulo = 30;
	private float rotacionar = 0;
	private float movimentar = 0;
	private boolean estouNoChao = true;
	
	private final float ALTURA_TERRENO = 0f;

	public Jogador(ObjetoComTextura objetoComTextura, Vetor3f posicao, float rotx, float roty,
			float rotz, float escala) {

			super(objetoComTextura, posicao, rotx, roty, rotz, escala);
	}
	
	public Jogador(ObjetoComTextura objetoComTextura, Vetor3f posicao, float rotx, float roty,
					float rotz, float escala, int velocidadeDeMovimento, int velocidadeDeRotacao ) {
		
		super(objetoComTextura, posicao, rotx, roty, rotz, escala);
		this.velocidadeDeMovimento = velocidadeDeMovimento;
		this.velocidadeDeRotacao = velocidadeDeRotacao;
	}
	
	public Jogador(ObjetoComTextura objetoComTextura, Vetor3f posicao, float rotx, float roty,
			float rotz, float escala, int velocidadeDeMovimento, int velocidadeDeRotacao, float gravidade, float pulo ) {

		super(objetoComTextura, posicao, rotx, roty, rotz, escala);
		this.velocidadeDeMovimento = velocidadeDeMovimento;
		this.velocidadeDeRotacao = velocidadeDeRotacao;
		this.gravidade = gravidade;
		this.pulo = pulo;
}
	
	public void mover(){
		float delta = GerenciadorDeTempo.getDelta();
		entradas();
		aumentarRotacao(0, rotacionar * delta, 0);
		float distancia = movimentar * delta;
		
		//O vetor de distancia é calculado com base no cos e sen em y da sua rotacao
		
		float distanciaX = (float) (Math.sin(Math.toRadians(getRoty())) * distancia);
		float distanciaZ = (float) (Math.cos(Math.toRadians(getRoty())) * distancia);
		aumentarPosicao(distanciaX, 0, distanciaZ);
		velocidadeY += gravidade * delta;
		aumentarPosicao(0,velocidadeY * delta,0);
		if(getPosicaoY() < ALTURA_TERRENO){
			velocidadeY = 0;
			estouNoChao = true;
			setPosicaoY(ALTURA_TERRENO);
		}
		
	}
	
	public void pular(){
		if(estouNoChao){
		velocidadeY = pulo;
		estouNoChao = false;
		}
	}
	
	public void entradas(){
		if(MeuTeclado.estaPressionada(Keyboard.KEY_UP)){
			movimentar = velocidadeDeMovimento;
		}else if(MeuTeclado.estaPressionada(Keyboard.KEY_DOWN)){
			movimentar = -velocidadeDeMovimento;
		}else{
			movimentar = 0;
		}
		
		if(MeuTeclado.estaPressionada(Keyboard.KEY_LEFT)){
			rotacionar = velocidadeDeRotacao;
		}else if(MeuTeclado.estaPressionada(Keyboard.KEY_RIGHT)){
			rotacionar = -velocidadeDeRotacao;
		}else{
			rotacionar = 0;
		}
		
		if(MeuTeclado.estaPressionada(Keyboard.KEY_SPACE)){
			pular();
		}
	}
	
}
