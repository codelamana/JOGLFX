package com.example.joglfx.geometry.primitives;

import com.example.joglfx.geometry.GeometryObject;
import com.example.joglfx.geometry.base.Face;
import com.example.joglfx.geometry.base.Vertex;
import com.example.joglfx.geometry.base.vec3;
import com.jogamp.common.nio.Buffers;

import java.awt.image.AreaAveragingScaleFilter;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Cube extends GeometryObject {

    int color = 0;
    double x, y, z;
    double width, height, depth;

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    float[] colorData;
    FloatBuffer bufferedColorData;

    public ArrayList<Face> faces;
    public ArrayList<Vertex> vertices;

    float cube_raw_vertices[] = {
            -1.0f,-1.0f,-1.0f, // triangle 1 : begin
            -1.0f,-1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, // triangle 1 : end
            1.0f, 1.0f,-1.0f, // triangle 2 : begin
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f, // triangle 2 : end
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f
    };

    public Cube(double x, double y, double z, double width, double height, double depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;

        this.faces = new ArrayList<>();
        this.vertices = new ArrayList<>();

        for (int i = 0; i < cube_raw_vertices.length ; i+=3) {
            Vertex t = new Vertex((float)(width * cube_raw_vertices[i]  + x),(float)(height*cube_raw_vertices[i+1] +y),(float)(depth*cube_raw_vertices[i+2] + z));
            t.color = new vec3(0.5f, 0.1f, 0.0f);
            this.vertices.add(t);

        }

        for (int i = 0; i < this.vertices.size(); i+=3) {
            this.faces.add(new Face(this.vertices.get(i),
                    this.vertices.get(i+1),
                    this.vertices.get(i+2)).returnWithFaceNormals());
        }

        System.out.println(this.vertices.size());
        System.out.println(this.faces.size());

        vertexData = new float[this.faces.size() * 3 * 3];
        normalData = new float[this.faces.size() * 3 * 3];
        colorData = new float[this.faces.size() * 3 * 3];
    }

    @Override
    public FloatBuffer getBufferedVertexData() {

        int i=0;
        for(Face f :this.faces) {
            for (Vertex v : f.vertices) {
                vertexData[i] = v.pos.x;
                vertexData[i+1] = v.pos.y;
                vertexData[i+2] = v.pos.z;
                i+=3;
            }
        }

        //System.out.println(vertexData.length);
        //printFloatArray(vertexData);
        bufferedVertexData = Buffers.newDirectFloatBuffer(vertexData);
        return bufferedVertexData;
    }

    @Override
    public FloatBuffer getBufferedNormalData() {

        int i=0;
        for(Face f :this.faces) {
            for (Vertex v : f.vertices) {
                normalData[i] = v.normal.x;
                normalData[i+1] = v.normal.y;
                normalData[i+2] = v.normal.z;
                i+=3;
            }
        }

        System.out.println(normalData.length);
        //printFloatArray(normalData);

        bufferedNormalData = Buffers.newDirectFloatBuffer(normalData);
        return bufferedNormalData;
    }

    public FloatBuffer getBufferedColorData(){

        int i = 0;
        for(Face f :this.faces) {
            for (Vertex v : f.vertices) {
                colorData[i] = v.color.x;
                colorData[i+1] = v.color.y;
                colorData[i+2] = v.color.z;
                i+=3;
            }
        }

        bufferedColorData = Buffers.newDirectFloatBuffer(colorData);
        return bufferedColorData;
    }

    @Override
    public int numberOfFaces() {
        return faces.size();
    }


}
