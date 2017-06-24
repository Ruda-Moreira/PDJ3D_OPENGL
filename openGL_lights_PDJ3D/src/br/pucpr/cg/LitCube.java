package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;
import org.joml.Matrix4f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LitCube implements Scene {
    private Keyboard keys = Keyboard.getInstance();

    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light = new DirectionalLight(
            new Vector4f( 1.0f, 1.0f, -1.0f, 0.0f), //direction
            new Vector4f( 0.05f,  0.05f,  0.05f, 0.0f), //ambient
            new Vector4f( 1.0f,  1.0f,  0.8f, 0.0f),   //diffuse
            new Vector4f( 1.0f,  1.0f,  1.0f, 0.0f),  //specular
            new Vector4f(camera.getPosition(),0.0f), //position
            45); //cut

    //Dados da malha
    private Mesh mesh;
    private Material material = new Material(
            new Vector3f(0.80f, 0.0f, 0.80f), //ambient
            new Vector3f(0.80f, 0.0f, 0.80f), //diffuse
            new Vector3f(1.0f, 1.0f, 1.0f), //specular
            256.0f);                    //specular power
    private float angleX = 0.0f;
    private float angleY = 0.5f;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mesh = MeshFactory.createCube();
        camera.getPosition().y = 1.0f;
    }

    @Override
    public void update(float secs) {

        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angleY += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_W)) {
            angleX += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_UP)) {
            camera.moveFront(6 * secs);
        }

        if (keys.isDown(GLFW_KEY_DOWN)) {
            camera.moveFront(-6 * secs);
        }

        if (keys.isDown(GLFW_KEY_LEFT)){
            camera.rotateInY((float)Math.toRadians(180)*secs);
        }

        if (keys.isDown(GLFW_KEY_RIGHT)){
            camera.rotateInY((float)Math.toRadians(-180)*secs);
        }

        if (keys.isDown(GLFW_KEY_Z)){
            camera.strafeLeft(-6* secs);
        }

        if (keys.isDown(GLFW_KEY_C)){
            camera.strafeRight(6* secs);
        }

        if (keys.isDown(GLFW_KEY_SPACE)) {
            angleX = 0;
            angleY = 0;
        }
    }

@Override
public void draw() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    Shader shader = mesh.getShader();
    shader.bind()
        .setUniform("uProjection", camera.getProjectionMatrix())
        .setUniform("uView", camera.getViewMatrix())
        .setUniform("uCameraPosition", camera.getPosition());
    light.apply(shader);
    material.apply(shader);
    shader.unbind();

    mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX));
    mesh.draw();
}

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new LitCube(), "Cube with lights", 800, 600).show();
    }
}
