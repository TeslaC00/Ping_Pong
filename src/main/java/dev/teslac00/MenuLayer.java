package dev.teslac00;

import org.joml.Vector4f;

import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MenuLayer extends Layer {

    private final StaticShader staticShader;
    private final Mesh rectangleMesh;
    private final Rectangle2D background;

    protected MenuLayer(Engine engine) {
        super(engine);
        staticShader = new StaticShader();
        rectangleMesh = MeshFactory.createRectangle();
        Material backgroundMaterial = new Material(staticShader.getProgramId(), new Vector4f(0, 0, 0, 0.5f));

        background = new Rectangle2D(rectangleMesh, backgroundMaterial, 0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    @Override
    void onAttach() {

    }

    @Override
    void onUpdate(double deltaTime) {

    }

    @Override
    void onRender() {
        engine.getRenderer().renderModel(background);
    }

    @Override
    void onDetach() {
        rectangleMesh.destroy();
        staticShader.destroy();
    }

    @Override
    boolean onEvent(Event event) {
        if (event.key() == GLFW_KEY_TAB && event.action() == GLFW_PRESS) {
            engine.pushLayer(new GameLayer(engine));
            return true;
        }
        return false;
    }
}
