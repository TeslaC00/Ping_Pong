
package dev.teslac00.physics;

import dev.teslac00.graphics.RenderableObject;

public class CircleCollider extends Collider {

    private final float radius;

    public CircleCollider(RenderableObject owner, float radius) {
        super(owner);
        this.radius = radius;
    }

    @Override
    public boolean intersects(Collider other) {
        return other.intersectsCircleCollider(this);
    }

    @Override
    public boolean intersectsBoxCollider(BoxCollider other) {
        boolean overlapX = Math.abs(owner.getPosition().x - other.owner.getPosition().x) <
                radius + other.getWidth() / 2;
        boolean overlapY = Math.abs(owner.getPosition().y - other.owner.getPosition().y) <
                radius + other.getHeight() / 2;

        return overlapX && overlapY;
    }

    @Override
    public boolean intersectsCircleCollider(CircleCollider other) {
        float dx = owner.getPosition().x - other.owner.getPosition().x;
        float dy = owner.getPosition().y - other.owner.getPosition().y;
        float dst = (float) Math.sqrt(dx * dx + dy * dy);

        return dst < radius + other.radius;
    }

    public float getRadius() {
        return radius;
    }
}
