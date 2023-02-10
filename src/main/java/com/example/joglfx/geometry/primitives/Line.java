package com.example.joglfx.geometry.primitives;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.example.joglfx.geometry.GeometryObject;
import com.example.joglfx.geometry.base.Face;
import com.example.joglfx.geometry.base.Vertex;
import com.example.joglfx.geometry.base.vec3;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Line extends GeometryObject {

    int color = 0;
    double x, y, z;
    double width, height, depth;

    int type = GL.GL_TRIANGLES;

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    public ArrayList<Face> faces;
    public ArrayList<Vertex> vertices;



    public Line(double x1, double y1, double z1, double x2, double y2, double z2) {

        this.faces = new ArrayList<>();
        this.vertices = new ArrayList<>();

        this.vertices.add(new Vertex((float) x1, (float) y1, (float) z1));
        this.vertices.add(new Vertex((float) x2, (float) y2, (float) z2));

        System.out.println(this.vertices.size());
        System.out.println(this.faces.size());

        vertexData = new float[this.vertices.size() * 3];
        normalData = new float[this.vertices.size() * 3];
    }

    @Override
    public FloatBuffer getBufferedVertexData() {


        int i=0;
        for (Vertex v : this.vertices) {
                vertexData[i] = v.pos.x;
                vertexData[i+1] = v.pos.y;
                vertexData[i+2] = v.pos.z;
                i+=3;
        }

        System.out.println(vertexData.length);
        printFloatArray(vertexData);
        bufferedVertexData = Buffers.newDirectFloatBuffer(vertexData);
        return bufferedVertexData;
    }

    @Override
    public FloatBuffer getBufferedNormalData() {


        int i=0;
        vec3 v3 = this.vertices.get(0).pos.xyz();
        vec3 v2 = this.vertices.get(1).pos.xyz();
        vec3 v1 = new vec3(0.5f,0,0);

        vec3 n= new vec3(0.5f, 0.5f, 0.5f);
        for (Vertex v : this.vertices) {
                normalData[i] = n.x;
                normalData[i+1] = n.y;
                normalData[i+2] = n.z;
                i+=3;
            }

        System.out.println(normalData.length);
        printFloatArray(normalData);

        bufferedNormalData = Buffers.newDirectFloatBuffer(normalData);
        return bufferedNormalData;
    }

    @Override
    public FloatBuffer getBufferedColorData() {
        return null;
    }

    @Override
    public int numberOfFaces() {
        return faces.size();
    }


}
