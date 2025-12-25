package dev.teslac00.layers;

import dev.teslac00.core.Engine;
import dev.teslac00.input.Event;

/**
 * Base abstract class for all engine layers.
 * <p>
 * A {@code Layer} represents an isolated section of the application (e.g. menu, gameplay, pause)
 * that can be stacked, updated, and rendered independently. Layers are managed by the
 * {@link Engine}'s layer stack.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Provide lifecycle hooks for setup, update, rendering, and cleanup</li>
 *     <li>Handle input events specific to the layer</li>
 *     <li>Optionally overlay on top of other layers (e.g. pause menu)</li>
 * </ul>
 *
 * <p>
 * Each derived layer should override all lifecycle methods to implement its own behavior.
 * Layers are typically pushed and popped from the engine stack dynamically using
 * {@link Engine#pushLayer(Layer)} and {@link Engine#popLayer()}.
 * </p>
 *
 * @see Engine
 * @see MenuLayer
 * @see GameLayer
 * @see PauseLayer
 */
public abstract class Layer {

    /**
     * Stops rendering if the layer is Suspended
     */
    protected boolean isSuspended;

    /**
     * Reference to the engine that owns this layer.
     */
    protected Engine engine;

    /**
     * Constructs a new {@code Layer} associated with the given engine.
     *
     * @param engine The engine instance this layer interacts with.
     */
    public Layer(Engine engine) {
        this.engine = engine;
    }

    /**
     * Returns the display name of this layer (for logging or debugging).
     *
     * @return The layer’s name.
     */
    public abstract String name();

    /**
     * Called once when the layer is added to the engine’s layer stack.
     * <p>
     * Use this for setup logic such as registering physics objects, loading resources,
     * or initializing UI elements.
     * </p>
     */
    public abstract void onAttach();

    /**
     * Called once per frame to update the layer’s logic.
     *
     * @param deltaTime Time elapsed since the last frame, in seconds.
     */
    public abstract void onUpdate(double deltaTime);

    /**
     * Called once per frame to render the layer’s visuals.
     * <p>
     * Typically invoked in draw order for all active layers.
     * </p>
     */
    public abstract void onRender();

    /**
     * Called when the layer is removed from the stack.
     * <p>
     * Use this for cleanup logic such as freeing GPU buffers, destroying shaders,
     * or unregistering colliders.
     * </p>
     */
    public abstract void onDetach();

    /**
     * Handles input or system events directed to this layer.
     *
     * @param event The event to process.
     * @return {@code true} if the event was handled (and should not propagate further).
     */
    public abstract boolean onEvent(Event event);

    public void transitionTo(Layer layer) {
        engine.queueTransition(layer);
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void unSuspend() {
        isSuspended = false;
    }
}
