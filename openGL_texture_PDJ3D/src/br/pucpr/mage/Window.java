package br.pucpr.mage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
    // The window handle
    private long window;
    private Scene scene;
    private int width;
    private int height;
    private String title;
    private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            Keyboard.getInstance().set(key, action);
        }
    };

    public Window(Scene scene, String title, int width, int height) {
        this.scene = scene;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public Window(Scene scene, String title) {
        this(scene, title, 800, 600);
    }

    public Window(Scene scene) {
        this(scene, "Game");
    }

    private void init() {
        System.setProperty("java.awt.headless", "true");
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden
                                                  // after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be
                                                   // resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed,
        // repeated or released.
        glfwSetKeyCallback(window, keyCallback);

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        scene.init();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        long before = System.currentTimeMillis() - 1;
        while (glfwWindowShouldClose(window) == GLFW_FALSE) {
            float time = (System.currentTimeMillis() - before) / 1000f;
            before = System.currentTimeMillis() - 1;
            scene.update(time);
            scene.draw();

            Keyboard.getInstance().update();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        scene.deinit();
    }

    public void show() {
        try {
            init();
            loop();

            // Destroy window and window callbacks
            glfwDestroyWindow(window);
        } finally {
            // Terminate GLFW and free the GLFWErrorCallback
            glfwTerminate();
        }
    }
}