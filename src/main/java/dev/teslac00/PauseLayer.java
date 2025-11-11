package dev.teslac00;

import org.joml.Vector4f;

import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents the pause menu overlay layer.
 * <p>
 * The {@code PauseLayer} draws a semi-transparent overlay that darkens the screen
 * while allowing the underlying gameplay layer to remain visible. It does not perform
 * any updates to game entities — only rendering and input handling.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Render a transparent black background over the current frame</li>
 *     <li>Handle input to unpause and return to the previous layer</li>
 * </ul>
 *
 * <p>
 * Pressing <b>P</b> removes this layer from the stack, resuming gameplay.
 * </p>
 *
 * @see GameLayer
 * @see Engine
 * @see Renderer
 */
public class PauseLayer extends Layer {

    private final StaticShader staticShader;
    private final Mesh rectangleMesh;
    private final Rectangle2D background;

    /**
     * Constructs a new {@code PauseLayer} instance.
     *
     * @param engine The engine providing access to the renderer and layer stack.
     */
    protected PauseLayer(Engine engine) {
        super(engine);

        staticShader = new StaticShader();
        rectangleMesh = MeshFactory.createRectangle();
        Material backgroundMaterial = new Material(staticShader.getProgramId(), new Vector4f(0, 0, 0, 0.5f));

        background = new Rectangle2D(rectangleMesh, backgroundMaterial, 0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    /**
     * @return The display name of this layer.
     */
    @Override
    String name() {
        return "Pause Layer";
    }

    /**
     * Called when this layer is added to the layer stack.
     */
    @Override
    void onAttach() {

    }

    /**
     * Called every frame; this layer does not perform updates.
     */
    @Override
    void onUpdate(double deltaTime) {

    }

    /**
     * Renders the semi-transparent background overlay.
     */
    @Override
    void onRender() {
        engine.getRenderer().renderModel(background);
    }

    /**
     * Called when this layer is removed from the stack.
     * <p>
     * Cleans up shader and mesh resources.
     * </p>
     */
    @Override
    void onDetach() {
        rectangleMesh.destroy();
        staticShader.destroy();
    }

    /**
     * Handles key events for this layer.
     * <p>
     * Pressing <b>P</b> removes the pause layer and resumes the game.
     * </p>
     *
     * @param event The keyboard event.
     * @return {@code true} if the event was consumed, otherwise {@code false}.
     */
    @Override
    boolean onEvent(Event event) {
        if (event.key() == GLFW_KEY_P && event.action() == GLFW_PRESS) {
            engine.popLayer();
            return true;
        }
        return false;
    }
}
