package dev.teslac00.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public final class InputManager {

    private static long window;
    private final static ArrayList<Event> eventQueue = new ArrayList<>();

    public InputManager(long window) {
        InputManager.window = window;
        GLFWKeyCallback keyCallback = new KeyboardInput();
        glfwSetKeyCallback(window, keyCallback);
    }

    public static boolean getKeyPressed(int key) {
        return KeyboardInput.keys[key];
    }

    public static ArrayList<Event> getEventQueue() {
        return eventQueue;
    }

    public static void pushEvent(Event event) {
        eventQueue.add(event);
    }

    //    TODO: This is already done in DisplayManager so maybe remove this
    public void destroy() {
        glfwFreeCallbacks(window);
    }
}
