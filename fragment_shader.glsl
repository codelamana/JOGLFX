#version 430

in vec3 v_color;
out vec4 color;

void main (void)
{
    /*vec3 L = normalize(lightpos-v);
    vec3 E = normalize(-v);       // we are in Eye Coordinates, so EyePos is (0,0,0)
    vec3 R = normalize(-reflect(L,N));

    //calculate Ambient Term:
    vec4 Iamb = vec4(v_color, 1);

    //calculate Diffuse Term:
    vec4 Idiff = 0.5 * vec4(v_color, 1) * max(dot(N,L), 0.0);

    // calculate Specular Term:
    vec4 Ispec = vec4(1,1,1,1) * pow(max(dot(R,E),0.0), shininess);*/

    // write Total Color:
    color = vec4(v_color,1);//Iamb + Idiff + Ispec;

}