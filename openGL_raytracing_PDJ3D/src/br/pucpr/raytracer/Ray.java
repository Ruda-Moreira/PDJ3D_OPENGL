package br.pucpr.raytracer;

import org.joml.Vector3f;
import static br.pucpr.raytracer.Vector3fOps.*;

public class Ray {
    private static final float EPSILON = 0.001f;
    
    private Vector3f origin;
    private Vector3f direction;

    public Ray(Vector3f origin, Vector3f direction) {
        this.origin = add(origin, mul(direction, EPSILON));
        this.direction = direction;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public Vector3f getDirection() {
        return direction;
    }

}
