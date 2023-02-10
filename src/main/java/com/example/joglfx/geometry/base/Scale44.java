package com.example.joglfx.geometry.base;

public class Scale44 extends Matrix44 {

    float[] factors = new float[]{1,1,1,1};

    public Scale44() {
        for(int i = 0; i < 4; i++){
            this.matrix[i][i] = factors[i];
        }
    }

    public Scale44(float[] factors) {
        for(int i = 0; i < 4; i++){
            this.matrix[i][i] = factors[i];
        }
    }
}
