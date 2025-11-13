package dev.teslac00.layers;

import dev.teslac00.core.Engine;
import dev.teslac00.graphics.*;
import dev.teslac00.input.Event;
import org.joml.Vector4f;

import static dev.teslac00.core.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.core.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MenuLayer extends Layer {

    private final StaticShader staticShader;
    private final Mesh rectangleMesh;
    private final Rectangle2D background;

    public MenuLayer(Engine engine) {
        super(engine);
        staticShader = new StaticShader();
        rectangleMesh = MeshFactory.createRectangle();
        Material backgroundMaterial = new Material(staticShader, new Vector4f(0, 0, 0, 1));

        background = new Rectangle2D(rectangleMesh, backgroundMaterial, 0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    @Override
    public String name() {
        return "Menu Layer";
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onUpdate(double deltaTime) {

    }

    @Override
    public void onRender() {
        engine.getRenderer().renderModel(background);
    }

    @Override
    public void onDetach() {
        rectangleMesh.destroy();
        staticShader.destroy();
    }

    @Override
    public boolean onEvent(Event event) {
        if (event.key() == GLFW_KEY_TAB && event.action() == GLFW_PRESS) {
            engine.pushLayer(new GameLayer(engine));
            return true;
        }
        return false;
    }
}
