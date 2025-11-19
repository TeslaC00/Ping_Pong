package dev.teslac00.graphics;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Font;
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

    public static TextMesh createTextMesh(String text, Font font) {
        if (text == null || text.isBlank())
            return new TextMesh(
                    new Mesh(0, 0, 0, 0),
                    0, 0
            );

//        Generate Vertices with Offsets
        List<Float> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float xCursor = 0f;

        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;

        float maxAscent = 0f;
        float maxDescent = 0f;

        for (char c : text.toCharArray()) {
            Glyph glyph = font.getGlyph(c);
            if (glyph == null) continue;

            float x0 = xCursor + glyph.xOffset();
            float y0 = font.getFontBase() - glyph.yOffset(); // top relative to baseline
            float x1 = x0 + glyph.width();
            float y1 = y0 - glyph.height(); // bottom relative to baseline

            minX = Math.min(minX, x0);
            minY = Math.min(minY, y1);
            maxX = Math.max(maxX, x1);
            maxY = Math.max(maxY, y0);

            maxAscent = Math.max(maxAscent, glyph.yOffset());
            maxDescent = Math.max(maxDescent, glyph.height() - glyph.yOffset());

            xCursor += glyph.xAdvance();
        }

//        True Center
//        Compute alignment
        float centerX = (minX + maxX) * 0.5f;
//        baseline stays at y = 0
        float centerY = 0f;

//        Build final mesh aligned to center
        xCursor = 0f;
        int indexOffset = 0;

        for (char c : text.toCharArray()) {
            Glyph glyph = font.getGlyph(c);
            if (glyph == null) continue;

            float x0 = (xCursor + glyph.xOffset()) - centerX;
            float y0 = (font.getFontBase() - glyph.yOffset()) - centerY;
            float x1 = x0 + glyph.width();
            float y1 = y0 - glyph.height();

            float u0 = glyph.u0(), v0 = 1.0f - glyph.v0();
            float u1 = glyph.u1(), v1 = 1.0f - glyph.v1();

            vertices.addAll(List.of(
                    x0, y0, u0, v0, // top-left
                    x1, y0, u1, v0, // top-right
                    x1, y1, u1, v1, // bottom-right
                    x0, y1, u0, v1  // bottom-left
            ));

            indices.addAll(List.of(
                    indexOffset, indexOffset + 1, indexOffset + 2,
                    indexOffset + 2, indexOffset + 3, indexOffset
            ));

            xCursor += glyph.xAdvance();
            indexOffset += 4;
        }

        return new TextMesh(
                Mesh.loadMesh(
                        toFloatArray(vertices),
                        indices.stream().mapToInt(i -> i).toArray(),
                        AssetManager.getLayoutPosUv()
                ),
                maxX - minX,
                maxY - minX
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
