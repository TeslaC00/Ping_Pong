
package dev.teslac00.physics;

import dev.teslac00.entities.Entity;

public class CircleCollider extends Collider {

    private final float radius;

    public CircleCollider(Entity owner, float radius) {
        super(owner);
        this.radius = radius;
    }

    @Override
    public boolean intersects(Collider other) {
        return other.intersectsCircleCollider(this);
    }

    @Override
    public boolean intersectsBoxCollider(BoxCollider other) {
        boolean overlapX = Math.abs(owner.transform.position.x - other.owner.transform.position.x) <
                radius + other.getWidth() / 2;
        boolean overlapY = Math.abs(owner.transform.position.y - other.owner.transform.position.y) <
                radius + other.getHeight() / 2;

        return overlapX && overlapY;
    }

    @Override
    public boolean intersectsCircleCollider(CircleCollider other) {
        float dx = owner.transform.position.x - other.owner.transform.position.x;
        float dy = owner.transform.position.y - other.owner.transform.position.y;
        float dst = (float) Math.sqrt(dx * dx + dy * dy);

        return dst < radius + other.radius;
    }

    public float getRadius() {
        return radius;
    }
}
