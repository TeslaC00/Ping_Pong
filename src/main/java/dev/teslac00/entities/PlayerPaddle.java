/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : PlayerPaddle
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Colors;
import dev.teslac00.graphics.*;
import dev.teslac00.input.InputManager;
import dev.teslac00.physics.BoxCollider;

import static dev.teslac00.core.Constants.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class PlayerPaddle extends RenderableEntity {

    private final BoxCollider collider;

    public PlayerPaddle() {
        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;
        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.COLOR_GREEN,
                AssetManager.getTexture(TEXTURE_MARIO)
        );

        renderable = new Rectangle2D(AssetManager.getRectangleMesh(), material,
                (-VIEWPORT_WIDTH + width) / 2,
                (VIEWPORT_HEIGHT - height) / 2, width, height);

        collider = new BoxCollider(renderable, width, height);
    }

    @Override
    public void update(double deltaTime) {
        renderable.getVelocity().y = 0;

        if (InputManager.getKeyPressed(GLFW_KEY_W))
            renderable.getVelocity().y = 1;
        else if (InputManager.getKeyPressed(GLFW_KEY_S))
            renderable.getVelocity().y = -1;

        float MOVE_SPEED = 300.0f;
        float nextY = renderable.getPosition().y +
                (float) (MOVE_SPEED * deltaTime * renderable.getVelocity().y);
        float limitY = (VIEWPORT_HEIGHT - renderable.getScale().y) / 2.0f;
        // Snap to limit and stop velocity
        float distance = Math.max(Math.min(nextY, limitY), -limitY) - renderable.getPosition().y;

        renderable.translate(0, distance);
    }

    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public void destroy() {

    }
}
