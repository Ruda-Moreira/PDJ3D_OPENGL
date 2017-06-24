package br.pucpr.raytracer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Vector3f;

import br.pucpr.raytracer.primitives.Plane;
import br.pucpr.raytracer.primitives.Primitive;
import br.pucpr.raytracer.primitives.Sphere;

public class Scene {
    private Vector3f background = new Vector3f(0,0,0);
    private List<Primitive> objects = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

    public Scene() {
        // Chao
        Material tint = new Material(new Vector3f(0.9f, 0.7f, 0.7f), 0.6f, 0.0f, 0.2f);
        objects.add(new Plane(new Vector3f(0, 1, 0), 2.0f, tint));

        // Parede
        objects.add(new Plane(new Vector3f(1, 0, -1).normalize(), 12.0f, tint));

        // Esfera proxima a parede
        objects.add(new Sphere(new Vector3f(-5.5f, -0.5f, 6.0f), 2.0f,
                new Material(new Vector3f(0.6f, 0.0f, 0.6f), 0.2f, 20.0f, 0.8f)));

        // Esfere proxima da camera
        objects.add(new Sphere(new Vector3f(1.0f, 0.0f, 3.0f), 2.5f,
                new Material(new Vector3f(0.7f, 0.8f, 0.8f), 0.9f, 20.0f, 0.3f)));

        // Luzes
        Vector3f yellow = new Vector3f(0.7f, 0.7f, 0.6f);
        lights.add(new Light(new Vector3f(0, 5, 5), yellow));
        lights.add(new Light(new Vector3f(2, 5, 1), yellow));
    }

    public Vector3f getBackground() {
        return background;
    }

    public List<Primitive> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public List<Light> getLights() {
        return Collections.unmodifiableList(lights);
    }
}
