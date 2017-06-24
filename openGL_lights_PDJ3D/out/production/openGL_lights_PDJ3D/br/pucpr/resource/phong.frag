#version 330

in vec3 vNormal;
in vec3 vViewPath;
in vec3 vLightDir;

out vec4 outColor;

//direção da luz difusa
uniform vec4 uLightDir;
//cor da luz amniente
uniform vec4 uAmbientLight;
//cor da luz difusa
uniform vec4 uDiffuseLight;
//cor da luz especular
uniform vec4 uSpecularLight;

//posição da luz difusa //spotlight
uniform vec4 uLightPos;
//limite de penumbra //spotlight
uniform float uLightCut;

//cor do material amniente
uniform vec4 uAmbientMaterial;
//cor do material difusa
uniform vec4 uDiffuseMaterial;
//cor do material especular
uniform vec4 uSpecularMaterial;

//Sensibilidade do material a luz especular
uniform float uSpecularPower;

void main() {
    vec3 N;
    vec3 L = normalize(uLightDir);
    vec3 P = normalize(vec3(-uLightPos));

    vec4 spec = vec4(0.0);

    //calculo com componente especular
    float specularIntensity = 0.0;
    //dentro do cone?
    if(dot(P, L) > uLightCut){
        N = normalize(vNormal);
        specularIntensity = max(dot(N, L), 0.0);
        if(specularIntensity  > 0.0){
            vec3 V = normalize(vViewPath);
            //vec3 R = reflect(L, V);
            vec3 H = normalize(L+V);
            spec = uSpecularLight * pow(max(dot(H, N), 0.0), uSpecularPower);
        }
    }
    vec4 specular = spec * uSpecularMaterial;

    //calculo do camponente ambiente
    vec4 ambient = uAmbientLight * uAmbientMaterial;

    //calculo do componente difuso
    //float intensity	= max(dot(N, -L), 0.0); //cosseno entre N e L
    //vec4 diffuse = clamp(uDiffuseLight * intensity * uDiffuseMaterial, 0.0, 1.0);
    vec4 diffuse = uDiffuseLight * specularIntensity * uDiffuseMaterial;

    //combina os componentes
    //vec3 color = clamp(ambient + diffuse + specular,	0.0, 1.0);
    vec3 color = max(diffuse + specular, ambient);

    outColor = vec4(color, 1.0f);
}