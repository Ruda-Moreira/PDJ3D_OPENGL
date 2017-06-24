package br.pucpr.mage;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

/**
 * Represents an Element Array of Floats.
 */
public class ArrayBuffer {
    private int id;
    private int elementSize;
    private int elementCount;

    public ArrayBuffer(int elementSize, FloatBuffer data) {
        if (elementSize < 1) {
            throw new IllegalArgumentException("Element size < 1!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
    
        this.id = glGenBuffers();
        this.elementSize = elementSize;
        this.elementCount = data.remaining() / elementSize;
    
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getId() {
        return id;
    }
    
    public int getElementSize() {
        return elementSize;
    }
    
    public int getElementCount() {
        return elementCount;
    }
    
    public int getSize() {
        return elementSize * elementCount;
    }

    public ArrayBuffer bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
        return this;
    }

    public ArrayBuffer unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, getElementCount());
    }
}
