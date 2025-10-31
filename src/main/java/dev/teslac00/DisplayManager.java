package dev.teslac00;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class DisplayManager {

    private final int width;
    private final int height;
    private final String title;
    private long window;

    public DisplayManager(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public long init() {
//        Setup an error callback. The default implementation will print the error message in System.err
        GLFWErrorCallback.createPrint(System.err).set();

//        Initialize GLFW. Most GLFW functions will not work before doing this
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

//        Configure GLFW
        glfwDefaultWindowHints();   // optional, current window hints are already default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);    // the window will stay hidden after creation
//        TODO: understand how to make window resizable first with GLViewport then make it true
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);   // the window will be resizable

//        Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

//        Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
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

        glViewport(0, 0, width, height);

        return window;
    }

    public void update() {

        glfwSwapBuffers(window);    // swap the color buffers

//            Poll for window events. The key callback above will only be invoked during this call.
        glfwPollEvents();
    }

    public void destroy() {

//        Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

//        Terminate GLFW and free the error callbacks
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
