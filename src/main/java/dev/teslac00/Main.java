package dev.teslac00;


import org.lwjgl.Version;

import static dev.teslac00.Colors.COLOR_BLUE;
import static dev.teslac00.Colors.COLOR_GREEN;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

    private long window;
    private Renderer renderer;
    private DisplayManager displayManager;

    final static int WIDTH = 1280, HEIGHT = 720;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(WIDTH, HEIGHT, "Ping Pong");
        renderer = new Renderer();

        window = displayManager.init();
        renderer.init(WIDTH, HEIGHT);
        loop();
        renderer.destroy();
        displayManager.destroy();
    }

    private void loop() {

        StaticShader staticShader = new StaticShader();
        Material greenMaterial = new Material(staticShader.getProgramId(), COLOR_GREEN);
        Material blueMaterial = new Material(staticShader.getProgramId(), COLOR_BLUE);

        Mesh rectangleMesh = MeshFactory.createRectangle();

        Rectangle2D greenRect = new Rectangle2D(rectangleMesh, greenMaterial, 100, 100, 100, (float) HEIGHT / 5);
        Rectangle2D blueRect = new Rectangle2D(rectangleMesh, blueMaterial, WIDTH - 100, 200, 100, (float) HEIGHT / 5);

        renderer.loadModel(greenRect);
        renderer.loadModel(blueRect);

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            renderer.render();
            displayManager.update();
        }

        staticShader.destroy();
        rectangleMesh.destroy();

    }

    public static void main(String[] args) {
        new Main().run();
    }
}