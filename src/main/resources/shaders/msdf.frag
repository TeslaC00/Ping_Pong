#version 400 core

in vec2 v_uv;
out vec4 fragColor;

uniform sampler2D u_font_atlas;
uniform vec4 u_color;
uniform float u_smoothing;// tweakable edge softness

float median(float r, float g, float b){
    return max(min(r, g), min(max(r, g), b));
}

// Might need to pass this as uniform depending on font generation size
float screenPxRange(){
    float pxRange = 2.0;
    vec2 unitRange = vec2(pxRange)/vec2(textureSize(u_font_atlas, 0));
    vec2 screenTexSize = vec2(1.0)/fwidth(v_uv);
    return max(0.5 * dot(unitRange, screenTexSize), 1.0);
}

void main() {
    //    Read MSDF texture
    vec3 msd = texture(u_font_atlas, v_uv).rgb;

    //    Calculate signed distance
    float sd = median(msd.r, msd.g, msd.b);

    //    Check logic: 0.5 is the edge.
    //    Use fwidth for anti-aliasing (standard deviation)
    float screenPxDistance = screenPxRange() * (sd-0.5);
    float opacity = clamp(screenPxDistance +0.5, 0.0, 1.0);

    if (opacity < 0.1) discard;// optimization

    //    float alpha = smoothstep(0.5 - u_smoothing, 0.5 + u_smoothing, dist);
    fragColor = vec4(u_color.rgb, opacity);
}
