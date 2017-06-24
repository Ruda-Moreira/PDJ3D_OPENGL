package br.pucpr.raytracer;

import org.joml.Vector3f;


public class Material {
    private Vector3f color;
    private float diffuse;
    private float specular;
    private float reflection;

    public Material(Vector3f color, float diffuse, float specular, float reflection) {
        this.color = color;
        this.diffuse = diffuse;
        this.reflection = reflection;
        this.specular = specular;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getDiffuse() {
        return diffuse;
    }

    public float getSpecular() {
        return specular;
    }

    public float getReflection() {
        return reflection;
    }
}
