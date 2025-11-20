package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;
import dev.teslac00.entities.Entity;

import static dev.teslac00.util.Constants.SHADER_STATIC_FRAGMENT;
import static dev.teslac00.util.Constants.SHADER_STATIC_VERTEX;

public class StaticShader extends ShaderProgram {

    public StaticShader() {
        super(SHADER_STATIC_VERTEX, SHADER_STATIC_FRAGMENT);
    }

    @Override
    protected void bindAttributes() {
    }

    @Override
    public void loadUniforms(Entity entity) {
        Material material = entity.renderable.material;

        setUniform("u_color", material.color());
        setUniform("u_trans", entity.getTransform());
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
