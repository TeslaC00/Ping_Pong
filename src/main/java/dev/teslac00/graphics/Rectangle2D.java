package dev.teslac00.graphics;

import dev.teslac00.core.AssetManager;

public class Rectangle2D extends RenderableObject {

    public Rectangle2D(Material material, float x, float y, float scaleX, float scaleY) {
        super(AssetManager.getRectangleMesh(), material, x, y, scaleX, scaleY);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void destroy() {

    }
}
