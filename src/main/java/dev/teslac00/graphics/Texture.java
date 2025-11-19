package dev.teslac00.graphics;

import dev.teslac00.util.FileUtils;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL12C.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13C.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Texture
 * Author  : Vikas Kumar
 * Created : 15-11-2025
 * ---------------------------------------------------------------
 */
public class Texture {

    private final int textureId;

    public Texture(String path) {
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);   // width of the image
            IntBuffer h = stack.mallocInt(1);   // height of the image
            IntBuffer bpp = stack.mallocInt(1); // Bits(channels) per pixel

            stbi_set_flip_vertically_on_load(true);
            ByteBuffer image = stbi_load_from_memory(
                    FileUtils.loadResourceAsByteBuffer(path),   // Resource as byte buffer to load
                    w,  // memory to write width
                    h,  // memory to write height
                    bpp, // memory to write bits per pixel
                    4   // desired channels
            );

            if (image == null)
                throw new RuntimeException("Failed to load texture: %s - %s".formatted(path, stbi_failure_reason()));

            glTexImage2D(
                    GL_TEXTURE_2D,  // Type of texture
                    0,              // Level of texture
                    GL_RGB8,        // Internal Image format
                    w.get(),        // Width of the texture
                    h.get(),        // Height of the texture
                    0,              // Border
                    GL_RGBA,        // Format of the texture
                    GL_UNSIGNED_BYTE,   // Type of data in texture
                    image           // The texture data
            );
            stbi_image_free(image);
        }

        unbind();
    }

    public void bind(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE2, 0);
    }

    public void destroy() {
        glDeleteTextures(textureId);
    }

    public int getTextureId() {
        return textureId;
    }
}
