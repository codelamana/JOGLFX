#version 120

attribute vec3 a_vert;
attribute vec3 a_norm;
attribute vec3 a_col;

varying vec3 N;
varying vec3 v;
//varying vec3 lightpos;

//vec3 light = vec3(5,5,0);

void main(void)
{

    v = vec3(gl_ModelViewMatrix * vec4(a_vert, 1));
    N = normalize(gl_NormalMatrix * normalize(a_norm));

    //lightpos = (gl_ModelViewProjectionMatrix * vec4(light, 1.0)).xyz;

    gl_Position = gl_ModelViewProjectionMatrix * vec4(a_vert, 1.0);
    gl_FrontColor = vec4(a_col, 1);
    gl_BackColor = vec4(a_col, 1);
}
