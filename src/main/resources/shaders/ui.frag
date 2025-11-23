#version 400

layout (location = 0) out vec4 out_color;

uniform vec4 u_color;

void main() {
    gl_FragColor = u_color;
}
