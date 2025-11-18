package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;

import static dev.teslac00.core.Constants.STATIC_FRAGMENT_SHADER;
import static dev.teslac00.core.Constants.STATIC_VERTEX_SHADER;

public class StaticShader extends ShaderProgram {

    public StaticShader() {
        super(STATIC_VERTEX_SHADER, STATIC_FRAGMENT_SHADER);
    }

    @Override
    protected void bindAttributes() {
    }

    @Override
    public void loadUniforms(RenderableObject model) {
        Material material = model.getMaterial();

        setUniform("u_color", material.color());
        setUniform("u_trans", model.getTransform());
        setUniform("u_proj", Renderer.getProjBuffer());

        Texture texture = material.texture();
        if (texture != null) {
            texture.bind(0);    // bind texture to slot 0
            setUniform("u_useTexture", 1);
            setUniform("u_texture", 0); // sampler2D index
        } else {
            setUniform("u_useTexture", 0);
        }
    }
}
