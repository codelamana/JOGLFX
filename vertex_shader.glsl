#version 430

in vec3 a_vert;
in vec3 a_norm;
in vec3 a_col;

out vec3 N;
out vec3 v;
//varying vec3 lightpos;

//vec3 light = vec3(5,5,0);

uniform mat4 mvp;

void main(void)
{

    v = vec3(mvp * vec4(a_vert, 1));
    N = a_norm;

    gl_Position = mvp * vec4(a_vert, 1.0);
    gl_FrontColor = vec4(a_col, 1);
    gl_BackColor = vec4(a_col, 1);
}
