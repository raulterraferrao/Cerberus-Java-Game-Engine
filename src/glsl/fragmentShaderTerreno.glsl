#version 330 core

in vec2 vetorTextura;
in vec3 normalDaSuperficie;
in vec3 vetorParaLuz[4];
in vec3 vetorParaCamera;
in float visibilidade;


out vec4 cor_saida;

//O textureSample recebe a textura que ativamos no glActiveTexture e demos bind no glBindTexture na classe
// Renderizador no método renderizar.

uniform sampler2D texturaVermelha;
uniform sampler2D texturaVerde;
uniform sampler2D texturaAzul;
uniform sampler2D texturaPreta;
uniform sampler2D texturaDeMistura;



uniform vec3 corDaLuz[4];
uniform vec3 atenuacao[4];
uniform float superficieReflexiva;
uniform float reflexividade;
uniform vec3 corDoCeu;

void main(void){

	vec3 normalDaSuperficieNormalizada = normalize(normalDaSuperficie);
	vec3 vetorParaCameraNormalizada = normalize(vetorParaCamera);

	
	vec4 corTexturaDeMistura = texture(texturaDeMistura,vetorTextura);
	
	vec2 tiledTextura = vetorTextura * 40;
	vec4 corTexturaVermelha = texture(texturaVermelha, tiledTextura) * corTexturaDeMistura.r;
	vec4 corTexturaVerde = texture(texturaVerde, tiledTextura) * corTexturaDeMistura.g;
	vec4 corTexturaAzul = texture(texturaAzul, tiledTextura) * corTexturaDeMistura.b;
	float valorTexturaPreta = 1 - (corTexturaDeMistura.r + corTexturaDeMistura.g + corTexturaDeMistura.b);
	vec4 corTexturaPreta = texture(texturaPreta, tiledTextura) * valorTexturaPreta;
	
	vec4 corFinal = corTexturaVermelha + corTexturaVerde + corTexturaAzul + corTexturaPreta;
	
	vec3 totalDifusa = vec3(0.0);
	vec3 totalEspecular = vec3(0.0);
	
	for(int i = 0; i < 4 ; i++){
		float distancia = length(vetorParaLuz[i]);
		float totalAtenuacao = atenuacao[i].x + (atenuacao[i].y * distancia) + (atenuacao[i].z * distancia * distancia);
		
		
		vec3 vetorParaLuzNormalizada = normalize(vetorParaLuz[i]);
		vec3 vetorDaLuzNormalizada = - vetorParaLuzNormalizada;
		
		
		//Calculo para a luz difusa
		float brilho = dot(normalDaSuperficieNormalizada,vetorParaLuzNormalizada);
		float intensidade = max(brilho,0.0);		
		totalDifusa = totalDifusa + (intensidade * corDaLuz[i])/totalAtenuacao;
		
		//Calculo para a luz especular		
		vec3 vetorLuzRefletida = reflect(vetorDaLuzNormalizada,normalDaSuperficieNormalizada);
		float fatorReflexivo = dot(vetorLuzRefletida,vetorParaCameraNormalizada);
		fatorReflexivo = max(fatorReflexivo,0);
		float reflexo = pow(fatorReflexivo,superficieReflexiva);
		totalEspecular = totalEspecular + (reflexo * reflexividade * corDaLuz[i])/totalAtenuacao;
	
	}
	
	totalDifusa = max(totalDifusa, 0.2);
	
	
	//texture essa é uma função que pega a textura que passamos e combina com os pixels do nosso objeto e retorna 
	//a cor de cada pixel no cor_saida, podemos dar strech na textura se colocarmos os vertices como 0.5 ao inves de 1. 

	cor_saida = vec4(totalDifusa,1.0) * corFinal + vec4(totalEspecular,1.0);
	
	//Aqui nós fazemos o fog
	
	cor_saida = mix(vec4(corDoCeu,1.0),cor_saida,visibilidade);

}