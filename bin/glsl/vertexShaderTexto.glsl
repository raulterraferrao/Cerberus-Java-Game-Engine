#version 330

in vec2 pos;
in vec2 coordenadasDaTextura;

out vec2 vetorTextura;

uniform vec2 translacao;

void main(void){

	gl_Position = vec4(pos + translacao * vec2(2.0, -2.0), 0.0, 1.0);
	vetorTextura = coordenadasDaTextura;

}