package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_SHADER_FILE = "VertexShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "FragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {
    }

    @Override
    public void loadUniforms(RenderableObject model) {
        setUniform("u_color", model.getMaterial().color());
        setUniform("u_trans", model.getTransform());
        setUniform("u_proj", Renderer.getProjBuffer());
    }
}
