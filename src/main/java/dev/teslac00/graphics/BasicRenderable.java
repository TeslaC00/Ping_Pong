/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : BasicRenderable
 * Author  : Vikas Kumar
 * Created : 19-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

public class BasicRenderable extends RenderableObject {

    public BasicRenderable(Mesh mesh, Material material, float x, float y, float scaleX, float scaleY) {
        super(mesh, material, x, y, scaleX, scaleY);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void destroy() {
        mesh.destroy();
    }

}
