package dev.teslac00.physics;

import dev.teslac00.entities.Entity;
import dev.teslac00.entities.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles basic 2D physics simulation and collision resolution between colliders.
 * <p>
 * The {@code PhysicsEngine} maintains a list of {@link Collider} objects and performs
 * simple pairwise intersection checks each frame. It currently supports interactions
 * between {@link BoxCollider} and {@link CircleCollider}.
 * </p>
 *
 * <p>
 * This implementation uses <b>discrete collision detection</b> (no continuous physics)
 * and performs immediate position and velocity corrections upon overlap.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Maintain and manage collider instances</li>
 *     <li>Perform intersection tests between all active colliders</li>
 *     <li>Resolve simple box–circle collisions via separation</li>
 * </ul>
 */
public final class PhysicsEngine {

    private final List<PhysicsBody> bodies = new ArrayList<>();

    /**
     * Adds a Entity with Physics Body to the physics simulation.
     *
     * @param entity The Entity with physics body to be tracked by the physics engine.
     */
    public void add(Entity entity) {
        if (entity.physicsBody != null)
            bodies.add(entity.physicsBody);
    }

    /**
     * Updates the physics simulation for the current frame.
     * <p>
     * Iterates through all registered colliders and performs pairwise intersection
     * checks. When a collision is detected, {@link #resolveCollision(Collider, Collider)}
     * is invoked to separate the objects and invert velocities.
     * </p>
     *
     * @param deltaTime The time elapsed since the last frame, in seconds.
     */
    public void update(double deltaTime) {
        integrate(deltaTime);
        checkCollisions();
    }

    private void integrate(double deltaTime) {
        for (PhysicsBody body : bodies) {
            Vector2f velocity = body.velocity;
            double dx = velocity.x * deltaTime;
            double dy = velocity.y * deltaTime;
            Transform transform = body.collider.owner.transform;
            transform.translate((float) dx, (float) dy);
//            TODO: remove physics update from entities
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                Collider a = bodies.get(i).collider;
                Collider b = bodies.get(j).collider;

                if (a.intersects(b)) {
                    System.out.printf("Collision between %s and %s%n",
                            a.getClass().getName(), b.getClass().getName());
                    resolveCollision(a, b);
                }
            }
        }
    }

    /**
     * Resolves a detected collision between two colliders.
     * <p>
     * This implementation currently supports resolving collisions between
     * {@link BoxCollider} and {@link CircleCollider} pairs.
     * </p>
     *
     * @param a The first collider.
     * @param b The second collider.
     */
    private void resolveCollision(Collider a, Collider b) {
        float dx = b.owner.transform.position.x - a.owner.transform.position.x;
        float dy = b.owner.transform.position.y - a.owner.transform.position.y;

        if (a instanceof BoxCollider box && b instanceof CircleCollider circle) {
            separateObjects(box, circle, dx, dy);

        } else if (a instanceof CircleCollider circle && b instanceof BoxCollider box) {
            separateObjects(box, circle, dx, dy);
        }
    }

    /**
     * Separates a circle from a box collider upon intersection and reflects its velocity.
     * <p>
     * The separation is determined along the axis of the least overlap (horizontal or vertical).
     * The circle’s velocity component on that axis is inverted to simulate a simple bounce.
     * </p>
     *
     * @param box    The box collider.
     * @param circle The circle collider.
     * @param dx     The X-axis distance between their centers.
     * @param dy     The Y-axis distance between their centers.
     */
    private void separateObjects(BoxCollider box, CircleCollider circle, float dx, float dy) {
        float overlapX = (box.getWidth() / 2) + circle.getRadius() - Math.abs(dx);
        float overlapY = (box.getHeight() / 2) + circle.getRadius() - Math.abs(dy);

        if (overlapX < overlapY) {
//            Horizontal Collision
            float direction = Math.signum(dx);

            circle.owner.transform.position.x += direction * overlapX;
            circle.owner.physicsBody.velocity.x *= -1;

        } else {
//            Vertical Collision
            float direction = Math.signum(dy);

            circle.owner.transform.position.y += direction * overlapY;
            circle.owner.physicsBody.velocity.y *= -1;
        }
    }

    /**
     * Removes all registered colliders from the simulation.
     * <p>
     * Useful when switching layers or scenes to ensure stale physics data
     * does not carry over.
     * </p>
     */
    public void destroy() {
        bodies.clear();
    }
}
