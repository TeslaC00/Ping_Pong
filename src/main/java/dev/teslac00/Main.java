package dev.teslac00;


import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private long window;
    private Renderer renderer;
    private DisplayManager displayManager;

    final static Vector4f COLOR_RED = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
    final static Vector4f COLOR_GREEN = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
    final static Vector4f COLOR_BLUE = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);

    final static int WIDTH = 1280, HEIGHT = 720;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(WIDTH, HEIGHT, "Ping Pong");
        renderer = new Renderer();

        window = displayManager.init();
        renderer.init();
        loop();
        renderer.destroy();
        displayManager.destroy();
    }

    private void loop() {

        float[] vertices = {
                200f, 100f,
                500f, 100f,
                500f, 200f,
                200f, 200f
        };

        float[] verticesRight = {
                600f, 100f,
                700f, 100f,
                700f, 300f,
                600f, 300f
        };

        int[] indices = {
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        Matrix4f proj = new Matrix4f().ortho(0, WIDTH, HEIGHT, 0, -1, 1);
        FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
        proj.get(projBuffer).rewind();

        renderer.loadModel(vertices, indices, COLOR_GREEN);
        renderer.loadModel(verticesRight, indices, COLOR_BLUE);

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            renderer.render(projBuffer);
            displayManager.update();
        }

    }

    public static void main(String[] args) {
        new Main().run();
    }
}