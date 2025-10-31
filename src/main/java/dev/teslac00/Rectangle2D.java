package dev.teslac00;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class Rectangle2D extends RenderableObject {

    private final float MOVE_SPEED = 300.0f;
    private int direction = 1;
    final static int HEIGHT = 720;

    public Rectangle2D(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        super(mesh, material, x, y, scaleX, scaleY);
    }

    public void update(double deltaTime) {

        if (InputManager.getKeyPressed(GLFW_KEY_W)) {
            direction = 1;
        } else if (InputManager.getKeyPressed(GLFW_KEY_S)) {
            direction = -1;
        } else {
            direction = 0;
        }

        float distance = (float) (MOVE_SPEED * deltaTime * direction);
        float nextY = this.position.y + distance;
        float limitY = (HEIGHT - this.scale.y) / 2.0f;

        if (nextY >= limitY) {   // check top collision
            distance = limitY - this.position.y;
        } else if (nextY <= -limitY) {    // check bottom collision
            distance = -limitY - this.position.y;
        }

        translate(0, distance);
    }
}
