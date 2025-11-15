package dev.teslac00.graphics;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : MSDFShader
 * Author  : Vikas Kumar
 * Created : 15-11-2025
 * ---------------------------------------------------------------
 */
public class MSDFShader extends ShaderProgram {

    private static final String MSDF_VERTEX_SHADER_FILE = "msdf.vert";
    private static final String MSDF_FRAGMENT_SHADER_FILE = "msdf.frag";

    public MSDFShader() {
        super(MSDF_VERTEX_SHADER_FILE, MSDF_FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {

    }

    @Override
    public void loadUniforms(RenderableObject model) {

    }
}
