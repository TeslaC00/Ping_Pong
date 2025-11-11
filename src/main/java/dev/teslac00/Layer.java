package dev.teslac00;

public abstract class Layer {

    protected Engine engine;

    protected Layer(Engine engine) {
        this.engine = engine;
    }

    abstract String name();

    abstract void onAttach();

    abstract void onUpdate(double deltaTime);

    abstract void onRender();

    abstract void onDetach();

    abstract boolean onEvent(Event event);

}
