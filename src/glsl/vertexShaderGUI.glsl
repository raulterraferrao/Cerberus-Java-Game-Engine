#version 330 core

in vec2 pos;

out vec2 coordenadasDaTextura;

uniform mat4 matrizDeTransformacao;

void main(void){

	gl_Position = matrizDeTransformacao * vec4(pos, 0.0, 1.0);
	
	//Transforma a posição xy em uv(usado em textura)s
	coordenadasDaTextura = vec2((pos.x+1.0)/2.0, 1 - (pos.y+1.0)/2.0);
}