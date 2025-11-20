package dev.teslac00.graphics;

import dev.teslac00.core.AssetManager;

public class Rectangle2D extends Renderable {

    public Rectangle2D(Material material) {
        super(AssetManager.getRectangleMesh(), material);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void destroy() {

    }
}
