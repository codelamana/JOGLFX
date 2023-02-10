package com.example.joglfx.geometry;

import java.nio.FloatBuffer;

public abstract class GeometryObject {

    public abstract FloatBuffer getBufferedVertexData();

    public abstract FloatBuffer getBufferedNormalData();

    public abstract FloatBuffer getBufferedColorData();

    public abstract int numberOfFaces();

    protected void printFloatArray(float[] array){
        int i = 0;
        for(float f : array){
            System.out.print(f);
            i++;
            if(i%3 == 0) System.out.println("");
        }
    }

}
