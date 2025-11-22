/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Button
 * Author  : Vikas Kumar
 * Created : 22-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.ui;

import dev.teslac00.entities.Entity;
import dev.teslac00.entities.Text2D;
import dev.teslac00.entities.Transform;
import dev.teslac00.graphics.Font;
import dev.teslac00.input.InputManager;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Button {

    private final Background background;
    private final Text2D text2D;
    private boolean isClicked = false;

    private final List<Entity> entities;

    public Button(String label, Font font, Vector4f backgroundColor,
                  float x, float y, float scaleX, float scaleY, float fontSize
    ) {
        background = new Background(x, y, scaleX, scaleY, backgroundColor);
        text2D = new Text2D(label, font, x, y, fontSize);

        entities = new ArrayList<>();
        entities.add(background);
        entities.add(text2D);
    }

    public boolean isHover() {
        Vector2f mousePosition = InputManager.getMouseWorldPosition();
        Transform transform = background.transform;
        return mousePosition.x >= transform.position.x - (transform.scale.x / 2f) &&
                mousePosition.x <= transform.position.x + (transform.scale.x / 2f) &&
                mousePosition.y >= transform.position.y - (transform.scale.y / 2f) &&
                mousePosition.y <= transform.position.y + (transform.scale.y / 2f);
    }

    public void update(double deltaTime) {
        isClicked = isHover() && InputManager.getMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT);
        if (isClicked)
            text2D.setText("Clicked");
        text2D.update(deltaTime);
    }

    public boolean isClicked() {
        return isClicked;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    //    @Override
    public void destroy() {
        background.destroy();
        text2D.destroy();
        entities.clear();
    }
}
