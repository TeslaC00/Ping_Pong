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
import dev.teslac00.util.Colors;
import dev.teslac00.graphics.*;
import dev.teslac00.physics.BoxCollider;

import static dev.teslac00.util.Constants.*;

public class AiPaddle extends RenderableEntity {

    private final BoxCollider collider;
    private final Ball ball;

    private final float speed = 350f;   // AI paddle movement speed
    private final float reactionDelay = 0.01f;  // AI hesitates slightly
    private final float jitterAmount = 5f;  // AI aim shakes a bit

    private double reactionTimer = 0;

    public AiPaddle(Ball ball) {
        this.ball = ball;

        float width = 60;
        float height = VIEWPORT_HEIGHT / 5f;

        Material material = new Material(
                AssetManager.getShader(StaticShader.class),
                Colors.COLOR_BLUE,
                AssetManager.getTexture(TEXTURE_KNIGHT)
        );

        renderable = new Rectangle2D(
                material,
                (VIEWPORT_WIDTH - width) / 2,
                0,
                width, height
        );

        collider = new BoxCollider(renderable, width, height);
    }

    @Override
    public void update(double deltaTime) {
        reactionTimer += deltaTime;
        if (reactionTimer < reactionDelay) return;
        reactionTimer = 0;

        float ballY = ball.renderable.getPosition().y;
        float targetY = ballY + (float) (Math.random() * jitterAmount - jitterAmount / 2);

        float paddleY = renderable.getPosition().y;
        float dy = targetY - paddleY;

        if (Math.abs(dy) > 3) {
            float direction = Math.signum(dy);
            renderable.translate(0, (float) (direction * speed * deltaTime));
        }

        clampToBounds();
    }

    private void clampToBounds() {
        float limitY = (VIEWPORT_HEIGHT - renderable.getScale().y) / 2f;
        float y = Math.max(-limitY, Math.min(renderable.getPosition().y, limitY));

        renderable.setPosition(renderable.getPosition().x, y);
    }

    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public void destroy() {

    }
}
