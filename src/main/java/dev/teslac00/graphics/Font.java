/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Font
 * Author  : Vikas Kumar
 * Created : 18-11-2025
 * ---------------------------------------------------------------
 */
package dev.teslac00.graphics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.teslac00.util.FileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a Font, managed by AssetManager.
 * It manages its own assets since it should be singleton
 */
public class Font {
    private int fontBase = 0;
    private final Texture texture;
    private final Map<Character, Glyph> glyphMap = new HashMap<>();

    public Font(String atlasPath, String dataJsonPath) {
        texture = new Texture(atlasPath);

        loadFontData(dataJsonPath);
    }

    private void loadFontData(String dataJsonPath) {
        JsonObject jsonObject = JsonParser.parseString(FileUtils.readFile(dataJsonPath)).getAsJsonObject();

        int atlasWidth = jsonObject.getAsJsonObject("common").get("scaleW").getAsInt();
        int atlasHeight = jsonObject.getAsJsonObject("common").get("scaleH").getAsInt();
        fontBase = jsonObject.getAsJsonObject("common").get("base").getAsInt();
//        float fontSize = jsonObject.getAsJsonObject("info").get("size").getAsFloat();

        JsonArray chars = jsonObject.getAsJsonArray("chars");
        for (int index = 0; index < chars.size(); index++) {
            JsonObject charObject = chars.get(index).getAsJsonObject();

            float x = charObject.get("x").getAsFloat();
            float y = charObject.get("y").getAsFloat();
            float width = charObject.get("width").getAsFloat();
            float height = charObject.get("height").getAsFloat();
            int id = charObject.get("id").getAsInt();

            Glyph character = new Glyph(
                    width,
                    height,
                    x / atlasWidth,
                    y / atlasHeight,
                    (x + width) / atlasWidth,
                    (y + height) / atlasHeight,
                    charObject.get("xoffset").getAsFloat(),
                    charObject.get("yoffset").getAsFloat(),
                    charObject.get("xadvance").getAsFloat()
            );

            glyphMap.put((char) id, character);
        }
    }

    public int getFontBase() {
        return fontBase;
    }

    public Glyph getGlyph(char c) {
        return glyphMap.getOrDefault(c, glyphMap.get('?'));
    }

    public Texture getTexture() {
        return texture;
    }

    public void destroy() {
        texture.destroy();
        glyphMap.clear();
    }
}
