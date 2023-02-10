#version 120

layout(location = 0) attribute vec3 vertexPosition_modelspace;

void main() {
    glPosition = vec4(vertexPosition_modelspace.xyz, 1.0);
    gl_Position = ftransform();
    gl_FrontColor = gl_Color;
    gl_BackColor = gl_Color;
}
