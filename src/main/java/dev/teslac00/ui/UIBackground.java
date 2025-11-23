/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : UIBackground
 * Author  : Vikas Kumar
 * Created : 23-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.ui;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Renderer;
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.UIShader;
import dev.teslac00.util.Constants;
import org.joml.Vector4f;

public class UIBackground extends UIComponent {

    public UIBackground(float x, float y, float width, float height, Vector4f color) {
        super(x, y, width, height);
        material = new Material(AssetManager.getShader(UIShader.class), color);
        mesh = AssetManager.getRectangleMesh();
    }

    public UIBackground(Vector4f color) {
        super(0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        material = new Material(AssetManager.getShader(UIShader.class), color);
        mesh = AssetManager.getRectangleMesh();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.submit(this);
    }

    @Override
    public void destroy() {

    }
}
