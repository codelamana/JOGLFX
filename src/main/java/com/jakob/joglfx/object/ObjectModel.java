package com.jakob.joglfx.object;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.base.Face;
import com.jakob.joglfx.geometry.base.Vertex;
import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class ObjectModel extends GeometryObject {

    public ArrayList<Face> faces;

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    private float[] colorData;
    FloatBuffer bufferedColorData;

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

    @Override
    public FloatBuffer getBufferedColorData() { int i = 0;

        colorData = new float[this.faces.size() * 3 * 3];

        for(Face f :this.faces) {
            for (Vertex v : f.vertices) {
                colorData[i] = 1.0f;
                colorData[i+1] = 0.5f;
                colorData[i+2] = 0.25f;
                i+=3;
            }
        }

        bufferedColorData = Buffers.newDirectFloatBuffer(colorData);
        return bufferedColorData;
    }

    public void printFloatArray(float[] array){
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
