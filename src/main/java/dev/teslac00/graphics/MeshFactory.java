package dev.teslac00.graphics;

import dev.teslac00.core.AssetManager;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;

public class MeshFactory {

    public static Mesh createQuad() {

        float[] vertices = new float[]{
                0.5f, 0.5f, 1f, 1f, // position + uv
                -0.5f, 0.5f, 0f, 1f,
                -0.5f, -0.5f, 0f, 0f,
                0.5f, -0.5f, 1f, 0f
        };

        int[] indices = new int[]{
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        return Mesh.loadMesh(vertices, indices, AssetManager.getLayoutPosUv());
    }

    public static Mesh createCircle(int segments) {
        int vertexCount = segments + 1; // 1 vertex for each segment + 1 for center
        int vertexAttribCount = 4;  // Count of vertex attrib values (2 position, 2 uv)
        float[] vertices = new float[vertexCount * vertexAttribCount];
        double angleStep = 2 * Math.PI / segments; // angle of each segment

//        Centre vertex coordinates
        vertices[0] = 0;    // center x
        vertices[1] = 0;    // center y
        vertices[2] = 0.5f;    // center u
        vertices[3] = 0.5f;    // center v


        for (int i = 0; i < segments; i++) {
            int index = (i + 1) * vertexAttribCount;
            double angle = i * angleStep;
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);

            vertices[index] = x;
            vertices[index + 1] = y;
            vertices[index + 2] = (x * 0.5f) + 0.5f;    // u
            vertices[index + 3] = (y * 0.5f) + 0.5f;    // v

        }

        int[] indices = new int[3 * segments];  // 1 triangle per segment with 3 indices
        for (int i = 0; i < segments; i++) {
            indices[i * 3] = 0;
            indices[i * 3 + 1] = i + 1;
            indices[i * 3 + 2] = (i + 1) % segments + 1;
        }

        return Mesh.loadMesh(vertices, indices, AssetManager.getLayoutPosUv());
    }

    public static Mesh createTextMesh(String text, Font font, float size) {
        List<Float> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float xCursor = 0;
        float yCursor = 0;
        float scale = size / font.getFontSize();
        int indexOffset = 0;

        for (char c : text.toCharArray()) {
            FontCharacter character = font.getFontCharacter(c);
            if (character == null) continue;

//            Vertex positions (local space, centered at origin)
            float x0 = xCursor + character.xOffset() * scale;
            float y0 = yCursor + character.yOffset() * scale;
            float x1 = x0 + character.width() * scale;
            float y1 = y0 + character.height() * scale;

//            Triangle 1
            vertices.addAll(List.of(
//                    Vertex 1
                    x0, y0,
                    character.u0(), character.v0(),
//                    Vertex 2
                    x1, y0,
                    character.u1(), character.v0(),
//                    Vertex 3
                    x1, y1,
                    character.u1(), character.v1(),
//                    Vertex 4
                    x0, y1,
                    character.u0(), character.v1()
            ));

            indices.addAll(List.of(
                    indexOffset, indexOffset + 1, indexOffset + 2,
                    indexOffset + 2, indexOffset + 3, indexOffset
            ));

            xCursor += character.xAdvance() * scale;
            indexOffset += 4;
        }

//        TODO: Mesh only works for single vertex attribute, make it dynamic
        return Mesh.loadMesh(
                toFloatArray(vertices),
                indices.stream().mapToInt(i -> i).toArray()
        );
    }

    private static float[] toFloatArray(List<Float> vertices) {
        float[] array = new float[vertices.size()];
        for (int index = 0; index < array.length; index++) {
            array[index] = vertices.get(index);
        }
        return array;
    }
}
