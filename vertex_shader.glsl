#version 430

in vec3 a_vert;

void main(void)
{
    gl_Position = vec4(a_vert, 1.0);
    gl_FrontColor = vec4(1,1,1, 1);
    gl_BackColor = vec4(1,1,1, 1);
}
