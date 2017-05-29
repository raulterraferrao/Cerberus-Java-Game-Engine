#version 330 core

in vec3 vetorTextura;
out vec4 cor_saida;

const float limiteInferior = 0.0;
const float limiteSuperior = 30.0;

uniform vec3 corDoCeu;
uniform float mistura;


uniform samplerCube cubeMap1;
uniform samplerCube cubeMap2;

void main(void){
    
    vec4 textura1 = texture(cubeMap1, vetorTextura);
    vec4 textura2 = texture(cubeMap2, vetorTextura);
    
    vec4 cor = mix(textura1,textura2,mistura);
    
    float fator = (vetorTextura.y - limiteInferior) / (limiteSuperior - limiteInferior);
    fator = clamp(fator, 0.0, 1.0);
    cor_saida = mix(vec4(corDoCeu,1.0),cor,fator);
}