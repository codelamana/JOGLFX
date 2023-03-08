package com.jakob.joglfx.geometry.primitives;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.base.Face;
import com.jakob.joglfx.geometry.base.Vertex;
import com.jogamp.common.nio.Buffers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Cube extends GeometryObject {


    double x, y, z;
    double width, height, depth;

    float[] cube_raw_vertices = {
            -1.0f,-1.0f,-1.0f, // triangle 1 : begin
            -1.0f,-1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, // triangle 1 : end
            1.0f, 1.0f,-1.0f, // triangle 2 : begin
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f, // triangle 2 : end
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f
    };

    public Cube(double x, double y, double z, double width, double height, double depth) {
        super(null);
        this.numberOfVertices = new SimpleIntegerProperty(0);
        this.worldSpacePosition = new Vector3f((float) x, (float) y, (float) z);
        this.width = width;
        this.height = height;
        this.depth = depth;

        generateVertices();
    }

    public Cube(String name, double x, double y, double z, double width, double height, double depth) {
        super(name);
        this.numberOfVertices = new SimpleIntegerProperty(0);
        this.worldSpacePosition = new Vector3f((float) x, (float) y, (float) z);
        this.width = width;
        this.height = height;
        this.depth = depth;

        generateVertices();
    }

    private void generateVertices(){

        for (int i = 0; i < cube_raw_vertices.length ; i+=3) {

            Vertex t = new Vertex((float)(width * cube_raw_vertices[i]),(float)(height*cube_raw_vertices[i+1]),(float)(depth*cube_raw_vertices[i+2]));

            t.color = new Vector3f(0.3f * cube_raw_vertices[i] + 0.5f, 0.3f * cube_raw_vertices[i+1] + 0.5f, 0.3f * cube_raw_vertices[i+2] + 0.5f);

            this.vertices.add(t);

        }

        for (int i = 0; i < this.vertices.size(); i+=3) {
            this.faces.add(new Face(this.vertices.get(i),
                    this.vertices.get(i+1),
                    this.vertices.get(i+2)).returnWithFaceNormals());
        }

        this.numberOfFaces.set(faces.size());


    }



}
