#version 330 core

in vec2 coordenadasDaTextura;

out vec4 cor_saida;

uniform sampler2D TexturaDaGui;

void main(void){

	cor_saida = texture(TexturaDaGui,coordenadasDaTextura);

}