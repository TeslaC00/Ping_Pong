/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Text
 * Author  : Vikas Kumar
 * Created : 23-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.ui;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Renderer;
import dev.teslac00.graphics.*;
import org.joml.Vector4f;

public class Text extends UIComponent {

    private String text;
    private final Font font;
    private boolean isDirty = false;
    private float width, height;

    public Text(String text, Font font, float x, float y, float size, Vector4f color) {
        super(x, y);
        this.text = text;
        this.font = font;

//        TODO: change font mesh to accept size and resize using unit size
        TextMesh textMesh = MeshFactory.createTextMesh(text, font, true);
        setDimension(1, 1);
        width = textMesh.width();
        height = textMesh.height();
        mesh = textMesh.mesh();
        material = new Material(AssetManager.getShader(MSDFShader.class), color, font.getTexture());
    }

    @Override
    public void update(double deltaTime) {
        if (isDirty) {
            TextMesh textMesh = MeshFactory.createTextMesh(text, font, true);
            mesh = textMesh.mesh();
            width = textMesh.width();
            height = textMesh.height();
            setDimension(getWidth(), getHeight());
            isDirty = false;
        }

    }

    @Override
    public void render(Renderer renderer) {
        renderer.submit(this);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setText(String text) {
        if (!this.text.equals(text)) {
            this.text = text;
            isDirty = true;
        }
    }

    @Override
    public void destroy() {

    }
}
