package dev.teslac00;

import static dev.teslac00.Colors.*;
import static dev.teslac00.Constants.*;
import static dev.teslac00.Constants.VIEWPORT_HEIGHT;
import static dev.teslac00.Constants.VIEWPORT_WIDTH;
import static org.lwjgl.glfw.GLFW.*;

public class GameLayer extends Layer {

    //    Shader & Meshes
    private final StaticShader staticShader;
    private final Mesh rectangleMesh;
    private final Mesh circleMesh;

    //    Entities
    private final Rectangle2D greenRect;
    private final Rectangle2D blueRect;
    private final Circle2D redCircle;

    private final float rectWidth = 60.0f, rectHeight = VIEWPORT_HEIGHT / 5.0f, radius = 30f;

    public GameLayer(Engine engine) {
        super(engine);
        staticShader = new StaticShader();
        Material greenMaterial = new Material(staticShader.getProgramId(), COLOR_GREEN);
        Material blueMaterial = new Material(staticShader.getProgramId(), COLOR_BLUE);
        Material redMaterial = new Material(staticShader.getProgramId(), COLOR_RED);

        rectangleMesh = MeshFactory.createRectangle();
        circleMesh = MeshFactory.createCircle(CIRCLE_MESH_DEFAULT_SEGMENTS);

        greenRect = new Rectangle2D(rectangleMesh, greenMaterial,
                (-VIEWPORT_WIDTH + rectWidth) / 2, (VIEWPORT_HEIGHT - rectHeight) / 2, rectWidth, rectHeight);
        blueRect = new Rectangle2D(rectangleMesh, blueMaterial,
                (VIEWPORT_WIDTH - rectWidth) / 2, 0, rectWidth, rectHeight);

        redCircle = new Circle2D(circleMesh, redMaterial, 0, 0, radius);
    }

    @Override
    String name() {
        return "Game Layer";
    }

    @Override
    void onAttach() {
        engine.getPhysicsEngine().add(new BoxCollider(greenRect, rectWidth, rectHeight));
        engine.getPhysicsEngine().add(new BoxCollider(blueRect, rectWidth, rectHeight));
        engine.getPhysicsEngine().add(new CircleCollider(redCircle, radius));
    }

    @Override
    void onUpdate(double deltaTime) {
        greenRect.update(deltaTime);
        blueRect.update(deltaTime);
        redCircle.update(deltaTime);
    }

    @Override
    void onRender() {
        engine.getRenderer().renderModel(greenRect);
        engine.getRenderer().renderModel(blueRect);
        engine.getRenderer().renderModel(redCircle);
    }

    @Override
    void onDetach() {
        engine.getPhysicsEngine().clearColliders();
        rectangleMesh.destroy();
        circleMesh.destroy();
        staticShader.destroy();
    }

    @Override
    boolean onEvent(Event event) {
        if (event.key() == GLFW_KEY_TAB && event.action() == GLFW_PRESS) {
            engine.popLayer();
            return true;
        }

        if (event.key() == GLFW_KEY_P && event.action() == GLFW_PRESS) {
            engine.pushLayer(new PauseLayer(engine));
            return true;
        }
        return false;
    }
}
