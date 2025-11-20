/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : BasicRenderable
 * Author  : Vikas Kumar
 * Created : 19-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

public class BasicRenderable extends Renderable {

    public BasicRenderable(Mesh mesh, Material material) {
        super(mesh, material);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void destroy() {
        mesh.destroy();
    }

}
