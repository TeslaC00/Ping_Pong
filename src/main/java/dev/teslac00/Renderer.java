package dev.teslac00;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;

public class Renderer {
    private int vaoId, vboId, iboId;
    private int indicesLength;

    public void init() {

//        Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        vaoId = glGenVertexArrays();    // Generate a vertex array for VAO (layout)
        glBindVertexArray(vaoId);   // Bind VAO, it will start tracking all VBO and IBO/EBO

        vboId = glGenBuffers(); // Generate a vertex buffer to store vertex
        glBindBuffer(GL_ARRAY_BUFFER, vboId);   // Bind the buffer to GL state to store data

        iboId = glGenBuffers(); // Generate an index buffer to store indices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);   // Bind the indices to GL state to store indices

//        NOTE: this binds VAO with current VBO
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
    }

    public void load(float[] vertices, int[] indices) {

        indicesLength = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vboId);   // Bind the buffer to GL state to store data
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);   // Bind the indices to GL state to store indices

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);   // Put the vertex data in buffer
        vertexBuffer.flip();  // Reset the pointer at start and lock the buffer size

        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);  // Give the vertex data with its usage type

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);    // unbind the buffer for cleanup
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);    // unbind the indices for cleanup
    }

    public void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glBindVertexArray(vaoId);   // bind the VAO to use in this frame
//            glDrawArrays(GL_TRIANGLES, 0, 3); // draw call to ask GL to draw these values
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
        glDrawElements(GL_TRIANGLES, indicesLength, GL_UNSIGNED_INT, 0);   // draw elements using ibo
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);   // unbind the VAO for cleanup after draw call
    }

    public void destroy() {
        glDisableVertexAttribArray(0);  // Disable vertex attributes for cleanup
        glBindBuffer(GL_ARRAY_BUFFER, 0);   // unbind vertex buffer for cleanup
        glDeleteBuffers(vboId); // delete vertex buffer after unbinding

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);    // unbind indices buffer for cleanup
        glDeleteBuffers(iboId); // delete indices buffer after unbinding

        glBindVertexArray(0);   // unbind the VAO for cleanup
        glDeleteVertexArrays(vaoId);    // delete the VAO after unbinding
    }
}
