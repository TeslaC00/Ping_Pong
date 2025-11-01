package dev.teslac00;

import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class Rectangle2D extends RenderableObject {

    public Rectangle2D(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        super(mesh, material, x, y, scaleX, scaleY);
    }

    public void update(double deltaTime) {

        velocity.y = 0;
        if (InputManager.getKeyPressed(GLFW_KEY_W)) {
            velocity.y = 1;
        } else if (InputManager.getKeyPressed(GLFW_KEY_S)) {
            velocity.y = -1;
        }

        float MOVE_SPEED = 300.0f;
        float nextY = position.y + (float) (MOVE_SPEED * deltaTime * velocity.y);
        float limitY = (VIEWPORT_HEIGHT - scale.y) / 2.0f;
        float distance = Math.max(Math.min(nextY, limitY), -limitY) - position.y;   // Snap to limit and stop velocity

        translate(0, distance);
    }

}
