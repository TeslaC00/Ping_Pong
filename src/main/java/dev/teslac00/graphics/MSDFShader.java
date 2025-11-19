/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : MSDFShader
 * Author  : Vikas Kumar
 * Created : 18-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;
import dev.teslac00.util.Constants;

public class MSDFShader extends ShaderProgram {

    public MSDFShader() {
        super(Constants.SHADER_MSDF_VERTEX, Constants.SHADER_MSDF_FRAGMENT);
    }

    @Override
    protected void bindAttributes() {

    }

    @Override
    public void loadUniforms(RenderableObject model) {
        Material material = model.getMaterial();

        setUniform("u_color", material.color());
        setUniform("u_trans", model.getTransform());    // model
        setUniform("u_proj", Renderer.getProjBuffer()); // projection

        Texture texture = material.texture();
        if (texture == null)
            throw new RuntimeException("Unable to load MSDF Shader Font texture from material");

        texture.bind(0);    // bind texture to slot 0
        setUniform("u_font_atlas", 0); // sampler2D index
//        TODO: apply smoothing or remove
        setUniform("u_smoothing", 1.0f);    // smoothing
    }
}
