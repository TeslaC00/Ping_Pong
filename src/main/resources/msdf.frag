#version 330 core
in vec2 v_uv;
out vec4 fragColor;

uniform sampler2D u_font;
uniform vec4 u_color;
uniform float u_smoothing; // tweakable edge softness

void main() {
    float dist = texture(u_font, v_uv).r;
    float alpha = smoothstep(0.5 - u_smoothing, 0.5 + u_smoothing, dist);
    fragColor = vec4(u_color.rgb, alpha * u_color.a);
}
