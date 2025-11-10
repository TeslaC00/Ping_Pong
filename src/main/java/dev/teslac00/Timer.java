package dev.teslac00;

public class Timer {

    private long lastTime;

    public void init() {
        lastTime = System.nanoTime();
    }

    public double getDeltaTime() {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;
        return deltaTime;
    }
}
