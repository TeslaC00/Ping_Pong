package dev.teslac00.graphics;

import dev.teslac00.core.AssetManager;

/**
 * Represents a 2D circle renderable that can move and bounce within the viewport bounds.
 * <p>
 * The circle is centered on its {@code position} and uses its {@code scale.x}
 * (or radius) to determine collision limits. It updates its position based
 * on velocity each frame and reverses direction when colliding with viewport edges.
 * </p>
 *
 * <p>This class is primarily used for dynamic objects (like balls in Pong)
 * that require simple boundary-based motion without physics forces.</p>
 */
public class Circle2D extends Renderable {

//    TODO: use shader to create a circle mathematically with quad instead of circle mesh

    /**
     * Constructs a new {@code Circle2D} renderable object.
     *
     * @param material The material (shader and color) used to render this circle.
     */
    public Circle2D(Material material) {
        super(AssetManager.getCircularMesh(), material);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void destroy() {

    }
}
