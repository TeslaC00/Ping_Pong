package dev.teslac00.graphics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.teslac00.core.FileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------------------------------------------
 * Project : Ping_Pong
 * File    : Font
 * Author  : Vikas Kumar
 * Created : 14-11-2025
 * ---------------------------------------------------------------
 */
// MSDF font generator -> https://msdf-bmfont.donmccurdy.com/
public class Font {
    private final Map<Integer, FontCharacter> characters = new HashMap<>();
    private final int atlasWidth;   // Font Texture Width
    private final int atlasHeight;  // Font Texture Height
    private final float fontSize;   // Font Character Size
    private final int textureId;

    public Font(
            String atlasPath, // Font Texture Path
            String jsonPath
    ) {
        this.textureId = atlasPath.length(); // TODO: use texture loader to load texture

        JsonObject jsonObject = JsonParser.parseString(FileUtils.readFile(jsonPath)).getAsJsonObject();

        this.atlasWidth = jsonObject.getAsJsonObject("common").get("scaleW").getAsInt();
        this.atlasHeight = jsonObject.getAsJsonObject("common").get("scaleH").getAsInt();
        this.fontSize = jsonObject.getAsJsonObject("info").get("size").getAsFloat();

        JsonArray chars = jsonObject.getAsJsonArray("chars");
        for (int index = 0; index < chars.size(); index++) {
            JsonObject charObject = chars.get(index).getAsJsonObject();

            float x = charObject.get("x").getAsFloat();
            float y = charObject.get("y").getAsFloat();
            float width = charObject.get("width").getAsFloat();
            float height = charObject.get("height").getAsFloat();
            int id = charObject.get("id").getAsInt();

            FontCharacter character = new FontCharacter(
                    x,
                    y,
                    width,
                    height,
                    charObject.get("xoffset").getAsFloat(),
                    charObject.get("yoffset").getAsFloat(),
                    charObject.get("xadvance").getAsFloat(),
                    x / atlasWidth,
                    y / atlasHeight,
                    (x + width) / atlasWidth,
                    (y + height) / atlasHeight
            );

            characters.put(id, character);
        }
    }

    public FontCharacter getFontCharacter(char character) {
        return characters.get((int) character);
    }

    public float getFontSize() {
        return fontSize;
    }

    public int getAtlasWidth() {
        return atlasWidth;
    }

    public int getAtlasHeight() {
        return atlasHeight;
    }
}
