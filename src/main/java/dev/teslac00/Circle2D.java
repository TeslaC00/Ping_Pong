package dev.teslac00;

import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.Constants.VIEWPORT_WIDTH;

public class Circle2D extends RenderableObject {

    private int dirX = 1, dirY = 1;

    public Circle2D(Mesh mesh, Material material, float x, float y, float radius) {
        super(mesh, material, x, y, radius, radius);
    }

    public void update(double deltaTime) {
        float MOVE_SPEED = 150.0f;
        float dx = (float) (MOVE_SPEED * deltaTime * dirX);
        float dy = (float) (MOVE_SPEED * deltaTime * dirY);

        float nextX = this.position.x + dx;
        float nextY = this.position.y + dy;
        float limitX = (VIEWPORT_WIDTH / 2.0f) - this.scale.x;
        float limitY = (VIEWPORT_HEIGHT / 2.0f) - this.scale.y;

        if (nextY > limitY) {   // check top screen collision
            dy = limitY - this.position.y;
            dirY *= -1;
        } else if (nextY < -limitY) {    // check bottom screen collision
            dy = -limitY - this.position.y;
            dirY *= -1;
        }

        if (nextX > limitX) {    // check right screen collision
            dx = limitX - this.position.x;
            dirX *= -1;
        } else if (nextX < -limitX) {  // check left screen collision
            dx = -limitX - this.position.x;
            dirX *= -1;
        }

        translate(dx, dy);
    }
}
