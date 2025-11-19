/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Text2D
 * Author  : Vikas Kumar
 * Created : 18-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.core;

import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.MeshFactory;
import dev.teslac00.graphics.RenderableObject;
import dev.teslac00.graphics.TextMesh;
import org.joml.Vector4f;

public class Text2D extends RenderableObject {

    private String text;
    private final Font font;
    private boolean isDirty = false;
    private float width, height;

    public Text2D(String text, Font font, float x, float y, float scale) {
//        Note: can use private constructor with static create function
        super(
                MeshFactory.createTextMesh(null, font).mesh(),
                new Material(new MSDFShader(), Colors.COLOR_WHITE, font.getTexture()),
                x, y, scale, scale
        );

        this.text = text;
        this.font = font;
        this.width = 0;
        this.height = 0;
    }

    public void setText(String text) {
        if (!this.text.equals(text)) {
            this.text = text;
            isDirty = true;
        }
    }

    public void update() {
        if (isDirty) {
            mesh.destroy();
            TextMesh textMesh = MeshFactory.createTextMesh(text, font);
            mesh = textMesh.mesh();
            width = textMesh.width();
            height = textMesh.height();
            isDirty = false;
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setColor(Vector4f color) {
//        TODO: add implementation when use this method
        System.out.printf("Color change requested for material: %s%n", color.toString());
    }

    public void destroy() {
        if (mesh != null) mesh.destroy();
    }
}
