# version 400 core

in vec2 position;
in vec4 rgba;

out vec4 color;

void main(void){
    gl_Position = vec4(position, 1.0, 1.0);
    color = rgba;
}