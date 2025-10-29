package dev.teslac00;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class Rectangle2D extends RenderableObject {

    private final float MOVE_SPEED = 300.0f;
    private int direction = 1;
    //    TODO: Move WIDTH, HEIGHT to separate global class
    final static int WIDTH = 1280, HEIGHT = 720;

    public Rectangle2D(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        super(mesh, material, x, y, scaleX, scaleY);
    }

    public void update(double deltaTime) {

        if (InputManager.getKeyPressed(GLFW_KEY_W)) {
            direction = -1;
        } else if (InputManager.getKeyPressed(GLFW_KEY_S)) {
            direction = 1;
        } else {
            direction = 0;
        }

        float distance = (float) (MOVE_SPEED * deltaTime * direction);
        float nextY = this.position.y + distance;

        if (nextY + this.scale.y >= HEIGHT) {
            distance = HEIGHT - this.scale.y - this.position.y;
        } else if (nextY <= 0) {
            distance = 0 - this.position.y;
        }

        translate(0, distance);
    }
}
