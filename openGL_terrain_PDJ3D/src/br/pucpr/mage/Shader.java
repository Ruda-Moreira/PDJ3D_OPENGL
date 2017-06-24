package br.pucpr.mage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class Shader {
    private int id;

    private Shader(int id) {
        this.id = id;
    }

    private static String readInputStream(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load shader", e);
        }
    }

    private static int loadShader(String name) {
        name = name.trim();

        int dotIndex = name.lastIndexOf(".");
        String extension = dotIndex == -1 ? "" : name.substring(dotIndex).toLowerCase();

        int type;
        if (extension.equals(".vert") || extension.equals(".vs"))
            type = GL_VERTEX_SHADER;
        else if (extension.endsWith(".frag") || extension.endsWith(".fs"))
            type = GL_FRAGMENT_SHADER;
        else if (extension.endsWith(".geom") || extension.endsWith(".gs"))
            type = GL_GEOMETRY_SHADER;
        else
            throw new IllegalArgumentException("Invalid shader name: " + name);

        String code = readInputStream(Shader.class.getResourceAsStream(name));
        return compileShader(type, code);
    }

    private static int compileShader(int type, String code) {
        int shader = glCreateShader(type);
        glShaderSource(shader, code);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String typeStr = type == GL_VERTEX_SHADER ? "vertex" : type == GL_FRAGMENT_SHADER ? "fragment" : "geometry";
            throw new RuntimeException("Unable to compile " + typeStr + " shader." + glGetShaderInfoLog(shader));
        }
        return shader;
    }

    private static int linkProgram(int... shaders) {
        int program = glCreateProgram();
        for (int shader : shaders) {
            glAttachShader(program, shader);
        }

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Unable to link shaders." + glGetProgramInfoLog(program));
        }

        for (int shader : shaders) {
            glDetachShader(program, shader);
        }

        return program;
    }

    public static Shader loadProgram(String... shaders) {
        if (shaders.length == 0) {
            throw new IllegalArgumentException("You must provide shader names!");
        }

        if (shaders.length == 1) {
            shaders = new String[] { shaders[0] + ".vert", shaders[0] + ".frag" };
        }

        int[] ids = new int[shaders.length];
        for (int i = 0; i < shaders.length; i++) {
            ids[i] = loadShader(shaders[i]);
        }
        return new Shader(linkProgram(ids));
    }

    public int getId() {
        return id;
    }

    public Shader bind() {
        glUseProgram(id);
        return this;
    }

    public Shader unbind() {
        glUseProgram(0);
        return this;
    }

    public Shader setAttribute(String name, ArrayBuffer buffer) {
        int attribute = glGetAttribLocation(id, name);
        if (attribute == -1) {
            throw new IllegalArgumentException("Attribute does not exists: " + name);
        }
        if (buffer == null) {
            glDisableVertexAttribArray(attribute);
        } else {
            glEnableVertexAttribArray(attribute);
            glVertexAttribPointer(attribute, buffer.getElementSize(), GL_FLOAT, false, 0, 0);
        }
        return this;
    }

    private int findUniform(String name) {
        int uniform = glGetUniformLocation(id, name);
        if (uniform == -1) {
            throw new IllegalArgumentException("Uniform does not exists: " + name);
        }
        return uniform;
    }

    public Shader setUniform(String name, Matrix3f matrix) {
        int uniform = findUniform(name);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        matrix.get(buffer);
        glUniformMatrix3fv(uniform, false, buffer);
        return this;
    }

    public Shader setUniform(String name, Matrix4f matrix) {
        int uniform = findUniform(name);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(uniform, false, buffer);
        return this;
    }

    public Shader setUniform(String name, Vector2f vector) {
        glUniform2f(findUniform(name), vector.x, vector.y);
        return this;
    }

    public Shader setUniform(String name, Vector3f vector) {
        glUniform3f(findUniform(name), vector.x, vector.y, vector.z);
        return this;
    }

    public Shader setUniform(String name, Vector4f vector) {
        glUniform4f(findUniform(name), vector.x, vector.y, vector.z, vector.w);
        return this;
    }

    public Shader setUniform(String name, int value) {
        glUniform1i(findUniform(name), value);
        return this;
    }

    public Shader setUniform(String name, float value) {
        glUniform1f(findUniform(name), value);
        return this;
    }

    public Shader setUniform(String name, boolean value) {
        return setUniform(name, value ? GL_TRUE : GL_FALSE);
    }
}
