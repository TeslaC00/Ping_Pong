
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
        float dx = owner.position.x - other.owner.position.x;
        float dy = owner.position.y - other.owner.position.y;
        float dst = (float) Math.sqrt(dx * dx + dy * dy);

        return dst < radius + other.radius;
    }

    public float getRadius() {
        return radius;
    }
}
