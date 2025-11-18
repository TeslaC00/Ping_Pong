/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Glyph
 * Author  : Vikas Kumar
 * Created : 18-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

public record Glyph(
        float width,
        float height,
        float u0,
        float v0,
        float u1,
        float v1,
        float xOffset,
        float yOffset,
        float xAdvance
) {
}
