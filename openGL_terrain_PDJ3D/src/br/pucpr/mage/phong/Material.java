package br.pucpr.mage.phong;

import org.joml.Vector3f;

import br.pucpr.mage.Shader;

public class Material {
    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;
    private float specularPower;
    
    public Material(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float specularPower) {
        super();
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularPower = specularPower;
    }
    public Material(Vector3f ambient, Vector3f diffuse) {
        this(ambient, diffuse, new Vector3f(), 0.0f);
    }

    public Material(Vector3f color) {
        this(color, color, new Vector3f(), 0.0f);
    }
    
    public Material() {
        this(new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    public Vector3f getAmbientColor() {
        return ambientColor;
    }
    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }
    public Vector3f getSpecularColor() {
        return specularColor;
    }
    public float getSpecularPower() {
        return specularPower;
    }
    
    public void setSpecularPower(float specularPower) {
        this.specularPower = specularPower;
    }    
    
    public void apply(Shader shader) {
        shader.setUniform("uAmbientMaterial", ambientColor);
        shader.setUniform("uDiffuseMaterial", diffuseColor);
        shader.setUniform("uSpecularMaterial", specularColor);
        shader.setUniform("uSpecularPower", specularPower);
    }
}
