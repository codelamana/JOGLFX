package com.jakob.joglfx.geometry.primitives;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.base.Face;
import com.jakob.joglfx.geometry.base.Vertex;
import com.jogamp.common.nio.Buffers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class ObjectModel extends GeometryObject {



    public ObjectModel() {
        this.name = new SimpleStringProperty("defaultValue");
        this.numberOfVertices = new SimpleIntegerProperty(0);
        this.faces = new ArrayList<>();
    }

    public ObjectModel(String name) {
        this.name = new SimpleStringProperty(name);
        this.numberOfVertices = new SimpleIntegerProperty(0);
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
