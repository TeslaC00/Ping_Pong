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
import dev.teslac00.entities.Entity;
import dev.teslac00.ui.UIComponent;
import dev.teslac00.util.Constants;

public class MSDFShader extends ShaderProgram {

    public MSDFShader() {
        super(Constants.SHADER_MSDF_VERTEX, Constants.SHADER_MSDF_FRAGMENT);
    }

    @Override
    protected void bindAttributes() {

    }

    @Override
    public void loadUniforms(Entity entity) {
        Material material = entity.renderable.material;

        setUniform("u_color", material.color());
        setUniform("u_trans", entity.getTransform());    // model
        setUniform("u_proj", Renderer.getProjBuffer()); // projection

        Texture texture = material.texture();
        if (texture == null)
            throw new RuntimeException("Unable to load MSDF Shader Font texture from material");

        texture.bind(0);    // bind texture to slot 0
        setUniform("u_font_atlas", 0); // sampler2D index
//        TODO: apply smoothing or remove
        setUniform("u_smoothing", 1.0f);    // smoothing
    }

    @Override
    public void loadUniforms(UIComponent uiComponent) {
        setUniform("u_trans", uiComponent.getTransform());    // model
        setUniform("u_proj", Renderer.getUiProjection()); // projection
        setUniform("u_color", uiComponent.getMaterial().color());

        Texture texture = uiComponent.getMaterial().texture();
        if (texture == null)
            throw new RuntimeException("Unable to load MSDF Shader Font texture from material");

        texture.bind(0);    // bind texture to slot 0
        setUniform("u_font_atlas", 0); // sampler2D index
//        TODO: apply smoothing or remove
        setUniform("u_smoothing", 1.0f);    // smoothing
    }
}
