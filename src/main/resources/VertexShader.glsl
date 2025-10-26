# version 400 core

layout (location = 0) in vec2 position;

uniform mat4 u_proj;
uniform mat4 u_trans;

void main(void){
    gl_Position = u_proj * u_trans * vec4(position, 0.0, 1.0);
}