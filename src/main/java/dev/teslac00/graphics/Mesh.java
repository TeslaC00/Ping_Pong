package dev.teslac00.graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.*;

/**
 * Defines the geometry or structure of the object. Does not know about the visual appearance
 */
public class Mesh {

    private final int vaoId;
    private final int vboId;
    private final int iboId;
    private final int indicesCount;

    public Mesh(int vaoId, int vboId, int iboId, int indicesCount) {
        this.vaoId = vaoId;
        this.vboId = vboId;
        this.iboId = iboId;
        this.indicesCount = indicesCount;
    }

    public static Mesh loadMesh(float[] vertices, int[] indices, VertexLayout layout) {

        int vaoId = glGenVertexArrays();    // Generate a vertex array for VAO (layout)
        int vboId = glGenBuffers(); // Generate a vertex buffer to store vertex
        int iboId = glGenBuffers(); // Generate an index buffer to store indices

        glBindVertexArray(vaoId);   // Bind VAO, it will start tracking all VBO and IBO/EBO

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices)   // Put the vertex data in buffer
                .flip();  // Reset the pointer at start and lock the buffer count

        glBindBuffer(GL_ARRAY_BUFFER, vboId);   // Bind the buffer to GL state to store data
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);  // Give the vertex data with its usage type

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);   // Bind the indices to GL state to store indices
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        for (VertexAttribute attribute : layout.getAttributes()) {
//        NOTE: this binds VAO with current VBO
            glVertexAttribPointer(  // Define the attribute of the vertex, vertex can have many attributes
                    attribute.index(),  // Index of the attribute in vertex
                    attribute.count(),  // number of element in attribute
                    attribute.type(),   // the data type of attribute
                    attribute.normalized(),  // is attribute normalized
                    layout.getStride(),   // the count of the vertex
                    attribute.offset()   // the offset of attribute from start of vertex
            );
            glEnableVertexAttribArray(attribute.index());   // Bind the vertex attribute with current vertex
        }

//        glBindBuffer(GL_ARRAY_BUFFER, 0);    // unbind the buffer for cleanup
//        glBindVertexArray(0);   // unbind the VAO for cleanup, it will finalize and will not record more setups
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);    // unbind the indices for cleanup

        return new Mesh(vaoId, vboId, iboId, indices.length);
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getIndicesCount() {
        return indicesCount;
    }

    public void destroy() {

        glDeleteBuffers(vboId); // delete vertex buffer after unbinding
        glDeleteBuffers(iboId); // delete indices buffer after unbinding
        glDeleteVertexArrays(vaoId);    // delete the VAO after unbinding

    }

}
