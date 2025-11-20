package dev.teslac00.physics;

import dev.teslac00.entities.Entity;

public abstract class Collider {
    //    TODO: remove physics properties from renderable object
    protected Entity owner;

    public Collider(Entity owner) {
        this.owner = owner;
    }

    public abstract boolean intersects(Collider other);

    public abstract boolean intersectsBoxCollider(BoxCollider other);

    public abstract boolean intersectsCircleCollider(CircleCollider other);
}
