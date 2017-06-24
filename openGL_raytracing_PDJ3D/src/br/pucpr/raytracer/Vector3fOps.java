package br.pucpr.raytracer;

import org.joml.Vector3f;

public class Vector3fOps {
    public static Vector3f add(Vector3f a, Vector3f b) {
        return new Vector3f(a).add(b);
    }
    
    public static Vector3f sub(Vector3f a, Vector3f b) {
        return new Vector3f(a).sub(b);
    }
    
    public static Vector3f mul(Vector3f v, float s) {
        return new Vector3f(v).mul(s);
    }
    
    public static Vector3f mul(float s, Vector3f v) {
        return new Vector3f(v).mul(s);
    }
    
    public static Vector3f mul(Vector3f a, Vector3f b) {
        return new Vector3f(a).mul(b);
    }
    
    public static Vector3f reflect(Vector3f v, Vector3f n) {
        return new Vector3f(v).reflect(n);
    }

}
