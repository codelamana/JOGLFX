package com.example.joglfx.geometry.base;

import java.util.ArrayList;

public class Face {

    static boolean CALC_FACE_NORMALS = true;

    public ArrayList<Vertex> vertices;
    vec3 face_normal;

    public Face(Vertex v1, Vertex v2, Vertex v3) {
        this.vertices = new ArrayList<>();
        this.vertices.add(v1);
        this.vertices.add(v2);
        this.vertices.add(v3);
        if(CALC_FACE_NORMALS){
            this.face_normal = vec3.cross(vec3.sub(v1.pos.xyz(), v3.pos.xyz()),vec3.sub(v1.pos.xyz(), v2.pos.xyz()));
        }
    }

    public Face returnWithFaceNormals(){
        Face f = new Face(vertices.get(0), vertices.get(1), vertices.get(2));
        for(Vertex v: f.vertices) v.normal = this.face_normal;
        return f;
    }

    public void setVertexToFaceNormals(){
        for(Vertex v: vertices) v.normal = face_normal;
    }
}
