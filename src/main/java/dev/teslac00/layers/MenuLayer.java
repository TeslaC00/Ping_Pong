package dev.teslac00.layers;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Engine;
import dev.teslac00.entities.Entity;
import dev.teslac00.graphics.Material;
import dev.teslac00.graphics.Rectangle2D;
import dev.teslac00.graphics.StaticShader;
import dev.teslac00.input.Event;
import dev.teslac00.util.Colors;

import static dev.teslac00.util.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MenuLayer extends Layer {

    private final Entity background;

    public MenuLayer(Engine engine) {
        super(engine);
//        TODO: use UI entities to render this
        background = new Entity(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT) {
            @Override
            public void update(double deltaTime) {
            }

            @Override
            public void destroy() {
            }
        };

        Material backgroundMaterial = new Material(AssetManager.getShader(StaticShader.class), Colors.BLACK);
        background.renderable = new Rectangle2D(backgroundMaterial);
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
        engine.getRenderer().submit(background);
    }

    @Override
    public void onDetach() {
        background.destroy();
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
