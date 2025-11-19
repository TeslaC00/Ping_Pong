/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : RenderableEntity
 * Author  : Vikas Kumar
 * Created : 19-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.entities;

import dev.teslac00.graphics.RenderableObject;

public abstract class RenderableEntity extends Entity {

    protected RenderableObject renderable;

    public RenderableObject getRenderable() {
        return renderable;
    }
}
