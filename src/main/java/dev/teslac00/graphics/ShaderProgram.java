package dev.teslac00.graphics;

import dev.teslac00.util.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20C.*;

public abstract class ShaderProgram {

    private final int programId;
    private final Map<String, Uniform> uniforms = new HashMap<>();

    private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        int vertexShaderId = compileShader(vertexShaderFile, GL_VERTEX_SHADER);
        int fragmentShaderId = compileShader(fragmentShaderFile, GL_FRAGMENT_SHADER);

        programId = glCreateProgram();  // Create a shader program that has uses fragment and vertex shader

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        bindAttributes();   // bind all attributes needed by child classes
        glLinkProgram(programId);
        glValidateProgram(programId);

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException(String.format("Shader linking failed: %s", glGetProgramInfoLog(programId)));
        }

        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        detectUniforms();
    }

    protected abstract void bindAttributes();

    //    Bind the attribute index in VAO to shader variable
    protected void bindAttribute(int attributeIndex, String variableName) {
        glBindAttribLocation(programId, attributeIndex, variableName);
    }

    public abstract void loadUniforms(RenderableObject model);

    private static int compileShader(String shaderPath, int type) {
        String shaderSrc = FileUtils.readFile(shaderPath);

        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, shaderSrc);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Failed to compile shader %s : %s"
                    .formatted(shaderPath, glGetProgramInfoLog(shaderId)));
        }
        return shaderId;
    }

    protected void detectUniforms() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer count = stack.mallocInt(1);
            IntBuffer size = stack.mallocInt(1);
            IntBuffer type = stack.mallocInt(1);

            glGetProgramiv(programId, GL_ACTIVE_UNIFORMS, count);
            int uniformCount = count.get(0);

            for (int i = 0; i < uniformCount; i++) {
                String name = glGetActiveUniform(programId, i, 64, size, type);
                int location = glGetUniformLocation(programId, name);
                uniforms.put(name, new Uniform(name, location, type.get(0)));
            }
        }
    }

    public void start() {
        glUseProgram(programId);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void destroy() {
        glDeleteProgram(programId);
    }

    public void setUniform(String name, int value) {
        Uniform uniform = uniforms.get(name);
        if (uniform != null) glUniform1i(uniform.location(), value);
    }

    public void setUniform(String name, float value) {
        Uniform uniform = uniforms.get(name);
        if (uniform != null) glUniform1f(uniform.location(), value);
    }

    public void setUniform(String name, Vector4f v) {
        Uniform uniform = uniforms.get(name);
        if (uniform != null) glUniform4f(uniform.location(), v.x, v.y, v.z, v.w);
    }

    public void setUniform(String name, Matrix4f m) {
        Uniform uniform = uniforms.get(name);
        if (uniform != null) {
            m.get(buffer).rewind();
            glUniformMatrix4fv(uniform.location(), false, buffer);
        }
    }

    public void setUniform(String name, FloatBuffer buffer) {
        Uniform uniform = uniforms.get(name);
        if (uniform != null) {
            glUniformMatrix4fv(uniform.location(), false, buffer);
        }
    }

    public Map<String, Uniform> getUniforms() {
        return uniforms;
    }
}
