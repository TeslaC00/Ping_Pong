/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : MouseInput
 * Author  : Vikas Kumar
 * Created : 22-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput extends GLFWMouseButtonCallback {

    public static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_MIDDLE];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (button < 0 || button > 3) return;
        buttons[button] = action != GLFW_RELEASE;
    }
}
