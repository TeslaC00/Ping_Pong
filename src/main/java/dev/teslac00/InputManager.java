package dev.teslac00;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private static long window;

    public InputManager(long window) {
        InputManager.window = window;
        GLFWKeyCallback keyCallback = new KeyboardInput();
        glfwSetKeyCallback(window, keyCallback);
    }

    public static boolean getKeyPressed(int key) {
        return KeyboardInput.keys[key];
    }

    public void destroy() {
        glfwFreeCallbacks(window);
    }
}
