#version 330

in vec2 vetorTextura;

out vec4 cor_saida;

uniform vec3 cor;
uniform sampler2D pngAtlasTexto;

void main(void){

	cor_saida = vec4(cor, texture(pngAtlasTexto, vetorTextura).a);

}