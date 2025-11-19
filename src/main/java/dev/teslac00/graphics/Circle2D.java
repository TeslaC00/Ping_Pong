package dev.teslac00.graphics;

import static dev.teslac00.core.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.core.Constants.VIEWPORT_WIDTH;

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
public class Circle2D extends RenderableObject {

    /**
     * Constructs a new {@code Circle2D} renderable object.
     *
     * @param mesh     The mesh representing the circle geometry.
     * @param material The material (shader and color) used to render this circle.
     * @param x        The initial X position in world space.
     * @param y        The initial Y position in world space.
     * @param radius   The circle radius in world units.
     */
    public Circle2D(Mesh mesh, Material material, float x, float y, float radius) {
        super(mesh, material, x, y, radius, radius);
        float speed = 200.0f;
        this.velocity.set(1, 1).mul(speed);
    }

    @Override
    public void update(double deltaTime) {
        float dx = (float) (velocity.x * deltaTime);
        float dy = (float) (velocity.y * deltaTime);

        translate(dx, dy);
        handleViewportCollision();
    }

    private void handleViewportCollision() {
        float limitX = (VIEWPORT_WIDTH / 2.0f) - scale.x;
        float limitY = (VIEWPORT_HEIGHT / 2.0f) - scale.y;

        if (position.y > limitY) {   // check top screen collision
            position.y = limitY;
            velocity.y *= -1;
        } else if (position.y < -limitY) {    // check bottom screen collision
            position.y = -limitY;
            velocity.y *= -1;
        }

        if (position.x > limitX) {    // check right screen collision
            position.x = limitX;
            velocity.x *= -1;
        } else if (position.x < -limitX) {  // check left screen collision
            position.x = -limitX;
            velocity.x *= -1;
        }
    }

    @Override
    public void destroy() {

    }
}
