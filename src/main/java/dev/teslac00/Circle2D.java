package dev.teslac00;

public class Circle2D extends RenderableObject {

    public Circle2D(Mesh mesh, Material material, float x, float y, float radius) {
        super(mesh, material, x, y, 2 * radius, 2 * radius);
    }
}
