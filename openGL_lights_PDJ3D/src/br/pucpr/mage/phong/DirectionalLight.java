package br.pucpr.mage.phong;

import br.pucpr.mage.Shader;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class DirectionalLight {
    private Vector4f direction;
    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;
    private Vector4f position;
    private float cut;
    
    public DirectionalLight(Vector4f direction, Vector4f ambient, Vector4f diffuse, Vector4f specular, Vector4f position, float cut) {
        super();
        this.direction = direction;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.position = position;
        this.cut = cut;
    }

    public Vector4f getDirection() {
        return direction;
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public Vector4f getPosition() {
        return position;
    }

    public float getCut() {
        return cut;
    }
    
    public void apply(Shader shader) {
        shader.setUniform("uLightDir", direction.normalize(new Vector4f()));
        shader.setUniform("uAmbientLight", ambient);
        shader.setUniform("uDiffuseLight", diffuse);
        shader.setUniform("uSpecularLight", specular);
        shader.setUniform("uLightPos", specular);
        shader.setUniform("uLightCut", specular);
    }
}
