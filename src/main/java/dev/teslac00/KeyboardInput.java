package dev.teslac00;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardInput extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[GLFW_KEY_LAST];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }
}
