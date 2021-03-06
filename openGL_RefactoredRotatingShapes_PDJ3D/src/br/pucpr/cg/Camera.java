package br.pucpr.cg;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Bruno on 08/05/2017.
 */
public class Camera {
    private Vector3f position = new Vector3f(0, 0, 2);
    private Vector3f up = new Vector3f(0, 1, 0);
    private Vector3f direction = new Vector3f(0, 0, -1);

    private float fov = (float) Math.toRadians(60);

    private float near = 0.1f;
    private float far = 1000.0f;

    public float getFov() {
        return fov;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public void setFar(float far) {
        this.far = far;
    }

    private float getAspect() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);

        long window = glfwGetCurrentContext();
        glfwGetWindowSize(window, w, h);

        return w.get() / (float) h.get();
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(position, new Vector3f(direction).add(position), up);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(
                fov, getAspect(), near, far);
    }

    public void moveFront(float distance){
        Vector3f aux = new Vector3f(direction);
        aux.mul(distance);
        position.add(aux);
    }

    public void strafeLeft(float distance){
        Vector3f aux = new Vector3f(direction).cross(up);
        aux.mul(distance);
        position.add(aux);
    }

    public void strafeRight(float distance){
        Vector3f aux = new Vector3f(direction).cross(up);
        aux.mul(distance);
        position.add(aux);
    }

    public void rotateInY(float angle){
        new Matrix3f().rotateY(angle).transform(direction);
    }


}