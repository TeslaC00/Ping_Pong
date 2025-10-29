package dev.teslac00;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class InputManager {

    private static long window;

    public static void init(long window) {
        InputManager.window = window;
    }

    public static boolean getKeyPressed(int key) {
        return glfwGetKey(InputManager.window, key) == 1;
    }
}
