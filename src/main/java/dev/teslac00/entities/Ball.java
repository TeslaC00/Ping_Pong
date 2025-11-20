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
import dev.teslac00.physics.PhysicsBody;
import dev.teslac00.util.Colors;
import dev.teslac00.util.Constants;

import static dev.teslac00.util.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;

public class Ball extends Entity {

    public Ball() {

        float radius = 30.0f;
        float speed = 200.0f;

        transform.scale.set(radius);

        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.RED,
                AssetManager.getTexture(Constants.TEXTURE_LUIGI)
        );
        renderable = new Circle2D(material);

        CircleCollider collider = new CircleCollider(this, radius);
        physicsBody = new PhysicsBody(speed, collider);
    }

    @Override
    public void update(double deltaTime) {
        float dx = (float) (physicsBody.velocity.x * deltaTime);
        float dy = (float) (physicsBody.velocity.y * deltaTime);

        transform.translate(dx, dy);
        handleViewportCollision();
    }

    private void handleViewportCollision() {
        float limitX = (VIEWPORT_WIDTH / 2.0f) - transform.scale.x;
        float limitY = (VIEWPORT_HEIGHT / 2.0f) - transform.scale.y;

        if (transform.position.y > limitY) {   // check top screen collision
            transform.position.y = limitY;
            physicsBody.velocity.y *= -1;
        } else if (transform.position.y < -limitY) {    // check bottom screen collision
            transform.position.y = -limitY;
            physicsBody.velocity.y *= -1;
        }

        if (transform.position.x > limitX) {    // check right screen collision
            transform.position.x = limitX;
            physicsBody.velocity.x *= -1;
        } else if (transform.position.x < -limitX) {  // check left screen collision
            transform.position.x = -limitX;
            physicsBody.velocity.x *= -1;
        }
    }

    @Override
    public void destroy() {
    }
}
