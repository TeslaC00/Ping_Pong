package dev.teslac00;


import dev.teslac00.core.Engine;

public final class Main {

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.init();
        while (engine.shouldRun())
            engine.run();
//        TODO: don't exit engine immediately when close is requested
        engine.destroy();
    }
}