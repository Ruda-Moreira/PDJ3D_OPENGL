package br.pucpr.raytracer.primitives;

import org.joml.Vector3f;

import br.pucpr.raytracer.Material;
import br.pucpr.raytracer.Ray;

public class Plane extends Primitive {
    private Vector3f normal;
    private float distance;

    public Plane(Vector3f normal, float distance, Material material) {
        super(material);
        this.normal = normal;
        this.distance = distance;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public Vector3f getNormalAt(Vector3f pos) {
        return normal;
    }

    @Override
    public Result intersect(Ray ray, float distance) {
        float d = normal.dot(ray.getDirection());
        if (d != 0) {
            float dst = -(normal.dot(ray.getOrigin()) + this.distance) / d;
            if (dst > 0 && dst < distance) {
                return new Result(true, dst);
            }
        }
        return new Result(false, distance);
    }

}
