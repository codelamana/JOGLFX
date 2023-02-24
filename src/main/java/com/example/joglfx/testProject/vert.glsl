#version 430

layout(location = 0) in vec3 a_vert;
layout(location = 1) in vec3 a_norm;
uniform mat4 mvp;

out vec3 norm;

void main() {
    gl_Position = mvp * vec4(a_vert.xyz, 1.0);
    gl_FrontColor = gl_Color;
    gl_BackColor = gl_Color;
}
