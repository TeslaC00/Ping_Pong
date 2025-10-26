package dev.teslac00;

import org.joml.Vector4f;

public record Material(
        int shaderId,
        /* Uniforms */
        Vector4f color
) {
}
