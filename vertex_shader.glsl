#version 430

in vec3 a_vert;
in vec3 a_color;

out vec3 v_color;

uniform mat4 mvp;

void main(void)
{
    gl_Position = mvp * vec4(a_vert, 1.0);
    v_color = a_color;
}
