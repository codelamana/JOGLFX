package com.jakob.joglfx.geometry.base;

import org.joml.GeometryUtils;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Face {

    /**
     * Data class for storing a face of a polygon model
     *
     */

    // always calulate face normal of a face
    static boolean CALC_FACE_NORMALS = true;

    public ArrayList<Vertex> vertices;
    Vector3f face_normal;

    public Face(Vertex v1, Vertex v2, Vertex v3) {
        this.vertices = new ArrayList<>();
        this.vertices.add(v1);
        this.vertices.add(v2);
        this.vertices.add(v3);
        if(CALC_FACE_NORMALS){

            Vector3f n = new Vector3f();

            GeometryUtils.normal(v1.pos.x(), v1.pos.y(), v1.pos.z(),
                                v2.pos.x(), v2.pos.y(), v2.pos.z(),
                                v3.pos.x(), v3.pos.y(), v3.pos.z(),
                                n);

            this.face_normal = n;

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
