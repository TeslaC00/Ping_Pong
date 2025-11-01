package dev.teslac00;

import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.Constants.VIEWPORT_WIDTH;

public class Circle2D extends RenderableObject {

    public Circle2D(Mesh mesh, Material material, float x, float y, float radius) {
        super(mesh, material, x, y, radius, radius);
        float speed = 150.0f;
        this.velocity.set(1, 1).mul(speed);
    }

    public void update(double deltaTime) {
        float dx = (float) (velocity.x * deltaTime);
        float dy = (float) (velocity.y * deltaTime);

        translate(dx, dy);
        handleViewportCollision();
    }

    private void handleViewportCollision() {
        float limitX = (VIEWPORT_WIDTH / 2.0f) - scale.x;
        float limitY = (VIEWPORT_HEIGHT / 2.0f) - scale.y;

        if (position.y > limitY) {   // check top screen collision
            position.y = limitY;
            velocity.y *= -1;
        } else if (position.y < -limitY) {    // check bottom screen collision
            position.y = -limitY;
            velocity.y *= -1;
        }

        if (position.x > limitX) {    // check right screen collision
            position.x = limitX;
            velocity.x *= -1;
        } else if (position.x < -limitX) {  // check left screen collision
            position.x = -limitX;
            velocity.x *= -1;
        }
    }
}
