package dev.teslac00.core;

import dev.teslac00.graphics.RenderableObject;
import dev.teslac00.graphics.ShaderProgram;
import dev.teslac00.util.Constants;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static dev.teslac00.util.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

/**
 * Handles all OpenGL rendering operations in the engine.
 * <p>
 * The {@code Renderer} maintains a queue of {@link RenderableObject}s and
 * draws them each frame using their associated meshes, materials, and shaders.
 * It also manages a 2D orthographic projection and basic OpenGL state setup.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Setup OpenGL context state (blending, projection, clear color)</li>
 *     <li>Manage per-frame render queue</li>
 *     <li>Submit and draw renderable objects</li>
 *     <li>Bind shaders and send uniforms (transform, projection, color)</li>
 * </ul>
 *
 * <p>
 * This renderer uses a simple immediate-mode batching style — all renderables
 * submitted via {@link #renderModel(RenderableObject)} are drawn in the same frame.
 * </p>
 */
public final class Renderer {

    private final ArrayList<RenderableObject> renderQueue = new ArrayList<>();
    private static FloatBuffer projBuffer; // orthographic projection

    // ---------------------------------------------------------------------
    // Initialization
    // ---------------------------------------------------------------------

    /**
     * Initializes the renderer by setting up the orthographic projection and OpenGL state.
     * <p>
     * Enables alpha blending and defines the viewport projection matrix
     * based on {@link Constants#VIEWPORT_WIDTH} and {@link Constants#VIEWPORT_HEIGHT}.
     * </p>
     */
    public void init() {
        projBuffer = BufferUtils.createFloatBuffer(16);

        // Orthographic projection covering the entire viewport
        Matrix4f proj = new Matrix4f().ortho(
                -VIEWPORT_WIDTH / 2.0f, VIEWPORT_WIDTH / 2.0f,
                -VIEWPORT_HEIGHT / 2.0f, VIEWPORT_HEIGHT / 2.0f,
                -1, 1
        );
        proj.get(projBuffer).rewind();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);   // Set the clear color
    }

    // ---------------------------------------------------------------------
    // Frame Rendering
    // ---------------------------------------------------------------------

    /**
     * Queues a renderable object to be drawn in the next frame.
     *
     * @param object The renderable object to add to the draw queue.
     */
    public void renderModel(RenderableObject object) {
        renderQueue.add(object);
    }

    /**
     * Clears the frame buffer before rendering.
     * <p>
     * Typically called once per frame before any draw calls are issued.
     * </p>
     */
    public void prepare() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        // TODO: Optional — add shader/material sorting here to reduce state changes.
    }

    /**
     * Executes all queued draw calls for the current frame.
     * <p>
     * Each {@link RenderableObject} is drawn using its material’s shader, color,
     * and transform. The render queue is cleared automatically at the end of the frame.
     * </p>
     */
    public void render() {

        for (RenderableObject model : renderQueue) {
            ShaderProgram shader = model.getMaterial().shader();

            shader.start();
            shader.loadUniforms(model);

            glBindVertexArray(model.getMesh().getVaoId());   // bind the VAO to use in this frame
            glDrawElements(GL_TRIANGLES, model.getMesh().getIndicesCount(), GL_UNSIGNED_INT, 0);   // draw elements using ibo
            glBindVertexArray(0);   // unbind the VAO for cleanup after draw call

            shader.stop();
        }

        renderQueue.clear();
    }

    // ---------------------------------------------------------------------
    // Cleanup
    // ---------------------------------------------------------------------

    /**
     * Clears the render queue and frees transient rendering resources.
     * <p>
     * Should be called on engine shutdown.
     * </p>
     */
    public void destroy() {
        renderQueue.clear();
    }

    public static FloatBuffer getProjBuffer() {
        return projBuffer;
    }
}
