package dev.teslac00;


import org.lwjgl.Version;

import static dev.teslac00.Colors.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

    private long window;
    private Renderer renderer;
    private DisplayManager displayManager;

    final static int WIDTH = 1280, HEIGHT = 720;
    final static int CIRCLE_MESH_DEFAULT_SEGMENTS = 32;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(WIDTH, HEIGHT, "Ping Pong");
        window = displayManager.init();

        InputManager inputManager = new InputManager(window);

        renderer = new Renderer();
        renderer.init(WIDTH, HEIGHT);

        loop();

        renderer.destroy();
        inputManager.destroy();
        displayManager.destroy();
    }

    private void loop() {

        StaticShader staticShader = new StaticShader();
        Material greenMaterial = new Material(staticShader.getProgramId(), COLOR_GREEN);
        Material blueMaterial = new Material(staticShader.getProgramId(), COLOR_BLUE);
        Material redMaterial = new Material(staticShader.getProgramId(), COLOR_RED);

        Mesh rectangleMesh = MeshFactory.createRectangle();
        Mesh circleMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);


        Rectangle2D greenRect = new Rectangle2D(rectangleMesh, greenMaterial,
                0, 100, 100, (float) HEIGHT / 5);
        Rectangle2D blueRect = new Rectangle2D(rectangleMesh, blueMaterial,
                WIDTH - 100, 200, 100, (float) HEIGHT / 5);

        Circle2D redCircle = new Circle2D(circleMesh, redMaterial,
                (WIDTH / 2.0f) - 100, (HEIGHT / 2.0f) - 100, 100);

        renderer.loadModel(greenRect);
        renderer.loadModel(blueRect);
        renderer.loadModel(redCircle);

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
        long lastFrameTime = System.nanoTime();
        double timeAccumulator = 0.0;
        int frames = 0;

        while (!glfwWindowShouldClose(window)) {
//            Delta time
            long currentFrameTime = System.nanoTime();
            double deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentFrameTime;

//            Physics
            greenRect.update(deltaTime);
            blueRect.update(deltaTime);

//            Rendering
            renderer.render();
            displayManager.update();

//            FPS Counter
            timeAccumulator += deltaTime;
            frames++;
            if (timeAccumulator >= 1.0) {
                System.out.printf("FPS: %d%n", frames);
                frames = 0;
                timeAccumulator -= 1.0f;
            }
        }

        staticShader.destroy();
        rectangleMesh.destroy();
        circleMesh.destroy();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}