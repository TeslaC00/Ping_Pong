package dev.teslac00;

import org.joml.Vector4f;

public record Model(int vaoId, int vboId, int iboId, int indicesCount, Vector4f u_color) {
}
