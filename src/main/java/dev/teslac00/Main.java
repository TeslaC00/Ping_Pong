package dev.teslac00;


import org.lwjgl.Version;

import static dev.teslac00.Colors.*;
import static dev.teslac00.Constants.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public final class Main {

    private long window;
    private Renderer renderer;
    private DisplayManager displayManager;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, "Ping Pong");
        window = displayManager.init();

        InputManager inputManager = new InputManager(window);

        renderer = new Renderer();
        renderer.init();

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

        float rectWidth = 60.0f, rectHeight = VIEWPORT_HEIGHT / 5.0f;
        Rectangle2D greenRect = new Rectangle2D(rectangleMesh, greenMaterial,
                (-VIEWPORT_WIDTH + rectWidth) / 2, (VIEWPORT_HEIGHT - rectHeight) / 2, rectWidth, rectHeight);
        Rectangle2D blueRect = new Rectangle2D(rectangleMesh, blueMaterial,
                (VIEWPORT_WIDTH - rectWidth) / 2, 0, rectWidth, rectHeight);

        Circle2D redCircle = new Circle2D(circleMesh, redMaterial, 0, 0, 30);

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
            redCircle.update(deltaTime);

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