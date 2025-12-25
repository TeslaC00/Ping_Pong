package dev.teslac00.core;

import dev.teslac00.input.Event;
import dev.teslac00.input.InputManager;
import dev.teslac00.layers.Layer;
import dev.teslac00.layers.MenuLayer;
import dev.teslac00.physics.PhysicsEngine;
import org.lwjgl.Version;

import java.util.ArrayList;

import static dev.teslac00.util.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * The main engine class responsible for initializing and running the core systems
 * of the application: window management, input, rendering, physics, and layered game states.
 * <p>
 * It manages a stack of {@link Layer}s, delegates updates and rendering to them,
 * and handles the main game loop execution.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Initialize and destroy all core subsystems</li>
 *     <li>Maintain a stack-based layer system (e.g., Menu, Game, Pause)</li>
 *     <li>Drive per-frame updates for physics, input, and rendering</li>
 *     <li>Manage timing, frame delta, and FPS tracking</li>
 * </ul>
 *
 * <p>
 * The engine loop is externally controlled from {@code Main.main()}, which repeatedly
 * calls {@link #run()} while {@link #shouldRun()} returns true.
 * </p>
 */
public class Engine {

    // ---------------------------------------------------------------------
    // Core Systems
    // ---------------------------------------------------------------------

    private long window;
    private DisplayManager displayManager;
    private InputManager inputManager;
    private AssetManager assetManager;
    private Renderer renderer;
    private PhysicsEngine physicsEngine;
    private final Timer timer = new Timer();

    /**
     * Just pauses physics but allows rendering
     */
    private boolean isPaused;
    private boolean shouldClose;
    private Layer transitionLayer;

    // ---------------------------------------------------------------------
    // Layers and Frame Data
    // ---------------------------------------------------------------------

    private final ArrayList<Layer> layerStack = new ArrayList<>();
    private double timeAccumulator = 0.0;
    private int frames = 0;

    // ---------------------------------------------------------------------
    // Lifecycle Methods
    // ---------------------------------------------------------------------

    /**
     * Initializes the engine and all core systems.
     * <p>
     * Creates the display, initializes OpenGL, input, and physics, and
     * pushes the starting {@link MenuLayer} onto the stack.
     * </p>
     */
    public void init() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        displayManager = new DisplayManager(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, "Ping Pong");
        window = displayManager.init();

        inputManager = new InputManager(window);
        assetManager = new AssetManager();
        assetManager.init();

        renderer = new Renderer();
        renderer.init();

        physicsEngine = new PhysicsEngine();

        timer.init();

//        Push starting menu layer on stack
        pushLayer(new MenuLayer(this));
    }

    /**
     * Executes a single frame of the game loop.
     * <p>
     * Handles input events, updates physics, processes active layer logic,
     * performs rendering, and updates FPS counters.
     * </p>
     */
    public void run() {
        double deltaTime = timer.getDeltaTime();

//        Process input events
        for (Event event : InputManager.getEventQueue()) {
            boolean consumed = layerStack.getLast().onEvent(event);
            if (consumed) System.out.printf("Event: %s consumed %n", event);
        }
        InputManager.getEventQueue().clear();

//        Physics step
        if (!isPaused)
            physicsEngine.update(deltaTime);
//        Update top-most active layer
        layerStack.getLast().onUpdate(deltaTime);

//            Rendering
        for (Layer layer : layerStack)
            if (!layer.isSuspended()) layer.onRender();

        renderer.prepare();
        renderer.render();

        displayManager.update();

//            FPS Counter
        updateFPS(deltaTime);

        if (transitionLayer != null) {
            layerStack.clear();
            physicsEngine.destroy();
            pushLayer(transitionLayer);
            transitionLayer = null;
        }
    }

    /**
     * Returns whether the engine should continue running.
     *
     * @return {@code true} if the window is open and the layer stack is not empty.
     */
    public boolean shouldRun() {
        return !glfwWindowShouldClose(window) && !layerStack.isEmpty() && !shouldClose;
    }

    /**
     * Updates the FPS counter and logs it once per second.
     *
     * @param deltaTime Time elapsed since last frame, in seconds.
     */
    private void updateFPS(double deltaTime) {
        timeAccumulator += deltaTime;
        frames++;
        if (timeAccumulator >= 1.0) {
            System.out.printf("FPS: %d%n", frames);
            frames = 0;
            timeAccumulator -= 1.0f;
        }
    }

    /**
     * Cleans up and destroys all active systems and resources.
     * <p>
     * Called after the main loop exits.
     * </p>
     */
    public void destroy() {
        for (Layer layer : layerStack)
            layer.onDetach();
        layerStack.clear();

        renderer.destroy();
        assetManager.destroy();
        inputManager.destroy();
        displayManager.destroy();
    }

    // ---------------------------------------------------------------------
    // Layer Stack Management
    // ---------------------------------------------------------------------

    /**
     * Pushes a new layer onto the stack and activates it.
     *
     * @param layer The layer to push.
     */
    public void pushLayer(Layer layer) {
        layerStack.add(layer);
        layer.onAttach();
        System.out.printf("Pushed %s", layer.name());
    }

    /**
     * Pops the top-most layer off the stack and detaches it.
     * <p>
     * If no layers remain, the engine loop will terminate on the next check.
     * </p>
     */
    public void popLayer() {
        if (layerStack.isEmpty()) return;
        layerStack.getLast().onDetach();
        System.out.printf("Popped %s", layerStack.getLast().name());
        layerStack.removeLast();
    }

    public void unSuspendLayer() {
        if (layerStack.isEmpty()) return;
        layerStack.getLast().unSuspend();
    }

    public void queueTransition(Layer layer) {
        transitionLayer = layer;
    }

    // ---------------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------------

    /**
     * @return The engine’s renderer instance.
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * @return The active physics engine instance.
     */
    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public void pause() {
        isPaused = true;
    }

    public void unPause() {
        isPaused = false;
    }

    public void close() {
        shouldClose = true;
    }
}
