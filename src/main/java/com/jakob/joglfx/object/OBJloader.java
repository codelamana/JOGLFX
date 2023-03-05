package com.jakob.joglfx.object;



import com.jakob.joglfx.geometry.base.Face;
import com.jakob.joglfx.geometry.base.Vertex;
import com.jakob.joglfx.geometry.primitives.ObjectModel;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class OBJloader {

    String path;

    public OBJloader(String path) {
        this.path = path;
    }


    public ObjectModel load(){

        ObjectModel model = null;
        ArrayList<Vector3f> fileVertexPositions = new ArrayList<>();
        ArrayList<Vector3f> fileNormalVectors = new ArrayList<>();

        try {
            // Read the file and extract data
            BufferedReader reader = new BufferedReader(new FileReader(this.path));

            model = new ObjectModel();

            String[] split;
            String line = reader.readLine();

            while (line != null) {
                line = line.trim();

                if (line.startsWith("v ")) {
                    split = line.split(" ");
                    System.out.print("Vertex detected ");

                    System.out.print(Arrays.toString(split));

                    fileVertexPositions.add(new Vector3f(
                            Float.parseFloat(split[1]),
                            Float.parseFloat(split[2]),
                            Float.parseFloat(split[3])
                    ));

                    System.out.println(fileVertexPositions.get(fileVertexPositions.size()-1));
                }

                if (line.startsWith("vn ")) {
                    split = line.split(" ");
                    System.out.print("Vertex normal detected");

                    System.out.print(Arrays.toString(split));

                    fileNormalVectors.add(new Vector3f(
                            Float.parseFloat(split[1]),
                            Float.parseFloat(split[2]),
                            Float.parseFloat(split[3])
                    ));
                    System.out.println(fileNormalVectors.get(fileNormalVectors.size()-1));
                }

                if (line.startsWith("f ")) {
                    split = line.split(" ");
                    System.out.println(Arrays.toString(split));

                    //Build separate vertices

                    Vertex v1 = new Vertex(
                            fileVertexPositions.get(Integer.parseInt(split[1].split("/")[0])-1),
                            fileNormalVectors.get(Integer.parseInt(split[1].split("/")[2])-1)
                    );
                    Vertex v2 = new Vertex(
                            fileVertexPositions.get(Integer.parseInt(split[2].split("/")[0])-1),
                            fileNormalVectors.get(Integer.parseInt(split[2].split("/")[2])-1)
                    );
                    Vertex v3 = new Vertex(
                            fileVertexPositions.get(Integer.parseInt(split[3].split("/")[0])-1),
                            fileNormalVectors.get(Integer.parseInt(split[3].split("/")[2])-1)
                    );

                    // Add to face

                    Face f  = new Face(v1, v2, v3);
                    model.addFace(f);
                }

                // read next line
                line = reader.readLine();
            }

        } catch(IOException e) {
            e.printStackTrace();
        }


        return model;
    }
}
