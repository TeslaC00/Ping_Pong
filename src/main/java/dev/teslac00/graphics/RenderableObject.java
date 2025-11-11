package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Base class for any renderable object in the engine.
 * <p>
 * A {@code RenderableObject} represents an entity with:
 * <ul>
 *     <li>A {@link Mesh} defining its geometry</li>
 *     <li>A {@link Material} defining its color/shader</li>
 *     <li>A {@link Matrix4f transform} describing its position, rotation, and scale</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class provides utility methods for basic transformations (translate, rotate, scale)
 * and stores a {@link Vector2f velocity} for motion-based objects (e.g. dynamic circles, boxes).
 * </p>
 *
 * <p><b>Coordinate system:</b> Center-based — the object's {@code position}
 * refers to its center, not the top-left corner.</p>
 *
 * @see Mesh
 * @see Material
 * @see Renderer
 */
public abstract class RenderableObject {

    protected final Mesh mesh;
    protected final Material material;

    protected Vector3f position;
    protected Vector3f scale;
    protected float rotation;

    protected final Matrix4f transform;

    protected final Vector2f velocity = new Vector2f(0, 0);

    /**
     * Creates a renderable object with default transform values.
     *
     * @param mesh     The mesh defining the geometry.
     * @param material The material used for rendering.
     */
    public RenderableObject(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
        transform = new Matrix4f().identity();
    }

    //  translation center based not top-left

    /**
     * Creates a renderable object with position and scale.
     * <p>
     * Translation is center-based (origin = center of object).
     * </p>
     *
     * @param mesh     The mesh defining the geometry.
     * @param material The material used for rendering.
     * @param x        The X position in world coordinates.
     * @param y        The Y position in world coordinates.
     * @param scaleX   The X-axis scale factor.
     * @param scaleY   The Y-axis scale factor.
     */
    public RenderableObject(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        this.mesh = mesh;
        this.material = material;
        this.position = new Vector3f(x, y, 0);
        this.scale = new Vector3f(scaleX, scaleY, 1);
        transform = new Matrix4f();
        updateTransform();
    }

    /**
     * Updates the internal transformation matrix using current position, rotation, and scale.
     * <p>
     * This should be called after any modification to position, rotation, or scale.
     * </p>
     */
    protected void updateTransform() {
        transform.identity().translate(position).rotateZ(rotation).scale(scale);
    }

    /**
     * Translates the object by the given delta along X and Y.
     *
     * @param dx The change in X position.
     * @param dy The change in Y position.
     */
    public void translate(float dx, float dy) {
        this.position.add(dx, dy, 0);
        updateTransform();
    }

    /**
     * Sets the object’s position directly in world space.
     *
     * @param x The new X coordinate.
     * @param y The new Y coordinate.
     */
    public void setPosition(float x, float y) {
        this.position.set(x, y, 0);
        updateTransform();
    }

    /**
     * Rotates the object around the Z-axis by the given angle (in radians).
     *
     * @param radian The rotation angle in radians.
     */
    public void rotate(float radian) {
        this.rotation = radian;
        updateTransform();
    }

    /**
     * @return The mesh defining this object’s geometry.
     */
    public Mesh getMesh() {
        return mesh;
    }

    /**
     * @return The material used for rendering this object.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @return The model transformation matrix for this object.
     */
    public Matrix4f getTransform() {
        return transform;
    }

    /**
     * @return The position used for rendering this object.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @return The object's current velocity.
     */
    public Vector2f getVelocity() {
        return velocity;
    }
}
