/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : VertexAttribute
 * Author  : Vikas Kumar
 * Created : 17-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

/**
 * Vertex Attribute
 *
 * @param index
 * @param count
 * @param type
 * @param normalized
 * @param offset
 */
public record VertexAttribute(
        int index,  // index of the attribute in vertex
        int count,   // number of elements in this attribute
        int type,   // type of variable this attribute holds
        boolean normalized,  // is the value normalized
        int offset  // Offset of this attribute from start of vertex
) {
}
