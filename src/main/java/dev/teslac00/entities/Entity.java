/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Entity
 * Author  : Vikas Kumar
 * Created : 19-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import dev.teslac00.graphics.Renderable;
import dev.teslac00.physics.PhysicsBody;
import org.joml.Matrix4f;

/**
 * Basic entity system. Destroy its mesh when deleted
 */
public abstract class Entity {

    public final Transform transform = new Transform();
    public Renderable renderable = null;
    public PhysicsBody physicsBody = null;

    public Entity() {
    }

    public Entity(float x, float y, float scale) {
        transform.position.set(x, y, 0);
        transform.scale.set(scale);
    }

    public Entity(float x, float y, float scaleX, float scaleY) {
        transform.position.set(x, y, 1);
        transform.scale.set(scaleX, scaleY, 1);
    }

    public abstract void update(double deltaTime);

    public abstract void destroy();

    public Matrix4f getTransform() {
        return transform.getTransform();
    }

}
