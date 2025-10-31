package dev.teslac00;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public final class InputManager {

    private static long window;

    public InputManager(long window) {
        InputManager.window = window;
        GLFWKeyCallback keyCallback = new KeyboardInput();
        glfwSetKeyCallback(window, keyCallback);
    }

    public static boolean getKeyPressed(int key) {
        return KeyboardInput.keys[key];
    }

    //    TODO: This is already done in DisplayManager so maybe remove this
    public void destroy() {
        glfwFreeCallbacks(window);
    }
}
