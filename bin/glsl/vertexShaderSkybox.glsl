#version 330 core

in vec3 pos;
out vec3 vetorTextura;

uniform mat4 matrizDeProjecao;
uniform mat4 matrizDeVisualizacao;

void main(void){
	
	gl_Position = matrizDeProjecao * matrizDeVisualizacao * vec4(pos, 1.0); 
	vetorTextura = pos;
	
}