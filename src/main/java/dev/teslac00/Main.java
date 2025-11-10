package dev.teslac00;


public final class Main {

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.init();
        while (Engine.shouldRun())
            engine.run();
        engine.destroy();
    }
}