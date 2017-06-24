package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;

public class Terrain implements Scene {
    private Keyboard keys = Keyboard.getInstance();
    
    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light = new DirectionalLight(
            new Vector3f( 1.0f, -3.0f, -1.0f), //direction
            new Vector3f( 0.02f,  0.02f,  0.02f),   //ambient
            new Vector3f( 1.0f,  1.0f,  1.0f),   //diffuse
            new Vector3f( 1.0f,  1.0f,  1.0f));  //specular

    //Dados da malha
    private Mesh mesh;
    private Material material = new Material(
            new Vector3f(1.0f, 1.0f, 1.0f), //ambient
            new Vector3f(0.7f, 0.7f, 0.7f), //diffuse
            new Vector3f(1.0f, 1.0f, 1.0f), //specular
            512.0f);                         //specular power    
    private float angleX = 0.0f;
    private float angleY = 0.5f;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        try {
            mesh = MeshFactory.loadTerrain(new File("..\\..\\img\\img\\opengl\\heights/volcano.png"), 0.5f);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        camera.getPosition().y = 200.0f;
        camera.getPosition().z = 200.0f;
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
        new Window(new Terrain(), "Terrain with lights", 1024, 768).show();
    }
}
