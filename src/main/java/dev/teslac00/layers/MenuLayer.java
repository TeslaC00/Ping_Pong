package dev.teslac00.layers;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Engine;
import dev.teslac00.entities.Entity;
import dev.teslac00.input.Event;
import dev.teslac00.input.InputManager;
import dev.teslac00.ui.Background;
import dev.teslac00.ui.Button;
import dev.teslac00.ui.Text;
import dev.teslac00.ui.UIBackground;
import dev.teslac00.util.Colors;

public class MenuLayer extends Layer {

    private final Background background;
    private final Button button;
    private final Text text;
    private final Text mousePosition;

    private final UIBackground uiBackground;

    public MenuLayer(Engine engine) {
        super(engine);
        background = new Background(Colors.BLACK);
        button = new Button(
                "Start", AssetManager.getFontChela(), Colors.GREEN,
                0, 60, 100, 70, 1
//                TODO: fix text centering in button
        );

        text = new Text("Menu", AssetManager.getFontChela(), 100, 50, 1, Colors.WHITE);
        mousePosition = new Text("100, 200", AssetManager.getFontChela(), 300, 200, 0.5f, Colors.WHITE);

        uiBackground = new UIBackground(20, 10, 50, 10, Colors.BLUE);
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
        text.update(deltaTime);
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
        uiBackground.render(engine.getRenderer());
        text.render(engine.getRenderer());
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
