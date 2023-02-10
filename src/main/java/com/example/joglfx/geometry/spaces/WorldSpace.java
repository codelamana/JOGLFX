package com.example.joglfx.geometry.spaces;

import com.example.joglfx.geometry.GeometryObject;
import com.example.joglfx.geometry.base.Face;
import com.example.joglfx.geometry.base.Vertex;
import com.example.joglfx.geometry.base.vec3;
import com.example.joglfx.geometry.base.vec4;
import com.example.joglfx.geometry.primitives.Cube;

public class WorldSpace {

    GeometryObject objects[];

    float x_min = -100, x_max = 100, y_min = -100, y_max = 100, z_min = -100, z_max = 100;

    public WorldSpace() {
        this.objects = new GeometryObject[1];
        this.objects[0] = new Cube(0,0,0, 3,3,3);
    }

    public static vec4 scaleToCanonicSpace(vec4 in){

        return new vec4();
    }
}
