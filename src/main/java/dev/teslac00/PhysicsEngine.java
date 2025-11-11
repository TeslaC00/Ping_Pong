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

        if (a instanceof BoxCollider box && b instanceof CircleCollider circle) {
            separateObjects(box, circle, dx, dy);

        } else if (a instanceof CircleCollider circle && b instanceof BoxCollider box) {
            separateObjects(box, circle, dx, dy);
        }
    }

    private void separateObjects(BoxCollider box, CircleCollider circle, float dx, float dy) {
        float overlapX = (box.getWidth() / 2) + circle.getRadius() - Math.abs(dx);
        float overlapY = (box.getHeight() / 2) + circle.getRadius() - Math.abs(dy);

        if (overlapX < overlapY) {
//            Horizontal Collision
            float direction = Math.signum(dx);

            circle.owner.position.x += direction * overlapX;
            circle.owner.velocity.x *= -1;

        } else {
//            Vertical Collision
            float direction = Math.signum(dy);

            circle.owner.position.y += direction * overlapY;
            circle.owner.velocity.y *= -1;
        }
    }

    public void clearColliders() {
        colliders.clear();
    }
}
