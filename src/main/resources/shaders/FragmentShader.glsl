# version 400 core

layout (location = 0) out vec4 out_color;

in vec2 v_uv;

uniform vec4 u_color;
uniform sampler2D u_texture;
uniform bool u_useTexture;

void main(void){
    if (u_useTexture){
        out_color = texture(u_texture, v_uv);
    } else {
        out_color = u_color;
    }
}