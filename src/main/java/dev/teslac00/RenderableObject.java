package dev.teslac00;

import org.joml.Matrix4f;

public abstract class RenderableObject {

    private final Mesh mesh;
    private final Material material;
    private final Matrix4f transform;

    public RenderableObject(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
        transform = new Matrix4f().identity();
    }

    public RenderableObject(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        this.mesh = mesh;
        this.material = material;
        transform = new Matrix4f()
                .translation(x, y, 0)   // translation center based not top-left
                .scale(scaleX, scaleY, 1);
    }

    public void translate(float x, float y) {
        transform.translate(x, y, 0);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }

    public Matrix4f getTransform() {
        return transform;
    }
}
