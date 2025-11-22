/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : MouseEvent
 * Author  : Vikas Kumar
 * Created : 22-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePosition extends GLFWCursorPosCallback {

    public static Vector2f position = new Vector2f();

    @Override
    public void invoke(long window, double xPos, double yPos) {
        MousePosition.position.set((float) xPos, (float) yPos);
    }
}
