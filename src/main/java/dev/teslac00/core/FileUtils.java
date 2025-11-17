package dev.teslac00.core;

import org.lwjgl.BufferUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class FileUtils {

    public static String readFile(String path) {
        try (InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null)
                throw new FileNotFoundException("Shader file not found in classpath: %s".formatted(path));
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read shader file: %s".formatted(path), e);
        }
    }

    public static ByteBuffer loadResourceAsByteBuffer(String path) {
        try (InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null)
                throw new FileNotFoundException("Resource File not found in classpath: %s".formatted(path));

            byte[] bytes = inputStream.readAllBytes();
            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();

            return buffer;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource file: %s".formatted(path), e);
        }
    }

}
