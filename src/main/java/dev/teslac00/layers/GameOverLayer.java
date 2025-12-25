/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : GameOverLayer
 * Author  : Vikas Kumar
 * Created : 21-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.layers;

import dev.teslac00.core.AssetManager;
import dev.teslac00.core.Engine;
import dev.teslac00.input.Event;
import dev.teslac00.ui.Button;
import dev.teslac00.ui.Text;
import dev.teslac00.util.Colors;
import dev.teslac00.util.Constants;

public class GameOverLayer extends Layer {

    private final Text gameOverText;

    private final Button menuButton;

    /**
     * Constructs a new {@code Layer} associated with the given engine.
     *
     * @param engine The engine instance this layer interacts with.
     */
    public GameOverLayer(Engine engine, String text) {
        super(engine);
        gameOverText = new Text(text, AssetManager.getFontPatrick(), 0, 0, 42, Colors.WHITE);
        gameOverText.setPosition(
                (Constants.VIEWPORT_WIDTH - gameOverText.getWidth()) / 2,
                (Constants.VIEWPORT_HEIGHT - gameOverText.getHeight()) / 2
        );

        float buttonWidth = 250, buttonHeight = 60;
        menuButton = new Button(
                "Go To Menu", AssetManager.getFontPatrick(), Colors.BLUE,
                Constants.VIEWPORT_WIDTH / 2f, Constants.VIEWPORT_HEIGHT / 2f + 70,
                buttonWidth, buttonHeight, 42
        );
    }

    @Override
    public String name() {
        return "Game Over Layer";
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onUpdate(double deltaTime) {
        menuButton.update(deltaTime);

        if (menuButton.isClicked())
            transitionTo(new MenuLayer(engine));
    }

    @Override
    public void onRender() {
        engine.getRenderer().submit(gameOverText);
        menuButton.render(engine.getRenderer());
    }

    @Override
    public void onDetach() {
        gameOverText.destroy();
        menuButton.destroy();
    }

    @Override
    public boolean onEvent(Event event) {
        return false;
    }
}
