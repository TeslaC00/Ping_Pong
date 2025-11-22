/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : AssetManager
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.core;

import dev.teslac00.graphics.*;
import dev.teslac00.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static dev.teslac00.util.Constants.CIRCLE_MESH_DEFAULT_SEGMENTS;
import static dev.teslac00.util.Constants.FONT_CHELA_ONE_JSON;
import static org.lwjgl.opengl.GL11C.GL_FLOAT;

public class AssetManager {

    //    Vertex Layouts
    private static VertexLayout layoutPosUv;

    //    Meshes
//    TODO: maybe make each object own its mesh instead of asset manager, think of something else for instancing
    private static Mesh rectangleMesh;
    private static Mesh circularMesh;

    private final static Map<String, Texture> textureMap = new HashMap<>();
    private final static Map<Class<? extends ShaderProgram>, Supplier<? extends ShaderProgram>> shaderFactories = new HashMap<>();
    private final static Map<Class<? extends ShaderProgram>, ShaderProgram> shaderInstances = new HashMap<>();

    private static Font fontChela;
    private static Font fontPatrick;

    public void init() {

        layoutPosUv = new VertexLayout();
        layoutPosUv.addAttribute(2, GL_FLOAT, false);   // position
        layoutPosUv.addAttribute(2, GL_FLOAT, false);   // uv coordinates

        rectangleMesh = MeshFactory.createQuad();
        circularMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);

//        Register shader factories
        shaderFactories.put(StaticShader.class, StaticShader::new);
        shaderFactories.put(MSDFShader.class, MSDFShader::new);

        fontChela = new Font(Constants.FONT_CHELA_ONE_ATLAS, FONT_CHELA_ONE_JSON);
        fontPatrick = new Font(Constants.FONT_PATRICK_HAND_ATLAS, Constants.FONT_PATRICK_HAND_JSON);
    }

    public static Texture getTexture(String path) {
        return textureMap.computeIfAbsent(path, Texture::new);
    }

    public static ShaderProgram getShader(Class<? extends ShaderProgram> shaderClass) {
        return shaderInstances.computeIfAbsent(shaderClass, shader -> {
            Supplier<? extends ShaderProgram> factory = shaderFactories.get(shader);
            if (factory == null)
                throw new RuntimeException("No factory registered for shader: %s".formatted(shader.getName()));
            return factory.get();
        });
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

    public static Font getFontChela() {
        return fontChela;
    }

    public static Font getFontPatrick() {
        return fontPatrick;
    }

    public void destroy() {
        rectangleMesh.destroy();
        circularMesh.destroy();

        for (Texture texture : textureMap.values())
            texture.destroy();
        textureMap.clear();

        for (ShaderProgram shaderProgram : shaderInstances.values())
            shaderProgram.destroy();
        shaderFactories.clear();
        shaderInstances.clear();

        fontChela.destroy();
    }
}
