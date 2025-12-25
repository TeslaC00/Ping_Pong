/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Button
 * Author  : Vikas Kumar
 * Created : 22-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.ui;

import dev.teslac00.core.Renderer;
import dev.teslac00.graphics.Font;
import dev.teslac00.input.InputManager;
import dev.teslac00.util.Colors;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Button extends UIComponent {

    private final UIBackground background;
    private final Text text;

    private boolean isClicked = false;

    //    NOTE: Button origin is in the center with pivot at the top left
    //    Text origin is in the top left
    public Button(String label, Font font, Vector4f backgroundColor,
                  float x, float y, float scaleX, float scaleY, float fontSize
    ) {
        super(x, y);
//        TODO: Add hover color
        background = new UIBackground(x, y, scaleX, scaleY, backgroundColor);
        System.out.printf("Button X:%f, Y:%f, Width:%f, Height:%f%n", x, y, scaleX, scaleY);
        text = new Text(label, font, 0, 0, fontSize, Colors.WHITE);
    }

    public boolean isHover() {
        Vector2f mousePosition = InputManager.getMousePosition();
        return mousePosition.x >= background.getX() - background.getWidth() / 2f &&
                mousePosition.x <= background.getX() + background.getWidth() / 2f &&
                mousePosition.y >= background.getY() - background.getHeight() / 2f &&
                mousePosition.y <= background.getY() + background.getHeight() / 2f;
    }

    public void update(double deltaTime) {
        text.update(deltaTime);

        text.setPosition(
                background.getX() - text.getWidth() / 2f,
                background.getY() - text.getHeight() / 2f
        );

        isClicked = isHover() && InputManager.getMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT);
        if (isHover())
            System.out.println("Button hovering");
    }

    @Override
    public void render(Renderer renderer) {
        background.render(renderer);
        text.render(renderer);
    }

    public boolean isClicked() {
        return isClicked;
    }

    @Override
    public void destroy() {
        text.destroy();
    }
}
