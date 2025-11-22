package dev.teslac00.layers;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Engine;
import dev.teslac00.entities.Entity;
import dev.teslac00.entities.Text2D;
import dev.teslac00.input.Event;
import dev.teslac00.input.InputManager;
import dev.teslac00.ui.Background;
import dev.teslac00.ui.Button;
import dev.teslac00.util.Colors;

public class MenuLayer extends Layer {

    private final Background background;
    private final Button button;
    private final Text2D mousePosition;

    public MenuLayer(Engine engine) {
        super(engine);
        background = new Background(Colors.BLACK);
        button = new Button(
                "Start", AssetManager.getFontChela(), Colors.GREEN,
                0, 60, 100, 70, 1
//                TODO: fix text centering in button
        );
        mousePosition = new Text2D(
                "100, 200", AssetManager.getFontChela(),
                300, 200, 0.5f
        );
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
        mousePosition.setText("%f, %f".formatted(
                InputManager.getMousePosition().x,
                InputManager.getMousePosition().y
        ));
        mousePosition.update(deltaTime);
        button.update(deltaTime);
        if (button.isClicked()) {
            engine.pushLayer(new GameLayer(engine));
        }
    }

    @Override
    public void onRender() {
        engine.getRenderer().submit(background);
        for (Entity entity : button.getEntities())
            engine.getRenderer().submit(entity);
        engine.getRenderer().submit(mousePosition);
    }

    @Override
    public void onDetach() {
        background.destroy();
    }

    @Override
    public boolean onEvent(Event event) {
        return false;
    }
}
