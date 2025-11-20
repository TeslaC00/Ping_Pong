/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : PhysicsBody
 * Author  : Vikas Kumar
 * Created : 20-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.physics;

import org.joml.Vector2f;

public class PhysicsBody {

    public Vector2f velocity = new Vector2f(0, 0);
    public Collider collider;

    public PhysicsBody(Collider collider) {
        this.collider = collider;
    }

    public PhysicsBody(float speed, Collider collider) {
        velocity.set(speed);
        this.collider = collider;
    }
}
