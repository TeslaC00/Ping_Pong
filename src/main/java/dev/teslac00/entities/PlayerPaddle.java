package dev.teslac00.entities;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Colors;
import dev.teslac00.graphics.*;
import dev.teslac00.input.InputManager;
import dev.teslac00.physics.BoxCollider;

import static dev.teslac00.core.Constants.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : PlayerPaddle
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
public class PlayerPaddle {

    private final Rectangle2D rectangle2D;
    private final BoxCollider collider;

    public PlayerPaddle(int shaderId) {
        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;
        Material material = new Material(shaderId, Colors.COLOR_GREEN);

        rectangle2D = new Rectangle2D(AssetManager.getRectangleMesh(), material,
                (-VIEWPORT_WIDTH + width) / 2,
                (VIEWPORT_HEIGHT - height) / 2, width, height);

        collider = new BoxCollider(rectangle2D, width, height);
    }

    public void update(double deltaTime) {
        rectangle2D.getVelocity().y = 0;

        if (InputManager.getKeyPressed(GLFW_KEY_W))
            rectangle2D.getVelocity().y = 1;
        else if (InputManager.getKeyPressed(GLFW_KEY_S))
            rectangle2D.getVelocity().y = -1;

        float MOVE_SPEED = 300.0f;
        float nextY = rectangle2D.getPosition().y +
                (float) (MOVE_SPEED * deltaTime * rectangle2D.getVelocity().y);
        float limitY = (VIEWPORT_HEIGHT - rectangle2D.getScale().y) / 2.0f;
        float distance = Math.max(Math.min(nextY, limitY), -limitY) - rectangle2D.getPosition().y;   // Snap to limit and stop velocity

        rectangle2D.translate(0, distance);
    }

    public Rectangle2D getRectangle2D() {
        return rectangle2D;
    }

    public BoxCollider getCollider() {
        return collider;
    }
}
