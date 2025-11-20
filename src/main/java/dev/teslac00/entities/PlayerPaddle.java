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
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.Rectangle2D;
import dev.teslac00.graphics.StaticShader;
import dev.teslac00.input.InputManager;
import dev.teslac00.physics.BoxCollider;
import dev.teslac00.physics.PhysicsBody;
import dev.teslac00.util.Colors;

import static dev.teslac00.util.Constants.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class PlayerPaddle extends Entity {

    public PlayerPaddle() {

        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;

        transform.position.set((-VIEWPORT_WIDTH + width) / 2, (VIEWPORT_HEIGHT - height) / 2, 0);
        transform.scale.set(width, height, 1);

        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.GREEN,
                AssetManager.getTexture(TEXTURE_MARIO)
        );
        renderable = new Rectangle2D(material);

        BoxCollider collider = new BoxCollider(this, width, height);
        physicsBody = new PhysicsBody(collider);
    }

    @Override
    public void update(double deltaTime) {
        physicsBody.velocity.y = 0;

        if (InputManager.getKeyPressed(GLFW_KEY_W))
            physicsBody.velocity.y = 1;
        else if (InputManager.getKeyPressed(GLFW_KEY_S))
            physicsBody.velocity.y = -1;

        float MOVE_SPEED = 300.0f;
        float nextY = transform.position.y +
                (float) (MOVE_SPEED * deltaTime * physicsBody.velocity.y);
        float limitY = (VIEWPORT_HEIGHT - transform.scale.y) / 2.0f;
        // Snap to limit and stop velocity
        float distance = Math.max(Math.min(nextY, limitY), -limitY) - transform.position.y;

        transform.translate(0, distance);
    }

    @Override
    public void destroy() {

    }
}
