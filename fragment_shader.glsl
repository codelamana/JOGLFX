#version 430

out vec4 color;

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
    color = vec4(1,0,1,1);//Iamb + Idiff + Ispec;

}