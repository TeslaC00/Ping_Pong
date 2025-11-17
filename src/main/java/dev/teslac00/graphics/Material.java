package dev.teslac00.graphics;

import org.joml.Vector4f;

/**
 * Defines the visual appearance of an object, holds all values related to visuals
 *
 * @param shader - Shader program ID
 * @param color  - The color uniform it's shader uses
 */
public record Material(
        ShaderProgram shader,
        /* Uniforms */
        Vector4f color,

        Texture texture
) {

    public Material(ShaderProgram shader, Vector4f color) {
        this(shader, color, null);
    }
}
