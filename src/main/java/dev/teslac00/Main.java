package dev.teslac00;


import org.joml.Vector4f;
import org.lwjgl.Version;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private long window;
    private Renderer renderer;
    private DisplayManager displayManager;

    final static Vector4f COLOR_RED = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
    final static Vector4f COLOR_GREEN = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
    final static Vector4f COLOR_BLUE = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(1280, 720, "Ping Pong");
        renderer = new Renderer();

        window = displayManager.init();
        renderer.init();
        loop();
        renderer.destroy();
        displayManager.destroy();
    }

    private void loop() {

        float[] vertices = {
                -0.2f, 0.8f,
                -0.4f, 0.8f,
                -0.4f, 0.2f,
                -0.2f, 0.2f
        };

        float[] verticesRight = {
                0.8f, 0.8f,
                0.2f, 0.8f,
                0.2f, 0.6f,
                0.8f, 0.6f
        };

        int[] indices = {
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        renderer.loadModel(vertices, indices, COLOR_GREEN);
        renderer.loadModel(verticesRight, indices, COLOR_BLUE);

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            renderer.render();
            displayManager.update();
        }

    }

    public static void main(String[] args) {
        new Main().run();
    }
}