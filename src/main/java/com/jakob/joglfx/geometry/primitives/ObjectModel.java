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
        super(null);
        this.numberOfVertices = new SimpleIntegerProperty(0);
        this.faces = new ArrayList<>();
    }

    public ObjectModel(String name) {
        super(name);
        this.numberOfVertices = new SimpleIntegerProperty(0);
        this.faces = new ArrayList<>();
    }

    public void addFace(Face f){
        this.faces.add(f);
        this.numberOfFaces.set(this.faces.size());
    }

    /**
     * Returns bufferedVertexData
     *
     * @return
     */




    public int numberOfFaces(){
        return faces.size();
    }

}
