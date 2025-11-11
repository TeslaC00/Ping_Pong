package dev.teslac00.core;

import dev.teslac00.graphics.Mesh;
import dev.teslac00.graphics.MeshFactory;

import static dev.teslac00.core.Constants.CIRCLE_MESH_DEFAULT_SEGMENTS;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : AssetManager
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
public class AssetManager {

    private static Mesh rectangleMesh;
    private static Mesh circularMesh;

    public void init() {
        rectangleMesh = MeshFactory.createRectangle();
        circularMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);
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
    }
}
