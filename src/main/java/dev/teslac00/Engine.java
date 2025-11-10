package dev.teslac00;

import org.lwjgl.Version;

import java.util.ArrayList;

import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Engine {

    //    Core entities
    private static long window;
    private DisplayManager displayManager;
    private InputManager inputManager;
    private Renderer renderer;
    private PhysicsEngine physicsEngine;
    private final Timer timer = new Timer();

    //    Layers
    private final ArrayList<Layer> layerStack = new ArrayList<>();

    //    Frames
    private double timeAccumulator = 0.0;
    private int frames = 0;

    public void init() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, "Ping Pong");
        window = displayManager.init();

        inputManager = new InputManager(window);

        renderer = new Renderer();
        renderer.init();

        physicsEngine = new PhysicsEngine();

        timer.init();

        GameLayer gameLayer = new GameLayer(this);
        layerStack.add(gameLayer);
        gameLayer.onAttach();
    }

    public void run() {
//            Delta time
        double deltaTime = timer.getDeltaTime();

//            Physics
        physicsEngine.update(deltaTime);
        layerStack.getLast().onUpdate(deltaTime);

//            Rendering
        renderer.prepare();
        for (Layer layer : layerStack) {
            layer.onRender();
        }
        renderer.render();

        displayManager.update();

//            FPS Counter
        updateFPS(deltaTime);
    }

    /**
     * Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
     *
     * @return True if window close is requested else False
     */
    public static boolean shouldRun() {
        return !glfwWindowShouldClose(window);
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

    public void destroy() {
        for (Layer layer : layerStack)
            layer.onDetach();
        layerStack.clear();

        renderer.destroy();
        inputManager.destroy();
        displayManager.destroy();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }
}
