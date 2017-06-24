package br.pucpr.cg;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Pentagon implements Scene {
	private Mesh mesh;
	private float angle;
    private Camera camera = new Camera();

	@Override
	public void init() {
		glClearColor(0.0f, 0.0f, 0.3f, 1.0f);

		mesh = MeshFactory.createPentagon();
	}

	@Override
	public void update(float secs) {
	}

	@Override
	public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        mesh.getShader()
                .bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
                .unbind();
		mesh
		.setUniform("uWorld", new Matrix4f().rotateY(angle))
		.draw();
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new Pentagon()).show();
	}
}
