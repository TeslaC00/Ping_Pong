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
import org.joml.Vector4f;

public class Text2D extends RenderableObject {

    private String text;
    private final Font font;
    private boolean isDirty = false;
//    private final float width, height;

    public Text2D(String text, Font font, float x, float y, float scale) {
        super(
                MeshFactory.createTextMesh(text, font),
                new Material(new MSDFShader(), Colors.COLOR_WHITE, font.getTexture()),
                x, y, scale, scale
        );

        this.text = text;
        this.font = font;
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
            mesh = MeshFactory.createTextMesh(text, font);
            isDirty = false;
        }
    }

    public void setColor(Vector4f color) {
//        TODO: add implementation when use this method
        System.out.printf("Color change requested for material: %s%n", color.toString());
    }

    public void destroy() {
        if (mesh != null) mesh.destroy();
    }
}
