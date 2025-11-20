package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;

/**
 * Base class for any renderable object in the engine.
 * <p>
 * A {@code RenderableObject} represents an entity with:
 * <ul>
 *     <li>A {@link Mesh} defining its geometry</li>
 *     <li>A {@link Material} defining its color/shader</li>
 * </ul>
 * </p>
 *
 * @see Mesh
 * @see Material
 * @see Renderer
 */
public abstract class Renderable {

    public Mesh mesh;
    public Material material;

    /**
     * Creates a renderable object with default transform values.
     *
     * @param mesh     The mesh defining the geometry.
     * @param material The material used for rendering.
     */
    public Renderable(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }


    //    TODO: is this update and destroy necessary ?
    public abstract void update(double deltaTime);

    public abstract void destroy();

    public void setMesh(Mesh mesh) {
        if (this.mesh != null) this.mesh.destroy();
        this.mesh = mesh;
    }
}
