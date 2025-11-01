package dev.teslac00;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class RenderableObject {

    protected final Mesh mesh;
    protected final Material material;
    protected Vector3f position;
    protected Vector3f scale;
    protected float rotation;
    protected final Matrix4f transform;

    protected final Vector2f velocity = new Vector2f(0, 0);

    public RenderableObject(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
        transform = new Matrix4f().identity();
    }

    //  translation center based not top-left
    public RenderableObject(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        this.mesh = mesh;
        this.material = material;
        this.position = new Vector3f(x, y, 0);
        this.scale = new Vector3f(scaleX, scaleY, 1);
        transform = new Matrix4f();
        updateTransform();
    }

    protected void updateTransform() {
        transform.identity().translate(position).rotateZ(rotation).scale(scale);
    }

    public void translate(float dx, float dy) {
        this.position.add(dx, dy, 0);
        updateTransform();
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y, 0);
        updateTransform();
    }

    public void rotate(float radian) {
        this.rotation = radian;
        updateTransform();
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
