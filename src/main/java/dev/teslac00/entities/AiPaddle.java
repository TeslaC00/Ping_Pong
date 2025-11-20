/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : AiPaddle
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import dev.teslac00.core.AssetManager;
import dev.teslac00.util.Colors;
import dev.teslac00.graphics.*;
import dev.teslac00.physics.BoxCollider;

import static dev.teslac00.util.Constants.*;

public class AiPaddle extends RenderableEntity {

    private final BoxCollider collider;

    public AiPaddle() {
        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;

        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.COLOR_BLUE,
                AssetManager.getTexture(TEXTURE_KNIGHT)
        );

        renderable = new Rectangle2D(
                material,
                (VIEWPORT_WIDTH - width) / 2,
                0,
                width, height
        );

        collider = new BoxCollider(renderable, width, height);
    }

    @Override
    public void update(double deltaTime) {

    }

    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public void destroy() {

    }
}
