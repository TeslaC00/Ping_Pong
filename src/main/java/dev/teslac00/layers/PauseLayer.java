package dev.teslac00.layers;

import dev.teslac00.core.*;
import dev.teslac00.entities.Text2D;
import dev.teslac00.graphics.*;
import dev.teslac00.input.Event;
import org.joml.Vector4f;

import static dev.teslac00.util.Constants.*;
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

    private final Rectangle2D background;

    private final Font font;
    private final Text2D pauseText;

    /**
     * Constructs a new {@code PauseLayer} instance.
     *
     * @param engine The engine providing access to the renderer and layer stack.
     */
    public PauseLayer(Engine engine) {
        super(engine);

        Material backgroundMaterial = new Material(
                AssetManager.getShader(StaticShader.class),
                new Vector4f(0, 0, 0, 0.5f)
        );
        background = new Rectangle2D(backgroundMaterial, 0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        font = new Font(FONT_CHELA_ONE_ATLAS, FONT_CHELA_ONE_JSON);
        pauseText = new Text2D("Pause", font, 0, 0, 2);
    }

    /**
     * @return The display name of this layer.
     */
    @Override
    public String name() {
        return "Pause Layer";
    }

    /**
     * Called when this layer is added to the layer stack.
     */
    @Override
    public void onAttach() {

    }

    /**
     * Called every frame; this layer does not perform updates.
     */
    @Override
    public void onUpdate(double deltaTime) {
        pauseText.update(deltaTime);
    }

    /**
     * Renders the semi-transparent background overlay.
     */
    @Override
    public void onRender() {
        engine.getRenderer().renderModel(background);
        engine.getRenderer().renderModel(pauseText.getRenderable());
    }

    /**
     * Called when this layer is removed from the stack.
     * <p>
     * Cleans up shader and mesh resources.
     * </p>
     */
    @Override
    public void onDetach() {
        pauseText.destroy();
        font.destroy();
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
    public boolean onEvent(Event event) {
        if (event.key() == GLFW_KEY_P && event.action() == GLFW_PRESS) {
            engine.popLayer();
            return true;
        }
        return false;
    }
}
