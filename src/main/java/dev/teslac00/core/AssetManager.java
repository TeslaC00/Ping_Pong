package dev.teslac00.core;

import dev.teslac00.graphics.*;

import java.util.HashMap;
import java.util.Map;

import static dev.teslac00.core.Constants.*;
import static org.lwjgl.opengl.GL11C.GL_FLOAT;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : AssetManager
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
public class AssetManager {

    //    Vertex Layouts
    private static VertexLayout layoutPosUv;

    //    Meshes
//    TODO: maybe make each object own its mesh instead of asset manager, think of something else for instancing
    private static Mesh rectangleMesh;
    private static Mesh circularMesh;

    private final static Map<String, Texture> textureMap = new HashMap<>();

    public void init() {

        layoutPosUv = new VertexLayout();
        layoutPosUv.addAttribute(2, GL_FLOAT, false);   // position
        layoutPosUv.addAttribute(2, GL_FLOAT, false);   // uv coordinates

        rectangleMesh = MeshFactory.createQuad();
        circularMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);
    }

    public static Texture getTexture(String path) {
        return textureMap.computeIfAbsent(path, Texture::new);
    }

    public static VertexLayout getLayoutPosUv() {
        return layoutPosUv;
    }

    public static Mesh getRectangleMesh() {
        return rectangleMesh;
    }

    public static Mesh getCircularMesh() {
        return circularMesh;
    }

    public void destroy() {
        rectangleMesh.destroy();
        circularMesh.destroy();

        for (Texture texture : textureMap.values())
            texture.destroy();
        textureMap.clear();
    }
}
