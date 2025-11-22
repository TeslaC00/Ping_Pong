/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Background
 * Author  : Vikas Kumar
 * Created : 22-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.ui;

import dev.teslac00.core.AssetManager;
import dev.teslac00.entities.Entity;
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.Rectangle2D;
import dev.teslac00.graphics.StaticShader;
import dev.teslac00.util.Constants;
import org.joml.Vector4f;

public class Background extends Entity {

    public Background(Vector4f color) {
        super(0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        Material material = new Material(AssetManager.getShader(StaticShader.class), color);
        this.renderable = new Rectangle2D(material);
    }

    public Background(float x, float y, float scaleX, float scaleY, Vector4f color) {
        super(x, y, scaleX, scaleY);
        Material material = new Material(AssetManager.getShader(StaticShader.class), color);
        this.renderable = new Rectangle2D(material);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void destroy() {

    }
}
