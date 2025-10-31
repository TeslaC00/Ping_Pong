package dev.teslac00;

import org.joml.Math;

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

    public static Mesh createCircle(int segments) {
        float[] vertices = new float[(segments + 1) * 2];   // +1 for center
        float cx = 0.5f, cy = 0.5f, r = 0.5f;
        double radian = 2 * Math.PI / segments; // angle of each segment

//        Centre vertex coordinates
        vertices[0] = cx;
        vertices[1] = cy;

        for (int i = 0; i < segments; i++) {
            int index = (i + 1) * 2;
            vertices[index] = cx + r * (float) Math.cos(i * radian);
            vertices[index + 1] = cy + r * (float) Math.sin(i * radian);
        }

        int[] indices = new int[3 * segments];
        for (int i = 0; i < segments; i++) {
            indices[i * 3] = 0;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = (i == segments - 1) ? 1 : i + 2;
        }

        return Mesh.loadMesh(vertices, indices);
    }
}
