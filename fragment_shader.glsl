#version 120

in vec3 N;
in vec3 v;
//varying vec3 lightpos;

out vec4 color;

vec3 lightpos = vec3(8,8,0);
vec4 ambient = vec4(0,0,0,1);
vec4 diffuse = vec4(0.6,0.6,0.6,1);
vec4 specular = vec4(0.6, 0.6, 0.6, 0.7);

int shininess = 50;

void main (void)
{
    /*vec3 L = normalize(lightpos-v);
    vec3 E = normalize(-v);       // we are in Eye Coordinates, so EyePos is (0,0,0)
    vec3 R = normalize(-reflect(L,N));

    //calculate Ambient Term:
    vec4 Iamb = gl_Color;

    //calculate Diffuse Term:
    vec4 Idiff = 0.5 * diffuse * max(dot(N,L), 0.0);

    // calculate Specular Term:
    vec4 Ispec = specular * pow(max(dot(R,E),0.0), shininess);*/

    // write Total Color:
    color = vec4(1,1,1,1);//Iamb + Idiff + Ispec;

}