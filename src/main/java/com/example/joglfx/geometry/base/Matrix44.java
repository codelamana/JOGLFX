package com.example.joglfx.geometry.base;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Matrix44 {

    float[][] matrix = new float[4][4];

    public Matrix44(Matrix44 m) {
        this.matrix = m.matrix;
    }

    public Matrix44(float[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix44(){
        for(int i = 0; i < 4; i++){
            this.matrix[i][i] = 1;
        }
    }

    public void set(int i, int j, float value){
        if(i < 0 || i > 4 || j <0 || j > 4) return;
        this.matrix[i][j] = value;
    }

    public float getValue(int i, int j){
        if(i < 0 || i > 4 || j <0 || j > 4) return 0;
        return this.matrix[i][j];
    }

    /**
     * Returns matrix
     *
     * @return
     */
    public float[][] getMatrix() {
        return matrix;
    }

    public static Matrix44 matrixMatrix(Matrix44 A, Matrix44 B){
        Matrix44 temp = new Matrix44();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) sum += A.getValue(i, k) * B.getValue(k, j);
                temp.set(i, j, sum);
            }
        }
        return temp;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            s += (new vec4(matrix[i])).toString() + "\n";
        }
        return s;
    }
}
