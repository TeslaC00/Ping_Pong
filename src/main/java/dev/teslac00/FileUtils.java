package dev.teslac00;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readFile(String path) {
        try (InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) throw new IOException("Shader file not found in classpath: " + path);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read shader file: " + path, e);
        }
    }
}
