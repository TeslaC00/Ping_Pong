package dev.teslac00.layers;

import dev.teslac00.core.Engine;
import dev.teslac00.core.Renderer;
import dev.teslac00.graphics.*;
import dev.teslac00.input.Event;
import dev.teslac00.input.InputManager;
import dev.teslac00.physics.BoxCollider;
import dev.teslac00.physics.CircleCollider;
import dev.teslac00.physics.PhysicsEngine;

import static dev.teslac00.core.Colors.*;
import static dev.teslac00.core.Constants.*;
import static dev.teslac00.core.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.core.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents the main gameplay layer where core simulation and rendering occur.
 * <p>
 * The {@code GameLayer} manages gameplay entities (paddles and ball), updates their
 * state each frame, and handles simple input-driven layer transitions (pause or exit).
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Create and manage in-game entities (rectangles and circle)</li>
 *     <li>Register colliders with the {@link PhysicsEngine}</li>
 *     <li>Render all entities via the {@link Renderer}</li>
 *     <li>Handle gameplay-related input events</li>
 * </ul>
 *
 * <p>
 * This layer demonstrates integration of physics and rendering systems
 * in the engine, forming a minimal “Pong-style” game example.
 * </p>
 *
 * @see Engine
 * @see Renderer
 * @see PhysicsEngine
 * @see PauseLayer
 */
public class GameLayer extends Layer {

    //    Shader & Meshes
    private final StaticShader staticShader;
    private final Mesh rectangleMesh;
    private final Mesh circleMesh;

    //    Entities
    private final Rectangle2D greenRect;
    private final Rectangle2D blueRect;
    private final Circle2D redCircle;

    private final float rectWidth = 60.0f;
    private final float rectHeight = VIEWPORT_HEIGHT / 5.0f;
    private final float radius = 30f;

    /**
     * Constructs a new {@code GameLayer} and initializes all game entities.
     *
     * @param engine The engine instance providing access to systems like renderer and physics.
     */
    public GameLayer(Engine engine) {
        super(engine);
        staticShader = new StaticShader();
        Material greenMaterial = new Material(staticShader.getProgramId(), COLOR_GREEN);
        Material blueMaterial = new Material(staticShader.getProgramId(), COLOR_BLUE);
        Material redMaterial = new Material(staticShader.getProgramId(), COLOR_RED);

        rectangleMesh = MeshFactory.createRectangle();
        circleMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);

        greenRect = new Rectangle2D(rectangleMesh, greenMaterial,
                (-VIEWPORT_WIDTH + rectWidth) / 2, (VIEWPORT_HEIGHT - rectHeight) / 2, rectWidth, rectHeight);
        blueRect = new Rectangle2D(rectangleMesh, blueMaterial,
                (VIEWPORT_WIDTH - rectWidth) / 2, 0, rectWidth, rectHeight);

        redCircle = new Circle2D(circleMesh, redMaterial, 0, 0, radius);
    }

    /**
     * @return The display name of this layer.
     */
    @Override
    public String name() {
        return "Game Layer";
    }

    /**
     * Called when the layer becomes active.
     * <p>
     * Registers all entity colliders with the physics engine.
     * </p>
     */
    @Override
    public void onAttach() {
        engine.getPhysicsEngine().add(new BoxCollider(greenRect, rectWidth, rectHeight));
        engine.getPhysicsEngine().add(new BoxCollider(blueRect, rectWidth, rectHeight));
        engine.getPhysicsEngine().add(new CircleCollider(redCircle, radius));
    }

    /**
     * Called every frame to update entity state.
     *
     * @param deltaTime Time elapsed since the last frame, in seconds.
     */
    @Override
    public void onUpdate(double deltaTime) {
        greenRect.update(deltaTime);
        blueRect.update(deltaTime);
        redCircle.update(deltaTime);
    }

    /**
     * Called each frame to render all entities.
     */
    @Override
    public void onRender() {
        engine.getRenderer().renderModel(greenRect);
        engine.getRenderer().renderModel(blueRect);
        engine.getRenderer().renderModel(redCircle);
    }

    /**
     * Called when the layer is removed from the stack.
     * <p>
     * Clears physics data and frees GPU resources.
     * </p>
     */
    @Override
    public void onDetach() {
        engine.getPhysicsEngine().clearColliders();
        rectangleMesh.destroy();
        circleMesh.destroy();
        staticShader.destroy();
    }

    /**
     * Handles keyboard input events for this layer.
     * <ul>
     *     <li><b>Tab</b> → Pop (exit) the current layer</li>
     *     <li><b>P</b> → Push the {@link PauseLayer}</li>
     * </ul>
     *
     * @param event The input event received from {@link InputManager}.
     * @return {@code true} if the event was handled; otherwise {@code false}.
     */
    @Override
    public boolean onEvent(Event event) {
        if (event.key() == GLFW_KEY_TAB && event.action() == GLFW_PRESS) {
            engine.popLayer();
            return true;
        }

        if (event.key() == GLFW_KEY_P && event.action() == GLFW_PRESS) {
            engine.pushLayer(new PauseLayer(engine));
            return true;
        }
        return false;
    }
}
