package com.jakob.joglfx.geometry.primitives;

import com.jakob.joglfx.geometry.GeometryObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class CompositeObject extends GeometryObject {

    float[] vertexData;
    FloatBuffer bufferedVertexData;

    float[] normalData;
    FloatBuffer bufferedNormalData;

    float[] colorData;
    FloatBuffer bufferedColorData;

    int numberOfFaces = 0;

    public CompositeObject() {
        this.name = new SimpleStringProperty("defaultValue");
        this.numberOfVertices = new SimpleIntegerProperty(0);
    }

    public CompositeObject(String name) {
        this.name = new SimpleStringProperty(name);
        this.numberOfVertices = new SimpleIntegerProperty(0);
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

}
