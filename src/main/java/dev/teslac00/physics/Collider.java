package dev.teslac00.physics;

import dev.teslac00.graphics.RenderableObject;

public abstract class Collider {
    protected RenderableObject owner;

    public Collider(RenderableObject owner) {
        this.owner = owner;
    }

    public abstract boolean intersects(Collider other);

    public abstract boolean intersectsBoxCollider(BoxCollider other);

    public abstract boolean intersectsCircleCollider(CircleCollider other);
}
