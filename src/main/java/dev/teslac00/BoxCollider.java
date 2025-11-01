package dev.teslac00;

public class BoxCollider extends Collider {
    //    uses owner position as its own
    private final float width;
    private final float height;
    // center based coordinate system

    public BoxCollider(RenderableObject owner, float width, float height) {
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
        boolean overlapX = Math.abs(owner.position.x - other.owner.position.x) < width / 2 + other.width / 2;
        boolean overlapY = Math.abs(owner.position.y - other.owner.position.y) < height / 2 + other.height / 2;

        return overlapX && overlapY;
    }

    @Override
    public boolean intersectsCircleCollider(CircleCollider other) {
        boolean overlapX = Math.abs(owner.position.x - other.owner.position.x) < width / 2 + other.getRadius();
        boolean overlapY = Math.abs(owner.position.y - other.owner.position.y) < height / 2 + other.getRadius();

        return overlapX && overlapY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
