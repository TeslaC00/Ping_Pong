package dev.teslac00.graphics;

import dev.teslac00.core.AssetManager;
import org.joml.Math;

public class MeshFactory {

    public static Mesh createRectangle() {

        float[] vertices = new float[]{
                0.5f, 0.5f,
                -0.5f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f
        };

        int[] indices = new int[]{
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        return Mesh.loadMesh(vertices, indices, AssetManager.getPositionLayout());
    }

    public static Mesh createCircle(int segments) {
        float[] vertices = new float[(segments + 1) * 2];   // +1 for center
        double angleStep = 2 * Math.PI / segments; // angle of each segment

//        Centre vertex coordinates
        vertices[0] = 0;
        vertices[1] = 0;

        for (int i = 0; i < segments; i++) {
            int index = (i + 1) * 2;
            double angle = i * angleStep;
            vertices[index] = (float) Math.cos(angle);
            vertices[index + 1] = (float) Math.sin(angle);
        }

        int[] indices = new int[3 * segments];
        for (int i = 0; i < segments; i++) {
            indices[i * 3] = 0;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = (i + 1) % segments + 1;
        }

        return Mesh.loadMesh(vertices, indices, AssetManager.getPositionLayout());
    }
}
