package com.example.joglfx.geometry.primitives;

import com.example.joglfx.geometry.GeometryObject;
import com.example.joglfx.geometry.base.Face;
import com.example.joglfx.geometry.base.Vertex;
import com.example.joglfx.geometry.base.vec3;

import java.awt.geom.NoninvertibleTransformException;
import java.nio.FloatBuffer;

public class Plane extends GeometryObject {
    
    
    float x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4;
    
    Vertex v1, v2, v3, v4;
    Face f1, f2;
    vec3 n1, n2;
    vec3 color;

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    float[] colorData;
    FloatBuffer bufferedColorData;

    public Plane(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
        this.v1 = new Vertex(x1,y1,z1);
        this.v2 = new Vertex(x2,y2,z2);
        this.v3 = new Vertex(x3,y3,z3);
        this.v4 = new Vertex(x4,y4,z4);

        f1 = new Face(v1,v2,v3).returnWithFaceNormals();
        f2 = new Face(v2,v3,v4).returnWithFaceNormals();
        
    }

    public Plane(Vertex v1, Vertex v2, Vertex v3, Vertex v4){
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;

        f1 = new Face(v1,v2,v3).returnWithFaceNormals();
        f2 = new Face(v2,v3,v4).returnWithFaceNormals();
    }

    @Override
    public FloatBuffer getBufferedVertexData() {
        bufferedVertexData = FloatBuffer.allocate(2 * 3*3);
        bufferedVertexData.put(v1.pos.xyz().asFloatArray())
                            .put(v2.pos.xyz().asFloatArray())
                            .put(v3.pos.xyz().asFloatArray())
                            .put(v2.pos.xyz().asFloatArray())
                            .put(v3.pos.xyz().asFloatArray())
                            .put(v4.pos.xyz().asFloatArray());
        bufferedVertexData.rewind();
        return bufferedVertexData;
    }

    @Override
    public FloatBuffer getBufferedNormalData() {
        bufferedNormalData = FloatBuffer.allocate(2 * 3*3);
        bufferedNormalData.put(f1.vertices.get(0).normal.asFloatArray())
                .put(f1.vertices.get(1).normal.asFloatArray())
                .put(f1.vertices.get(2).normal.asFloatArray())
                .put(f2.vertices.get(0).normal.asFloatArray())
                .put(f2.vertices.get(1).normal.asFloatArray())
                .put(f2.vertices.get(2).normal.asFloatArray());
        bufferedNormalData.rewind();
        return bufferedNormalData;
    }

    @Override
    public FloatBuffer getBufferedColorData() {
        bufferedColorData = FloatBuffer.allocate(2 * 3*3);

        for (int i = 0; i < 2*3*3 ; i++) {
            bufferedColorData.put(0.5f);
        }

        bufferedColorData.rewind();
        return bufferedColorData;

    }

    @Override
    public int numberOfFaces() {
        return 2;
    }
}
