package br.pucpr.cg;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;

public class RotatingSquare implements Scene {
    private Mesh mesh;
    private float angle;
    private Camera camera = new Camera();

    @Override
    public void init() {
        glClearColor(0.0f, 0.0f, 0.2f, 1.0f);

        mesh = MeshFactory.createSquare();
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        mesh.getShader()
                .bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
                .unbind();
        mesh.setUniform("uWorld", new Matrix4f().rotateY(angle));
        mesh.draw();
    }

    @Override
    public void update(float secs) {
        angle += Math.toRadians(180) * secs;
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new RotatingSquare()).show();
    }
}
