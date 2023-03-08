package com.jakob.joglfx.geometry;

import com.jakob.joglfx.geometry.base.Face;
import com.jakob.joglfx.geometry.base.Vertex;
import com.jogamp.common.nio.Buffers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GeometryObject {

    protected ArrayList<GeometryObject> children = new ArrayList<>();

    protected float[] vertexData;
    protected FloatBuffer bufferedVertexData;

    protected float[] normalData;
    protected FloatBuffer bufferedNormalData;

    protected float[] colorData;
    protected FloatBuffer bufferedColorData;

    protected ArrayList<Face> faces;
    protected ArrayList<Vertex> vertices;

    protected SimpleIntegerProperty numberOfFaces;
    protected SimpleStringProperty name;
    protected SimpleIntegerProperty numberOfVertices;

    protected Matrix4f modelMatrix;
    protected Vector3f worldSpacePosition;
    protected Quaternionf rotation;

    public GeometryObject(String newName) {
        this.name = new SimpleStringProperty(Objects.requireNonNullElse(newName, "Default Name"));
        this.numberOfVertices = new SimpleIntegerProperty(0);
        this.numberOfFaces = new SimpleIntegerProperty(0);
        this.vertexData = new float[0];
        this.normalData = new float[0];
        this.colorData = new float[0];
        this.faces = new ArrayList<>();
        this.vertices = new ArrayList<>();

        this.modelMatrix = new Matrix4f().identity();
        worldSpacePosition = new Vector3f();
        rotation = new Quaternionf();
    }

    public void addChild(GeometryObject... newObjects){
        this.children.addAll(Arrays.asList(newObjects));
        int numberOfChildFaces = this.children.stream().mapToInt(GeometryObject::numberOfFaces).sum();
        this.numberOfFaces.setValue(numberOfChildFaces + this.faces.size());
    }

    public void addChild(ArrayList<GeometryObject> newObjects){
        this.children.addAll(newObjects);
        int numberOfChildFaces = this.children.stream().mapToInt(GeometryObject::numberOfFaces).sum();
        this.numberOfFaces.setValue(numberOfChildFaces + this.faces.size());
    }

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

        bufferedVertexData = FloatBuffer.allocate(this.numberOfFaces.get() * 9);
        
        bufferedVertexData.put(vertexData);
        for(GeometryObject g: this.children) {
            bufferedVertexData.put(g.getBufferedVertexData());
        }
        
        bufferedVertexData.rewind();
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

        bufferedNormalData = FloatBuffer.allocate(this.numberOfFaces.get() * 9);

        bufferedNormalData.put(normalData);
        for(GeometryObject g: this.children) {
            bufferedNormalData.put(g.getBufferedNormalData());
        }

        bufferedNormalData.rewind();
        return bufferedNormalData;
    }



    public FloatBuffer getBufferedColorData() {

        colorData = new float[this.faces.size() * 3 * 3];

        int i = 0;
        for(Face f :this.faces) {
            for (Vertex v : f.vertices) {
                colorData[i] = v.color.x;
                colorData[i+1] = v.color.y;
                colorData[i+2] = v.color.z;
                i+=3;
            }
        }

        bufferedColorData = FloatBuffer.allocate(this.numberOfFaces.get() * 9);

        bufferedColorData.put(colorData);
        for(GeometryObject g: this.children) {
            bufferedColorData.put(g.getBufferedColorData());
        }

        bufferedColorData.rewind();
        return bufferedColorData;
    }

    public int numberOfFaces(){
        return this.numberOfFaces.get();
    }

    public SimpleIntegerProperty numberOfFacesProperty(){
        return this.numberOfFaces;
    }

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

    public ArrayList<GeometryObject> getChildren() {
        return children;
    }

    public static void printFloatArray(float[] array){
        int i = 0;
        for(float f : array){
            System.out.print(f);
            i++;
            if(i%3 == 0) System.out.println("");
        }
    }

    public ArrayList<Face> getFaces() {
        return faces;
    }

    public Matrix4f getModelMatrix() {
        Matrix4f temp = new Matrix4f(modelMatrix);
        return temp.translate(this.worldSpacePosition).rotate(this.rotation);
    }

    public void setRotation(Quaternionf newRotation){
        this.rotation = newRotation;
    }

}
