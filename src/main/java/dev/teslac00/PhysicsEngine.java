package dev.teslac00;

import java.util.ArrayList;
import java.util.List;

public final class PhysicsEngine {

    private final List<Collider> colliders = new ArrayList<>();

    public void add(Collider collider) {
        colliders.add(collider);
    }

    public void update(double deltaTime) {
        for (int i = 0; i < colliders.size(); i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                Collider a = colliders.get(i);
                Collider b = colliders.get(j);

                if (a.intersects(b)) {
                    System.out.printf("Collision between %s and %s%n",
                            a.getClass().getName(), b.getClass().getName());
                    resolveCollision(a, b);
                }
            }
        }
    }

    private void resolveCollision(Collider a, Collider b) {
        float dx = b.owner.position.x - a.owner.position.x;
        float dy = b.owner.position.y - a.owner.position.y;

        if (Math.abs(dx) > Math.abs(dy)) {
            a.owner.velocity.x *= -1;
            b.owner.velocity.x *= -1;
        } else {
            a.owner.velocity.y *= -1;
            b.owner.velocity.y *= -1;
        }
    }
}
