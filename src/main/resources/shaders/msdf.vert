#version 400 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 uv;

out vec2 v_uv;

uniform mat4 u_proj;
uniform mat4 u_trans;

void main() {
    gl_Position = u_proj * u_trans * vec4(position, 0.0, 1.0);
    v_uv = uv;
}
