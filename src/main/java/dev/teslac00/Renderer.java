package dev.teslac00;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;

public class Renderer {
    private final ArrayList<Model> models = new ArrayList<>();
    private ShaderProgram shader;
    private int uniformLocation;

    public void init() {

        shader = new StaticShader();

        uniformLocation = glGetUniformLocation(shader.getProgramId(), "u_color");
        if (uniformLocation == -1)
            throw new RuntimeException("Uniform u_color not found in shader");

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);   // Set the clear color
    }

    public void loadModel(float[] vertices, int[] indices, Vector4f color) {

        int vaoId = glGenVertexArrays();    // Generate a vertex array for VAO (layout)
        glBindVertexArray(vaoId);   // Bind VAO, it will start tracking all VBO and IBO/EBO

        int vboId = glGenBuffers(); // Generate a vertex buffer to store vertex
        glBindBuffer(GL_ARRAY_BUFFER, vboId);   // Bind the buffer to GL state to store data

        int iboId = glGenBuffers(); // Generate an index buffer to store indices
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

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);   // Put the vertex data in buffer
        vertexBuffer.flip();  // Reset the pointer at start and lock the buffer size

        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);  // Give the vertex data with its usage type

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);    // unbind the buffer for cleanup
        glBindVertexArray(0);   // unbind the VAO for cleanup, it will finalize and will not record more setups
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);    // unbind the indices for cleanup

        models.add(new Model(vaoId, vboId, iboId, indices.length, color));
    }

    public void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        shader.start();

        for (Model model : models) {
            glUniform4f(uniformLocation, model.u_color().x, model.u_color().y, model.u_color().z, model.u_color().w);
            glBindVertexArray(model.vaoId());   // bind the VAO to use in this frame
            glDrawElements(GL_TRIANGLES, model.indicesCount(), GL_UNSIGNED_INT, 0);   // draw elements using ibo
            glBindVertexArray(0);   // unbind the VAO for cleanup after draw call
        }

        shader.stop();
    }

    public void destroy() {

        for (Model model : models) {
            glDeleteBuffers(model.vboId()); // delete vertex buffer after unbinding
            glDeleteBuffers(model.iboId()); // delete indices buffer after unbinding
            glDeleteVertexArrays(model.vaoId());    // delete the VAO after unbinding
        }

        shader.destroy();

        models.clear();
    }
}
