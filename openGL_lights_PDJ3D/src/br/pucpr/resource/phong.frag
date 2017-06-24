#version 330

in vec3 vNormal;
in vec3 vViewPath;

out vec4 outColor;

//direção da luz difusa
uniform vec3 uLightDir;
//cor da luz amniente
uniform vec3 uAmbientLight;
//cor da luz difusa
uniform vec3 uDiffuseLight;
//cor da luz especular
uniform vec3 uSpecularLight;

//cor do material amniente
uniform vec3 uAmbientMaterial;
//cor do material difusa
uniform vec3 uDiffuseMaterial;
//cor do material especular
uniform vec3 uSpecularMaterial;

//Sensibilidade do material a luz especular
uniform float uSpecularPower;

void main() {
    vec3 N = normalize(vNormal);
    vec3 L = normalize(uLightDir);

    //calculo do camponente ambiente
    vec3 ambient = uAmbientLight * uAmbientMaterial;

    //calculo do componente difuso
    float intensity	= max(dot(N, -L), 0.0); //cosseno entre N e L
    vec3 diffuse = clamp(uDiffuseLight * intensity * uDiffuseMaterial, 0.0, 1.0);

    //calculo com componente especular
    float specularIntensity = 0.0;
    if(uSpecularPower > 0.0){
        vec3 V = normalize(vViewPath);
        vec3 R = reflect(L, N);
        specularIntensity = pow(max(dot(R, V), 0.0), uSpecularPower);
    }
    vec3 specular = specularIntensity * uSpecularLight * uSpecularMaterial;

    //combina os componentes
    vec3 color = clamp(ambient + diffuse + specular,	0.0, 1.0);

    outColor = vec4(color, 1.0f);
}