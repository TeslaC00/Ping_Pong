package dev.teslac00.graphics;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL11C.GL_INT;


/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : VertexLayout
 * Author  : Vikas Kumar
 * Created : 17-11-2025
 * ---------------------------------------------------------------
 */
public class VertexLayout {

    private final List<VertexAttribute> attributes = new ArrayList<>();
    private int stride = 0;

    public void addAttribute(int size, int type, boolean normalized) {
        attributes.add(new VertexAttribute(attributes.size(), size, type, normalized, stride));

        switch (type) {
            case GL_INT -> stride += size * Integer.BYTES;
            case GL_FLOAT -> stride += size * Float.BYTES;
        }
    }

    public List<VertexAttribute> getAttributes() {
        return attributes;
    }

    public int getStride() {
        return stride;
    }
}
