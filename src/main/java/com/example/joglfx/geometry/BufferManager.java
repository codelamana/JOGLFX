package com.example.joglfx.geometry;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class BufferManager {

    ArrayList<GeometryObject> objects = new ArrayList<>();

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    float[] colorData;
    FloatBuffer bufferedColorData;

    int numberOfFaces = 0;

    public BufferManager() {

    }

    public void addObject(GeometryObject geometryObject){
        this.objects.add(geometryObject);
        this.numberOfFaces += geometryObject.numberOfFaces();
    }


    public int numberOfFaces() {
        int sum = 0;
        for(GeometryObject p : objects){
            sum += p.numberOfFaces();
        }
        this.numberOfFaces = sum;
        return sum;
    }

    public FloatBuffer getBufferedVertexData() {
        bufferedVertexData = FloatBuffer.allocate(this.numberOfFaces * 9);
        System.out.println(this.numberOfFaces*9);
        for(GeometryObject g: this.objects) {
            bufferedVertexData.put(g.getBufferedVertexData());
        }
        //printFloatBuffer(bufferedVertexData);
        bufferedVertexData.rewind();
        return bufferedVertexData;
    }

    public FloatBuffer getBufferedNormalData() {
        bufferedNormalData = FloatBuffer.allocate(this.numberOfFaces * 9);
        for(GeometryObject g: this.objects) {
            bufferedNormalData.put(g.getBufferedNormalData());
        }
        bufferedNormalData.rewind();
        return bufferedNormalData;
    }

    public FloatBuffer getBufferedColorData() {
        bufferedColorData = FloatBuffer.allocate(this.numberOfFaces * 9);
        for(GeometryObject g: this.objects) {
            bufferedColorData.put(g.getBufferedColorData());
        }
        bufferedColorData.rewind();
        return bufferedColorData;
    }

    public static void printFloatBuffer(FloatBuffer buf){

            buf.rewind();
            System.err.print(buf.toString() + ": ");
            for (int i = 0; i < buf.remaining(); i++) {
                System.err.print(buf.get(i) + " ");
                if((i+1) % 3 == 0) System.err.println("");
            }
            System.err.println(" ");
            buf.rewind();

    }
}
