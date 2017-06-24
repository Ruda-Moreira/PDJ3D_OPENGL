package br.pucpr.raytracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Vector3f;

public class Canvas {
    private static final String FILE = "raytrace.png";
    private BufferedImage img;

    public Canvas(int w, int h) {
        img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }

    public float getWidth() {
        return (float) img.getWidth();
    }

    public float getHeight() {
        return (float) img.getHeight();
    }
    
    private void saturate(Vector3f color) {
        color.x = color.x > 1.0f ? 1.0f : (color.x < 0.0f ? 0.0f : color.x);
        color.y = color.y > 1.0f ? 1.0f : (color.y < 0.0f ? 0.0f : color.y);
        color.z = color.z > 1.0f ? 1.0f : (color.z < 0.0f ? 0.0f : color.z);
    }

    private int toRGB(Vector3f color) {
        saturate(color);
        int r = (int) (color.x * 255);
        int g = (int) (color.y * 255);
        int b = (int) (color.z * 255);
        return new Color(r, g, b).getRGB();
    }

    public void set(int x, int y, Vector3f color) {
        img.setRGB(x, y, toRGB(color));
    }

    public void save() {
        try {
            ImageIO.write(img, "png", new File(FILE));
        } catch (IOException e) {
            throw new RuntimeException("Problem while saving image", e);
        }
    }
}
