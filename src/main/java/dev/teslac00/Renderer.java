package dev.teslac00;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;

public class Renderer {
    private final ArrayList<RenderableObject> models = new ArrayList<>();
    private final FloatBuffer transformBuffer = BufferUtils.createFloatBuffer(16);
    private FloatBuffer projBuffer;

    public void init(int width, int height) {

        projBuffer = BufferUtils.createFloatBuffer(16);
        Matrix4f proj = new Matrix4f().ortho(0, width, height, 0, -1, 1);
        proj.get(projBuffer).rewind();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);   // Set the clear color
    }

    public void loadModel(RenderableObject object) {
        models.add(object);
    }

    public void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        for (RenderableObject model : models) {
            Vector4f color = model.getMaterial().color();
            int shaderId = model.getMaterial().shaderId();
            model.getTransform().get(transformBuffer).rewind();

            bindShader(shaderId);   // bind and use the shader for current draw call
            glUniform4f(getUniformLocation(shaderId, "u_color"), color.x, color.y, color.z, color.w);
            glUniformMatrix4fv(getUniformLocation(shaderId, "u_proj"), false, projBuffer);
            glUniformMatrix4fv(getUniformLocation(shaderId, "u_trans"), false, transformBuffer);
            glBindVertexArray(model.getMesh().getVaoId());   // bind the VAO to use in this frame
            glDrawElements(GL_TRIANGLES, model.getMesh().getIndicesCount(), GL_UNSIGNED_INT, 0);   // draw elements using ibo

            glBindVertexArray(0);   // unbind the VAO for cleanup after draw call
            unbindShader(); // unbind the shader program
        }

    }

    public void destroy() {
        models.clear();
    }

    private int getUniformLocation(int shaderId, String uniformName) {
        int uniformLocation = glGetUniformLocation(shaderId, uniformName);
        if (uniformLocation == -1)
            throw new RuntimeException("Uniform u_color not found in shader");
        return uniformLocation;
    }

    private void bindShader(int shaderId) {
        glUseProgram(shaderId);
    }

    private void unbindShader() {
        glUseProgram(0);
    }
}
