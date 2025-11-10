package dev.teslac00;


import org.lwjgl.Version;

import static dev.teslac00.Colors.*;
import static dev.teslac00.Constants.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public final class Main {

    //    Core entities
    private long window;
    private Renderer renderer;
    private PhysicsEngine physicsEngine;
    private DisplayManager displayManager;
    private Timer timer;

    //    Frames
    private double timeAccumulator = 0.0;
    private int frames = 0;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, "Ping Pong");
        window = displayManager.init();

        InputManager inputManager = new InputManager(window);

        physicsEngine = new PhysicsEngine();
        renderer = new Renderer();
        renderer.init();
        timer.init();

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

        float rectWidth = 60.0f, rectHeight = VIEWPORT_HEIGHT / 5.0f, radius = 30f;
        Rectangle2D greenRect = new Rectangle2D(rectangleMesh, greenMaterial,
                (-VIEWPORT_WIDTH + rectWidth) / 2, (VIEWPORT_HEIGHT - rectHeight) / 2, rectWidth, rectHeight);
        Rectangle2D blueRect = new Rectangle2D(rectangleMesh, blueMaterial,
                (VIEWPORT_WIDTH - rectWidth) / 2, 0, rectWidth, rectHeight);

        Circle2D redCircle = new Circle2D(circleMesh, redMaterial, 0, 0, radius);

        renderer.loadModel(greenRect);
        renderer.loadModel(blueRect);
        renderer.loadModel(redCircle);

        physicsEngine.add(new BoxCollider(greenRect, rectWidth, rectHeight));
        physicsEngine.add(new BoxCollider(blueRect, rectWidth, rectHeight));
        physicsEngine.add(new CircleCollider(redCircle, radius));

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.

        while (!glfwWindowShouldClose(window)) {
//            Delta time
            double deltaTime = timer.getDeltaTime();

//            Physics
            greenRect.update(deltaTime);
            blueRect.update(deltaTime);
            redCircle.update(deltaTime);
            physicsEngine.update(deltaTime);

//            Rendering
            renderer.render();
            displayManager.update();

//            FPS Counter
            updateFPS(deltaTime);
        }

        staticShader.destroy();
        rectangleMesh.destroy();
        circleMesh.destroy();
    }

    private void updateFPS(double deltaTime) {
        timeAccumulator += deltaTime;
        frames++;
        if (timeAccumulator >= 1.0) {
            System.out.printf("FPS: %d%n", frames);
            frames = 0;
            timeAccumulator -= 1.0f;
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}