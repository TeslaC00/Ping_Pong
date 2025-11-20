/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Ball
 * Author  : Vikas Kumar
 * Created : 20-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import dev.teslac00.core.AssetManager;
import dev.teslac00.graphics.Circle2D;
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.StaticShader;
import dev.teslac00.physics.CircleCollider;
import dev.teslac00.util.Colors;
import dev.teslac00.util.Constants;

import static dev.teslac00.util.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;

public class Ball extends RenderableEntity {

    private final CircleCollider collider;

    public Ball() {
        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.COLOR_RED,
                AssetManager.getTexture(Constants.TEXTURE_LUIGI)
        );

        float radius = 30.0f;
        float speed = 200.0f;
        renderable = new Circle2D(AssetManager.getCircularMesh(), material, 0, 0, radius);
        renderable.getVelocity().set(1).mul(speed);

        collider = new CircleCollider(renderable, radius);
    }

    @Override
    public void update(double deltaTime) {
        float dx = (float) (renderable.getVelocity().x * deltaTime);
        float dy = (float) (renderable.getVelocity().y * deltaTime);

        renderable.translate(dx, dy);
        handleViewportCollision();
    }

    private void handleViewportCollision() {
        float limitX = (VIEWPORT_WIDTH / 2.0f) - renderable.getScale().x;
        float limitY = (VIEWPORT_HEIGHT / 2.0f) - renderable.getScale().y;

        if (renderable.getPosition().y > limitY) {   // check top screen collision
            renderable.getPosition().y = limitY;
            renderable.getVelocity().y *= -1;
        } else if (renderable.getPosition().y < -limitY) {    // check bottom screen collision
            renderable.getPosition().y = -limitY;
            renderable.getVelocity().y *= -1;
        }

        if (renderable.getPosition().x > limitX) {    // check right screen collision
            renderable.getPosition().x = limitX;
            renderable.getVelocity().x *= -1;
        } else if (renderable.getPosition().x < -limitX) {  // check left screen collision
            renderable.getPosition().x = -limitX;
            renderable.getVelocity().x *= -1;
        }
    }

    public CircleCollider getCollider() {
        return collider;
    }

    @Override
    public void destroy() {
    }
}
