/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : UIComponent
 * Author  : Vikas Kumar
 * Created : 23-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.ui;

import dev.teslac00.core.Renderer;
import dev.teslac00.entities.Transform;
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.Mesh;
import org.joml.Matrix4f;

public abstract class UIComponent {

    protected Transform transform = new Transform();
    protected boolean isVisible = true;

    protected Mesh mesh;
    protected Material material;

    public UIComponent(float x, float y) {
        transform.position.set(x, y, 0);
    }

    public UIComponent(float x, float y, float width, float height) {
        transform.position.set(x, y, 0);
        transform.scale.set(width, height, 1);
    }

    public abstract void update(double deltaTime);

    public abstract void render(Renderer renderer);

    public abstract void destroy();

    public Matrix4f getTransform() {
        return transform.getTransform();
    }

    public float getX() {
        return transform.position.x;
    }

    public float getY() {
        return transform.position.y;
    }

    public float getWidth() {
        return transform.scale.x;
    }

    public float getHeight() {
        return transform.scale.y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Material getMaterial() {
        return material;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setPosition(float x, float y) {
        this.transform.position.set(x, y, 0);
    }

    public void setDimension(float width, float height) {
        this.transform.scale.set(width, height, 1);
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
