package dev.teslac00;

public class MeshFactory {

    public static Mesh createRectangle() {

        float[] vertices = new float[]{
                0f, 0f,
                1f, 0f,
                1, 1f,
                0f, 1f
        };

        int[] indices = new int[]{
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        return Mesh.loadMesh(vertices, indices);
    }
}
