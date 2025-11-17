package dev.teslac00.core;

import dev.teslac00.graphics.Mesh;
import dev.teslac00.graphics.MeshFactory;
import dev.teslac00.graphics.VertexLayout;

import static dev.teslac00.core.Constants.CIRCLE_MESH_DEFAULT_SEGMENTS;
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
    private static VertexLayout positionLayout;

    //    Meshes
    private static Mesh rectangleMesh;
    private static Mesh circularMesh;

    public void init() {
        positionLayout = new VertexLayout();
        positionLayout.addAttribute(2, GL_FLOAT, false);

        rectangleMesh = MeshFactory.createRectangle();
        circularMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);
    }

    public static VertexLayout getPositionLayout() {
        return positionLayout;
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
