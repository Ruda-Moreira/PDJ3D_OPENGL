#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;

uniform vec3 uCameraPosition;

uniform vec4 uLightPos;
uniform vec4 diffuse;

in vec4 aPosition;
in vec3 aNormal;

out vec3 vNormal;
out vec3 vViewPath;
out vec3 vLightDir;

void main() {
    vec4 worldPos = uWorld * aPosition;
    vNormal = (uWorld * vec4(aNormal, 0)).xyz;
    vLightDir = normalize(vec3(uLightPos - worldPos));
    float NdotL = max(dot(vNormal, vLightDir), 0.0);
    vViewPath = uCameraPosition - worldPos.xyz;
    gl_Position =  uProjection * uView * worldPos;
}