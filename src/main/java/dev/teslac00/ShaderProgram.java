package dev.teslac00;

import static org.lwjgl.opengl.GL20C.*;

public abstract class ShaderProgram {

    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;

    public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        vertexShaderId = loadShader(vertexShaderFile, GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentShaderFile, GL_FRAGMENT_SHADER);
        programId = glCreateProgram();  // Create a shader program that has uses fragment and vertex shader

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        bindAttributes();   // bind all attributes needed by child classes
        glLinkProgram(programId);
        glValidateProgram(programId);

    }

    protected abstract void bindAttributes();

    //    Bind the attribute index in VAO to shader variable
    protected void bindAttribute(int attributeIndex, String variableName) {
        glBindAttribLocation(programId, attributeIndex, variableName);
    }

    public void start() {
        glUseProgram(programId);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void destroy() {
        glUseProgram(0);
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteProgram(programId);
    }

    private static int loadShader(String shaderPath, int type) {
        String shaderSrc = FileUtils.readFile(shaderPath);

        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, shaderSrc);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderId, 500));
            throw new RuntimeException(String.format("Could not compile shader: %s, Type: %d ", shaderPath, type));
        }
        return shaderId;
    }

    public int getProgramId() {
        return programId;
    }
}
