package dev.teslac00;

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
        boolean overlapX = Math.abs(owner.position.x - other.owner.position.x) < radius + other.getWidth() / 2;
        boolean overlapY = Math.abs(owner.position.y - other.owner.position.y) < radius + other.getHeight() / 2;

        return overlapX && overlapY;
    }

    @Override
    public boolean intersectsCircleCollider(CircleCollider other) {
        boolean overlapX = Math.abs(owner.position.x - other.owner.position.x) < radius + other.radius;
        boolean overlapY = Math.abs(owner.position.y - other.owner.position.y) < radius + other.radius;

        return overlapX && overlapY;
    }

    public float getRadius() {
        return radius;
    }
}
