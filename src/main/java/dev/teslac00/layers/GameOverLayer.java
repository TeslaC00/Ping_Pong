/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : GameOverLayer
 * Author  : Vikas Kumar
 * Created : 21-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.layers;

import dev.teslac00.core.Engine;
import dev.teslac00.entities.Text2D;
import dev.teslac00.graphics.Font;
import dev.teslac00.input.Event;
import dev.teslac00.util.Constants;

public class GameOverLayer extends Layer {

    private final Text2D gameOverText;

    /**
     * Constructs a new {@code Layer} associated with the given engine.
     *
     * @param engine The engine instance this layer interacts with.
     */
    public GameOverLayer(Engine engine, String text) {
        super(engine);
        Font font = new Font(Constants.FONT_PATRICK_HAND_ATLAS, Constants.FONT_PATRICK_HAND_JSON);
        gameOverText = new Text2D(text, font, 0, 0, 2);
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

    }

    @Override
    public void onRender() {
        engine.getRenderer().submit(gameOverText);
    }

    @Override
    public void onDetach() {
        gameOverText.destroy();
    }

    @Override
    public boolean onEvent(Event event) {
        return false;
    }
}
