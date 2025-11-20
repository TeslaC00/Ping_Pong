package dev.teslac00.physics;

import dev.teslac00.entities.Entity;

/**
 * An axis-aligned box collider (AABB) for basic 2D collision detection.
 * <p>
 * It supports intersection cheks with both BoxCollider and CircleCollider.
 * </p>
 *
 * <p> <b>Coordinate system:</b> Center-based, where {@code owner.position} </p>
 */
public class BoxCollider extends Collider {

    private final float width;
    private final float height;

    public BoxCollider(Entity owner, float width, float height) {
        super(owner);
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean intersects(Collider other) {
        return other.intersectsBoxCollider(this);
    }

    @Override
    public boolean intersectsBoxCollider(BoxCollider other) {
        boolean overlapX = Math.abs(owner.transform.position.x - other.owner.transform.position.x) < (width + other.width) / 2;
        boolean overlapY = Math.abs(owner.transform.position.y - other.owner.transform.position.y) < (height + other.height) / 2;

        return overlapX && overlapY;
    }

    @Override
    public boolean intersectsCircleCollider(CircleCollider other) {
        boolean overlapX = Math.abs(owner.transform.position.x - other.owner.transform.position.x) < width / 2 + other.getRadius();
        boolean overlapY = Math.abs(owner.transform.position.y - other.owner.transform.position.y) < height / 2 + other.getRadius();

        return overlapX && overlapY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
