package dev.teslac00;


import org.lwjgl.Version;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private long window;
    private Renderer renderer;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        DisplayManager displayManager = new DisplayManager(1280, 720, "Ping Pong");
        renderer = new Renderer();

        window = displayManager.init();
        renderer.init();
        loop();
        renderer.destroy();
        displayManager.destroy();
    }

    private void loop() {

        float[] vertices = {
                0.5f, 0.5f,
                -0.5f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f
        };

        int[] indices = {
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        renderer.load(vertices, indices);

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
        while (!glfwWindowShouldClose(window)) {

            renderer.render();

            glfwSwapBuffers(window);    // swap the color buffers

//            Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}