package com.jakob.joglfx.geometry.base;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {

    public Vector4f pos;
    public Vector3f normal;

    public Vector3f color;

    public Vertex() {
    }

    public Vertex(float x, float y, float z){
        this.pos  = new Vector4f(x,y,z,1);
        this.normal = new Vector3f(1,1,1);
    }

    public Vertex(Vector3f pos, Vector3f normal){
        this.pos = new Vector4f(pos, 1);
        this.normal = normal;
    }

    public Vertex(Vertex v, Vector3f normal){
        this.pos = v.pos;
        this.normal = normal;
    }


}
