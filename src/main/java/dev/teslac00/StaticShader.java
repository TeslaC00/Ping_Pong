package dev.teslac00;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_SHADER_FILE = "VertexShader.glsl";
    private static final String FRAGMENT_SHADER_FILE = "FragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
