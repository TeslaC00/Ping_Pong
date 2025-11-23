/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : UIShader
 * Author  : Vikas Kumar
 * Created : 23-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

import dev.teslac00.core.Renderer;
import dev.teslac00.entities.Entity;
import dev.teslac00.ui.UIComponent;
import dev.teslac00.util.Constants;

public class UIShader extends ShaderProgram {

    public UIShader() {
        super(Constants.SHADER_UI_VERTEX, Constants.SHADER_UI_FRAGMENT);
    }

    @Override
    protected void bindAttributes() {

    }

    @Override
    public void loadUniforms(Entity entity) {
    }

    @Override
    public void loadUniforms(UIComponent uiComponent) {

        setUniform("u_trans", uiComponent.getTransform());    // model
        setUniform("u_proj", Renderer.getUiProjection()); // projection
        setUniform("u_color", uiComponent.getMaterial().color());
    }
}
