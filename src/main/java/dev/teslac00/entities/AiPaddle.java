/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : AiPaddle
 * Author  : Vikas Kumar
 * Created : 12-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import dev.teslac00.core.AssetManager;
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.Rectangle2D;
import dev.teslac00.graphics.StaticShader;
import dev.teslac00.physics.BoxCollider;
import dev.teslac00.physics.PhysicsBody;
import dev.teslac00.util.Colors;

import static dev.teslac00.util.Constants.*;

public class AiPaddle extends Entity {

    private final Ball ball;

    private final float speed = 350f;   // AI paddle movement speed
    private final float reactionDelay = 0.01f;  // AI hesitates slightly
    private final float jitterAmount = 5f;  // AI aim shakes a bit

    private double reactionTimer = 0;

    public AiPaddle(Ball ball) {
        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;

        transform.position.set((VIEWPORT_WIDTH - width) / 2, 0, 0);
        transform.scale.set(width, height, 1);

        this.ball = ball;

        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.BLUE,
                AssetManager.getTexture(TEXTURE_KNIGHT)
        );
        renderable = new Rectangle2D(material);

        BoxCollider collider = new BoxCollider(this, width, height);
        physicsBody = new PhysicsBody(collider);
    }

    @Override
    public void update(double deltaTime) {
        reactionTimer += deltaTime;
        if (reactionTimer < reactionDelay) return;
        reactionTimer = 0;

        float ballY = ball.transform.position.y;
        float targetY = ballY + (float) (Math.random() * jitterAmount - jitterAmount / 2);

        float paddleY = transform.position.y;
        float dy = targetY - paddleY;

        if (Math.abs(dy) > 3) {
            float direction = Math.signum(dy);
            transform.translate(0, (float) (direction * speed * deltaTime));
        }

        clampToBounds();
    }

    private void clampToBounds() {
        float limitY = (VIEWPORT_HEIGHT - transform.scale.y) / 2f;
        float y = Math.max(-limitY, Math.min(transform.position.y, limitY));

        transform.position.set(transform.position.x, y, 0);
    }

    @Override
    public void destroy() {

    }
}
