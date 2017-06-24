package br.pucpr.mage;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Mesh {
    private int id;
    private Shader shader;
    private IndexBuffer indexBuffer;

    private Map<String, ArrayBuffer> attributes = new HashMap<>();
    private Map<String, Uniform> uniforms = new HashMap<>();

    Mesh() {
        id = glGenVertexArrays();
    }

    public int getId() {
        return id;
    }

    Mesh setIndexBuffer(IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
        return this;
    }

    public Mesh setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    public Shader getShader() {
        return shader;
    }

    void addAttribute(String name, ArrayBuffer data) {
        if (attributes.containsKey(name)) {
            throw new IllegalArgumentException("Attribute already exists: " + name);
        }
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }
        
        attributes.put(name, data);
    }

    private Mesh setUniform(String name, UniformType type, Object value) {
        if (value == null)
            uniforms.remove(name);
        else {
            uniforms.put(name, new Uniform(type, value));
        }
        return this;
    }

    public Mesh setUniform(String name, Matrix3f matrix) {
        return setUniform(name, UniformType.Matrix3f, matrix);
    }

    public Mesh setUniform(String name, Matrix4f matrix) {
        return setUniform(name, UniformType.Matrix4f, matrix);
    }

    public Mesh setUniform(String name, Vector2f vector) {
        return setUniform(name, UniformType.Vector2f, vector);
    }

    public Mesh setUniform(String name, Vector3f vector) {
        return setUniform(name, UniformType.Vector3f, vector);
    }

    public Mesh setUniform(String name, Vector4f vector) {
        return setUniform(name, UniformType.Vector4f, vector);
    }

    public Mesh setUniform(String name, float value) {
        return setUniform(name, UniformType.Float, value);
    }
    
    public Mesh setUniform(String name, int value) {
        return setUniform(name, UniformType.Integer, value);
    }

    public Mesh setUniform(String name, boolean value) {
        return setUniform(name, UniformType.Boolean, value);
    }

    public Mesh draw() {
        if (shader == null || attributes.size() == 0) {
            return this;
        }

        glBindVertexArray(id);
        shader.bind();
        for (Map.Entry<String, ArrayBuffer> attribute : attributes.entrySet()) {
            ArrayBuffer buffer = attribute.getValue();
            buffer.bind();
            shader.setAttribute(attribute.getKey(), buffer);
            buffer.unbind();
        }

        for (Map.Entry<String, Uniform> entry : uniforms.entrySet()) {            
            entry.getValue().set(shader, entry.getKey());
        }

        if (indexBuffer == null) {
            attributes.values().iterator().next().draw();
        } else {
            indexBuffer.draw();
        }

        // Faxina
        for (String attribute : attributes.keySet()) {
            shader.setAttribute(attribute, null);
        }
        shader.unbind();
        glBindVertexArray(0);
        return this;
    }

}
