package br.pucpr.raytracer;

import static br.pucpr.raytracer.Vector3fOps.*;

import org.joml.Vector3f;

import br.pucpr.raytracer.primitives.Primitive;
import br.pucpr.raytracer.primitives.Result;

public class Raytracer {
    private static final float INFINITE = 1000000.0f;
    private static final int TRACEDEPTH = 6;

    private Canvas canvas;
    private Scene scene = new Scene();
    private float wx1, wx2, wy1, wy2, dx, dy;

    public Raytracer() {
        this(1.5f);
    }

    public Raytracer(float zoom) {
        this.canvas = new Canvas(1024, 768);
        float aspect = (canvas.getWidth() / canvas.getHeight()) * 10;
        wx1 = -(aspect / 2.0f) / zoom;
        wx2 = -wx1;
        wy1 = (10.0f / 2.0f) / zoom;
        wy2 = -wy1;
        dx = (wx2 - wx1) / canvas.getWidth();
        dy = (wy2 - wy1) / canvas.getHeight();
    }

    public void render() {
        Vector3f o = new Vector3f(0, 0, -5);
        float sy = wy1;
        for (int y = 0; y < canvas.getHeight(); y++) {
            float sx = wx1;
            for (int x = 0; x < canvas.getWidth(); x++) {
                //Dir.	feixe:	(pixel	-	origem).normalize();
                Vector3f dir = new Vector3f(sx, sy, 0).sub(o).normalize();
                Ray ray = new Ray(o, dir);
                Vector3f color = raytrace(ray, 0);
                canvas.set(x, y, color);
                sx += dx;
            }
            sy += dy;
        }
        canvas.save();
    }

    public Vector3f raytrace(Ray ray, int depth) {
        if(depth > TRACEDEPTH){
            return new Vector3f();
        }
        float dist = INFINITE;
        Primitive prim = null;
        //	find	the	nearest	intersection
        for (Primitive obj : scene.getObjects()) {
            Result result = obj.intersect(ray, dist);
            if (result.isHit()) {
                dist = result.getDistance();
                prim = obj;
            }
        }
        if (prim == null) {
            return scene.getBackground();
        }
        //Point of intersection calculation
        Vector3f pi = add(ray.getOrigin(), mul(ray.getDirection(), dist));
        Vector3f color = new Vector3f();
        for (Light light : scene.getLights()) {
            Vector3f L = sub(light.getPosition(), pi);
            float ldist = L.length();
            L.div(ldist);
            //Traces	a	secondary ray from the point of intersection
            //towards the	light	and see if this point is	in	shadows.
            Ray shadowRay = new Ray(pi, L);
            boolean lit = true;
            for (Primitive obj : scene.getObjects())
                if (obj.intersect(shadowRay, ldist).isHit()) {
                    lit = false;
                    break;
                }
            if (!lit) {
                continue;
            }
            Vector3f N = prim.getNormalAt(pi);
            if (prim.getMaterial().getDiffuse() > 0) {
                float dot = N.dot(L);
                if (dot > 0) {
                    float diff = dot * prim.getMaterial().getDiffuse();
                    // add diffuse component to ray color
                    // color += diff * light * material;
                    Vector3f diffuse =
                            mul(diff, light.getColor()).mul(prim.getColor());
                    color.add(diffuse);
                }
            }
            // determine specular component
            if (prim.getMaterial().getSpecular() > 0) {
                Vector3f V = ray.getDirection();
                Vector3f R = reflect(L, N);
                float dot = V.dot(R);
                if (dot > 0) {
                    float specularIntensity = (float) Math.pow(dot, prim.getMaterial().getSpecular());
                    // color += specularIntensity * light;
                    color.add(mul(specularIntensity, light.getColor()));
                }
            }
        }
        // calculate reflection
        float refl = prim.getMaterial().getReflection();
        if (refl > 0.0f) {
            Vector3f N = prim.getNormalAt(pi);
            Vector3f R = reflect(ray.getDirection(), N);
            Vector3f rcol = raytrace(new Ray(pi, R), depth + 1);
            color.lerp(rcol, refl);
        }
        return color;
    }
}
