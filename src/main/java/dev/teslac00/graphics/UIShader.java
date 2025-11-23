/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : UIShader
 * Author  : Vikas Kumar
 * Created : 23-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

import dev.teslac00.entities.Entity;
import dev.teslac00.ui.UIComponent;
import dev.teslac00.util.Constants;
import org.joml.Vector2f;

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
        setUniform("u_position", uiComponent.getPosition());
        setUniform("u_dimension", uiComponent.getDimension());
        setUniform("u_resolution",
                new Vector2f(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        setUniform("u_color", uiComponent.getMaterial().color());
    }
}
