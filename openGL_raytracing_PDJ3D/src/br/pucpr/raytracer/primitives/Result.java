package br.pucpr.raytracer.primitives;

public class Result {
    private boolean hit;
    private float distance;

    public Result(boolean hit, float distance) {
        this.hit = hit;
        this.distance = distance;
    }

    public boolean isHit() {
        return hit;
    }

    public float getDistance() {
        return distance;
    }
}
