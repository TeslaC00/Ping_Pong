package dev.teslac00.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Manages the creation, configuration, and lifecycle of a GLFW window and OpenGL context.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Initializing and terminating GLFW</li>
 *   <li>Creating and centering the window</li>
 *   <li>Setting up OpenGL context and viewport</li>
 *   <li>Handling the display update and buffer swapping per frame</li>
 * </ul>
 * </p>
 * <p>
 * Typically, an instance of {@code DisplayManager} is created by the {@link Engine}
 * during initialization and remains active for the lifetime of the application.
 * </p>
 */
public final class DisplayManager {

    private final int width;
    private final int height;
    private final String title;
    private long window;

    /**
     * Constructs a new {@code DisplayManager} with the given display settings.
     *
     * @param width  The desired window width in pixels.
     * @param height The desired window height in pixels.
     * @param title  The title text shown on the window bar.
     */
    public DisplayManager(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /**
     * Initializes GLFW, creates a window, and sets up the OpenGL context.
     *
     * @return The created window handle, required for further GLFW operations.
     * @throws IllegalStateException if GLFW cannot be initialized.
     * @throws RuntimeException      if the window creation fails.
     */
    public long init() {
//        Setup an error callback. The default implementation will print the error message in System.err
        GLFWErrorCallback.createPrint(System.err).set();

//        Initialize GLFW. Most GLFW functions will not work before doing this
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

//        Configure GLFW default window hints
        glfwDefaultWindowHints();   // optional, current window hints are already default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);    // the window will stay hidden after creation
//        TODO: understand how to make window resizable first with GLViewport then make it true
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);   // the window is not resizable

//        Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

//        Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

//        Get the thread stack and push a new frame
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);  // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

//            Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

//            Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

//            Center the window
            assert vidMode != null;
            glfwSetWindowPos(
                    window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        }   // the stack frame is popped automatically

//        Make the OpenGL context current
        glfwMakeContextCurrent(window);
//        Enable v-sync
        glfwSwapInterval(1);

//        Make the window visible
        glfwShowWindow(window);

//        This line is critical for LWJGL's interoperation with GLFW's
//        OpenGL context, or any context that is managed externally.
//        LWJGL detects the context that is current in the current thread,
//        create the GLCapabilities instance and makes the OpenGL bindings available for use.
        GL.createCapabilities();

//        Set initial viewport
        glViewport(0, 0, width, height);

        return window;
    }

    /**
     * Swaps the front and back buffers and polls window events.
     * <p>
     * Should be called once per frame after rendering.
     * </p>
     */
    public void update() {

        glfwSwapBuffers(window);    // swap the color buffers

//            Poll for window events. The key callback above will only be invoked during this call.
        glfwPollEvents();
    }

    /**
     * Destroys the window and terminates GLFW.
     * <p>
     * Must be called once before program exit to release native resources.
     * </p>
     */
    public void destroy() {

//        Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

//        Terminate GLFW and free the error callbacks
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
