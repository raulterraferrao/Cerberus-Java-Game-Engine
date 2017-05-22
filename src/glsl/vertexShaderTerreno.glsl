#version 330 core

in vec3 pos;
in vec2 coordenadasDaTextura;
in vec3 normais;

out vec2 vetorTextura;
out vec3 vetorParaLuz;
out vec3 normalDaSuperficie;
out vec3 vetorParaCamera;
out float visibilidade;

uniform mat4 matrizDeTransformacao;
uniform mat4 matrizDeProjecao;
uniform mat4 matrizDeVisualizacao;
uniform vec3 posicaoDaLuz;

const float densidade = 0.0035;
const float gradiente = 5.0;

void main(void){
	
	//pos_mundo trás a posicao em que o vertice está no ambiente
	//pos_camera trás a posicao onde a cemera está no ambiente
	//pos_relativa_vertice_camera trás a posicao relativa entre o vertice do mundo e a posicao da camera
	
	vec4 pos_mundo = matrizDeTransformacao * vec4(pos,1.0);
	vec4 pos_camera = inverse(matrizDeVisualizacao) * vec4(0,0,0,1);
	vec4 pos_relativa_vertice_camera = matrizDeVisualizacao * pos_mundo;
	
	float distancia_vertice_camera = length(pos_relativa_vertice_camera.xyz);
	
	visibilidade = exp(-pow(distancia_vertice_camera * densidade, gradiente));
	visibilidade = clamp(visibilidade, 0.0, 1.0);
	
	//Onde renderizar os vertices na tela
	
	gl_Position = matrizDeProjecao * matrizDeVisualizacao * pos_mundo;
	
	//O vertexshader não pode lidar com a textura já que ele lida somente com os vertices e não com o que está entre os vertices.
	
	vetorTextura = coordenadasDaTextura;
	

	normalDaSuperficie = (matrizDeTransformacao * vec4(normais,0.0)).xyz;
	vetorParaLuz = posicaoDaLuz - pos_mundo.xyz;
	
	vetorParaCamera = pos_camera.xyz - pos_mundo.xyz;
	
	
	

}