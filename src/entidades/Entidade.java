package entidades;



import estruturasDeDados.Vetor3f;
import objetos.ObjetoComTextura;

public class Entidade {

	private ObjetoComTextura objetoComTextura;
	private Vetor3f posicao;
	private float rotx,roty,rotz;
	private float escala;
	private int indiceDaTextura = 0;
	/***
	 *  Classe responsável em criar as entidades no game, ela contém o modelo do objeto e as suas respectivas posições,rotação e escala.
	 * @param objetoComTextura
	 * @param posicao
	 * @param rotx
	 * @param roty
	 * @param rotz
	 * @param escala
	 */
	public Entidade(ObjetoComTextura objetoComTextura, Vetor3f posicao, float rotx, float roty, float rotz, float escala) {
		this.objetoComTextura = objetoComTextura;
		this.posicao = posicao;
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
		this.escala = escala;
	}
	
	public Entidade(ObjetoComTextura objetoComTextura, int indiceDaTextura, Vetor3f posicao, float rotx, float roty, float rotz, float escala) {
		this.objetoComTextura = objetoComTextura;
		this.posicao = posicao;
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
		this.escala = escala;
		this.indiceDaTextura = indiceDaTextura;
	}
	
	public float getOffSetXTextura(){
		float qtdLinha = objetoComTextura.getTextura().getQuantidadeDeLinhas();
		float coluna = (indiceDaTextura % qtdLinha);
		return coluna / qtdLinha;
		
	}
	
	public float getOffSetYTextura(){
		float qtdLinha = objetoComTextura.getTextura().getQuantidadeDeLinhas();
		float linha = (indiceDaTextura / qtdLinha);
		return linha / qtdLinha;
	}
	
	public void aumentarPosicao(float posX,float posY,float posZ){
		posicao.x += posX;
		posicao.y += posY;
		posicao.z += posZ;	
	}
	
	
	public void aumentarRotacao(float rotx,float roty,float rotz){
		this.rotx += rotx;
		this.roty += roty;
		this.rotz += rotz;
	}
	
	public ObjetoComTextura getObjetoComTextura() {
		return objetoComTextura;
	}
	public void setObjeto(ObjetoComTextura objetoComTextura) {
		this.objetoComTextura = objetoComTextura;
	}
	public Vetor3f getPosicao() {
		return posicao;
	}
	public void setPosicao(Vetor3f posicao) {
		this.posicao = posicao;
	}
	public void setPosicaoX(float posX){
		posicao.x = posX;
	}
	public void setPosicaoY(float posY){
		posicao.y = posY;
	}
	public void setPosicaoZ(float posZ){
		posicao.z = posZ;
	}
	public float getPosicaoX(){
		return posicao.x;
	}
	public float getPosicaoY(){
		return posicao.y;
	}
	public float getPosicaoZ(){
		return posicao.z;
	}
	
	public float getRotx() {
		return rotx;
	}
	public void setRotx(float rotx) {
		this.rotx = rotx;
	}
	public float getRoty() {
		return roty;
	}
	public void setRoty(float roty) {
		this.roty = roty;
	}
	public float getRotz() {
		return rotz;
	}
	public void setRotz(float rotz) {
		this.rotz = rotz;
	}
	public float getEscala() {
		return escala;
	}
	public void setEscala(float escala) {
		this.escala = escala;
	}
	
	
	
}
