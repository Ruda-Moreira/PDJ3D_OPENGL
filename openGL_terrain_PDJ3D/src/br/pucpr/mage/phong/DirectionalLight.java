package br.pucpr.mage.phong;

import org.joml.Vector3f;

import br.pucpr.mage.Shader;

public class DirectionalLight {
    private Vector3f direction;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    
    public DirectionalLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular) {
        super();
        this.direction = direction;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }
    
    public void apply(Shader shader) {
        shader.setUniform("uLightDir", direction.normalize(new Vector3f()));
        shader.setUniform("uAmbientLight", ambient);
        shader.setUniform("uDiffuseLight", diffuse);
        shader.setUniform("uSpecularLight", specular);
    }
}
