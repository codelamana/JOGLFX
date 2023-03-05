package com.jakob.joglfx.geometry;

import com.jakob.joglfx.geometry.base.Face;
import com.jakob.joglfx.geometry.base.Vertex;
import com.jogamp.common.nio.Buffers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public abstract class GeometryObject {

    protected ArrayList<GeometryObject> objects = new ArrayList<>();

    protected float[] vertexData;
    protected FloatBuffer bufferedVertexData;

    protected float[] normalData;
    protected FloatBuffer bufferedNormalData;

    protected float[] colorData;
    protected FloatBuffer bufferedColorData;

    protected ArrayList<Face> faces;
    protected ArrayList<Vertex> vertices;

    protected SimpleStringProperty name;
    protected SimpleIntegerProperty numberOfVertices;



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

        bufferedNormalData = Buffers.newDirectFloatBuffer(normalData);
        return bufferedNormalData;
    }



    public FloatBuffer getBufferedColorData() {

        colorData = new float[this.faces.size() * 3 * 3];

        int i = 0;
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

    public abstract int numberOfFaces();



    public SimpleIntegerProperty numberOfVerticesProperty(){
        return numberOfVertices;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getName(){
        return this.name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public ArrayList<GeometryObject> getObjects() {
        return objects;
    }

    protected void printFloatArray(float[] array){
        int i = 0;
        for(float f : array){
            System.out.print(f);
            i++;
            if(i%3 == 0) System.out.println("");
        }
    }

}
