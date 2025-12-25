/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Transform
 * Author  : Vikas Kumar
 * Created : 20-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Transform based on a center. Defaults to center coordinates (0,0), Scale 1 and Rotation 0.
 */
public class Transform {

    public Vector3f position = new Vector3f(0);
    public Vector3f scale = new Vector3f(1);
    public float rotation = 0;

    public Matrix4f getTransform() {
        return new Matrix4f()
                .identity()
                .translate(position)
                .rotateZ(rotation)
                .scale(scale);
    }

    public void translate(float dx, float dy) {
        this.position.add(dx, dy, 0);
    }

}
