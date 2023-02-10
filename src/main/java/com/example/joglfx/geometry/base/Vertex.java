package com.example.joglfx.geometry.base;

public class Vertex {

    public vec4 pos;
    public vec3 normal;

    public vec3 color;

    public Vertex() {
    }

    public Vertex(float x, float y, float z){
        this.pos  = new vec4(x,y,z,1);
        this.normal = new vec3(1,1,1);
    }

    public Vertex(vec4 pos, vec3 normal){
        this.pos = pos;
        this.normal = normal;
    }

    public Vertex(Vertex v, vec3 normal){
        this.pos = v.pos;
        this.normal = normal;
    }


}
