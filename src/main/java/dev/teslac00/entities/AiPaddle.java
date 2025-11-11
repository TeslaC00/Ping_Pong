package dev.teslac00.entities;

import dev.teslac00.core.Colors;
import dev.teslac00.graphics.*;
import dev.teslac00.physics.BoxCollider;

import static dev.teslac00.core.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.core.Constants.VIEWPORT_WIDTH;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : AiPaddle
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
public class AiPaddle {

    private final Mesh rectangleMesh;
    private final Rectangle2D rectangle2D;
    private final BoxCollider collider;

    public AiPaddle(int shaderId) {
        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;

        rectangleMesh = MeshFactory.createRectangle();
        Material material = new Material(shaderId, Colors.COLOR_BLUE);

        rectangle2D = new Rectangle2D(rectangleMesh, material,
                (VIEWPORT_WIDTH - width) / 2, 0, width, height);

        collider = new BoxCollider(rectangle2D, width, height);
    }

    public void update(double deltaTime) {

    }

    public void destroy() {
        rectangleMesh.destroy();
    }

    public Rectangle2D getRectangle2D() {
        return rectangle2D;
    }

    public BoxCollider getCollider() {
        return collider;
    }
}
