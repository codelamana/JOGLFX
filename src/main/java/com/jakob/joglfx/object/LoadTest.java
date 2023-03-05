package com.jakob.joglfx.object;

import com.jakob.joglfx.geometry.primitives.ObjectModel;

public class LoadTest {

    public static void main(String[] args) {
        OBJloader l = new OBJloader("src/testProject/cube_flat.obj");
        ObjectModel m = l.load();
    }

}
