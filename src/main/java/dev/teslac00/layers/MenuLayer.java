package dev.teslac00.layers;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Engine;
import dev.teslac00.input.Event;
import dev.teslac00.input.InputManager;
import dev.teslac00.ui.Background;
import dev.teslac00.ui.Button;
import dev.teslac00.ui.Text;
import dev.teslac00.util.Colors;

import static dev.teslac00.util.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.util.Constants.VIEWPORT_WIDTH;

public class MenuLayer extends Layer {

    private final Background background;
    private final Button button;
    private final Button quitButton;
    private final Text text;
    private final Text mousePosition;

    public MenuLayer(Engine engine) {
        super(engine);
        background = new Background(Colors.BLACK);
        float buttonWidth = 100, buttonHeight = 70;
        button = new Button(
                "Start", AssetManager.getFontChela(), Colors.GREEN,
                VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f,
                buttonWidth, buttonHeight, 42
        );

        quitButton = new Button(
                "Quit", AssetManager.getFontChela(), Colors.RED,
                VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f + 100,
                buttonWidth, buttonHeight, 42
        );

        text = new Text("Menu", AssetManager.getFontChela(), 0, 0, 42, Colors.WHITE);
        text.setPosition((VIEWPORT_WIDTH - text.getWidth()) / 2, 200);
        mousePosition = new Text("", AssetManager.getFontChela(), 0, 0, 21, Colors.WHITE);
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
        mousePosition.setPosition(VIEWPORT_WIDTH - mousePosition.getWidth(), 0);

        text.update(deltaTime);
        button.update(deltaTime);
        if (button.isClicked()) {
            isSuspended = true;
            engine.unPause();
            engine.pushLayer(new GameLayer(engine));
        }

        quitButton.update(deltaTime);
        if (quitButton.isClicked()) {
            System.out.println("Quitting game now");
            engine.close();
        }
    }

    @Override
    public void onRender() {
        engine.getRenderer().submit(background);
        mousePosition.render(engine.getRenderer());
        text.render(engine.getRenderer());
        button.render(engine.getRenderer());
        quitButton.render(engine.getRenderer());
    }

    @Override
    public void onDetach() {
        background.destroy();
        button.destroy();
        quitButton.destroy();
    }

    @Override
    public boolean onEvent(Event event) {
        return false;
    }
}
