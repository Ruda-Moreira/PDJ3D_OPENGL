#version 330

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uWorld;

in vec3 aPosition;
in vec3 aColor;

out vec4 vColor;

void main(){
    gl_Position =  uProjection* uView * uWorld * vec4(aPosition, 1.0);
    vColor = vec4(aColor, 1.0);
}