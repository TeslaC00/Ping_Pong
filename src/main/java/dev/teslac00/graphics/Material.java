package dev.teslac00.graphics;

import org.joml.Vector4f;

public record Material(
        ShaderProgram shader,
        /* Uniforms */
        Vector4f color
) {
}
