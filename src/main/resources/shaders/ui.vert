#version 400

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 uv;

uniform vec2 u_position;
uniform vec2 u_dimension;
uniform vec2 u_resolution;

void main() {
    vec2 position_normalized = (uv * u_dimension + u_position) / u_resolution;
    float x = (position_normalized.x * 2.0) - 1;
    float y = 1 - (position_normalized.y * 2.0);
    gl_Position = vec4(x, y, 1.0, 1.0);
}
