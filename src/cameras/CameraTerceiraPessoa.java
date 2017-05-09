package cameras;

import entidades.Jogador;
import entradas.MeuMouse;

public class CameraTerceiraPessoa extends Camera{

		private float distanciaDoJogador = 150;
		private float anguloEmVoltaDoJogador = 0;
		private float sensitividade = 2;
		
		private Jogador jogador;
		
		public CameraTerceiraPessoa(Jogador jogador){
			this.jogador = jogador;
		}
		
		public void mover(){
			calcularZoom();
			calcularAnguloVertical();
			calcularAnguloHorizontal();
			float distanciaHorizontal = calcularDistanciaHorizontal();
			float distanciaVertical = calcularDistanciaVertical();
			calcularPosicaoDaCamera(distanciaHorizontal, distanciaVertical);
			this.yaw = 180 - (anguloEmVoltaDoJogador + jogador.getRoty());
		}
		
		private void calcularZoom(){
			float zoom = MeuMouse.getDeltaRodinha();
			distanciaDoJogador -= zoom * sensitividade;
		}
		
		private void calcularAnguloVertical(){
			if(MeuMouse.isBotaoDireito()){
				float mudarAnguloVertical = MeuMouse.getDeltaY(); 
				super.pitch -= mudarAnguloVertical * sensitividade;
			}
		}
		
		private void calcularPosicaoDaCamera(float distanciaHorizontal, float distanciaVertical){
			float anguloTeta = jogador.getRoty() + anguloEmVoltaDoJogador;
			float posX = (float) (distanciaHorizontal * Math.sin(Math.toRadians(anguloTeta)));
			float posZ = (float) (distanciaHorizontal * Math.cos(Math.toRadians(anguloTeta)));
			this.posicao.x = jogador.getPosicaoX() - posX;
			this.posicao.z = jogador.getPosicaoZ() - posZ;
			this.posicao.y = jogador.getPosicaoY() + distanciaVertical;
		}
		
		
		private void calcularAnguloHorizontal(){
			if(MeuMouse.isBotaoEsquerdo()){
				float mudarAnguloHorizontal = MeuMouse.getDeltaX(); 
				anguloEmVoltaDoJogador -= mudarAnguloHorizontal * sensitividade;
			}
		}
		
		private float calcularDistanciaHorizontal(){
			return (float) (distanciaDoJogador * Math.cos(Math.toRadians(super.pitch)));
		}
		private float calcularDistanciaVertical(){
			return (float) (distanciaDoJogador * Math.sin(Math.toRadians(super.pitch)));
		}
}
