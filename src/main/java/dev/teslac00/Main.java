package dev.teslac00;


import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;

public class Main {

    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        DisplayManager displayManager = new DisplayManager(1280, 720, "Ping Pong");

        window = displayManager.init();
        loop();
        displayManager.destroy();
    }

    private void loop() {
//        This line is critical for LWJGL's interoperation with GLFW's
//        OpenGL context, or any context that is managed externally.
//        LWJGL detects the context that is current in the current thread,
//        create the GLCapabilities instance and makes the OpenGL bindings available for use.
        GL.createCapabilities();

//        glViewport();

//        Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        int vaoId = glGenVertexArrays();    // Generate a vertex array for VAO (layout)
        glBindVertexArray(vaoId);   // Bind VAO, it will start tracking all VBO and IBO/EBO

        int vboId = glGenBuffers(); // Generate a vertex buffer to store vertex
        glBindBuffer(GL_ARRAY_BUFFER, vboId);   // Bind the buffer to GL state to store data

        int iboId = glGenBuffers(); // Generate an index buffer to store indices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);

        float[] vertices = {
                0.5f, 0.5f,
                -0.5f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f
        };

        int[] indices = {
                0, 1, 2,    // Top Left triangle
                2, 3, 0 // Bottom Right triangle
        };

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);   // Put the vertex data in buffer
        vertexBuffer.flip();  // Reset the pointer at start and lock the buffer size

        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);  // Give the vertex data with its usage type

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(  // Define the attribute of the vertex, vertex can have many attributes
                0,  // Index of the attribute in vertex
                2,  // number of element in attribute
                GL_FLOAT,   // the data type of attribute
                false,  // is attribute normalized
                2 * Float.BYTES,   // the size of the vertex
                0   // the offset of attribute from start of vertex
        );
        glEnableVertexAttribArray(0);   // Bind the vertex attribute with current vertex

        glBindVertexArray(0);   // unbind the VAO for cleanup, it will finalize and will not record more setups
        glBindBuffer(GL_ARRAY_BUFFER, 0);    // unbind the buffer for cleanup
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);    // unbind the indices for cleanup

//        Run the rendering loop until the user has attempted to close the window or press ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glBindVertexArray(vaoId);   // bind the VAO to use in this frame
//            glDrawArrays(GL_TRIANGLES, 0, 3); // draw call to ask GL to draw these values
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);   // draw elements using ibo
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            glBindVertexArray(0);   // unbind the VAO for cleanup after draw call

            glfwSwapBuffers(window);    // swap the color buffers

//            Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }

        glDisableVertexAttribArray(0);  // Disable vertex attributes for cleanup
        glBindBuffer(GL_ARRAY_BUFFER, 0);   // unbind vertex buffer for cleanup
        glDeleteBuffers(vboId); // delete vertex buffer after unbinding

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);    // unbind indices buffer for cleanup
        glDeleteBuffers(iboId); // delete indices buffer after unbinding

        glBindVertexArray(0);   // unbind the VAO for cleanup
        glDeleteVertexArrays(vaoId);    // delete the VAO after unbinding
    }

    public static void main(String[] args) {
        new Main().run();
    }
}