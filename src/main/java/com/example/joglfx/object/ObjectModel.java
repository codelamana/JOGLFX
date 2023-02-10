package com.example.joglfx.object;

import com.example.joglfx.geometry.base.Face;
import com.example.joglfx.geometry.base.Vertex;
import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class ObjectModel {

    public ArrayList<Face> faces;

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    public ObjectModel() {
        this.faces = new ArrayList<>();
    }

    public void addFace(Face f){
        this.faces.add(f);
    }

    /**
     * Returns bufferedVertexData
     *
     * @return
     */
    public FloatBuffer getBufferedVertexData() {
        vertexData = new float[this.faces.size() * 3 * 3];
        
        int i=0;
        for(Face f :this.faces) {
            for (Vertex v : f.vertices) {
                vertexData[i] = v.pos.x;
                vertexData[i+1] = v.pos.y;
                vertexData[i+2] = v.pos.z;
                i+=3;
            }
        }

        System.out.println(vertexData.length);
        //printFloatArray(vertexData);
        bufferedVertexData = Buffers.newDirectFloatBuffer(vertexData);
        return bufferedVertexData;
    }

    /**
     * Returns bufferedNormalData
     *
     * @return
     */
    public FloatBuffer getBufferedNormalData() {
        normalData = new float[this.faces.size() * 3 * 3];

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

    void printFloatArray(float[] array){
        int i = 0;
        for(float f : array){
            System.out.print(f);
            i++;
            if(i%3 == 0) System.out.println("");
        }
    }

    public int numberOfFaces(){
        return faces.size();
    }


}
