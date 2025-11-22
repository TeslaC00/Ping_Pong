package dev.teslac00.input;

import dev.teslac00.util.Constants;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public final class InputManager {

    private static long window;
    private final static ArrayList<Event> eventQueue = new ArrayList<>();

    public InputManager(long window) {
        InputManager.window = window;
        GLFWKeyCallback keyCallback = new KeyboardInput();
        GLFWCursorPosCallback mousePositionCallback = new MousePosition();
        GLFWMouseButtonCallback mouseButtonCallback = new MouseInput();
        glfwSetKeyCallback(window, keyCallback);
        glfwSetCursorPosCallback(window, mousePositionCallback);
        glfwSetMouseButtonCallback(window, mouseButtonCallback);
    }

    public static boolean getKeyPressed(int key) {
        return KeyboardInput.keys[key];
    }

    public static Vector2f getMousePosition() {
        return MousePosition.position;
    }

    public static boolean getMouseButtonPressed(int button) {
        return MouseInput.buttons[button];
    }

    public static Vector2f getMouseWorldPosition() {
        Vector2f position = MousePosition.position;
        return new Vector2f(
                position.x - (Constants.VIEWPORT_WIDTH / 2f),
                (Constants.VIEWPORT_HEIGHT / 2f) - position.y
        );
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
