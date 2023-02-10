package com.example.joglfx.geometry.base;

public class vec4 {

    public float x,y,z,w;

    public vec4() {
        this.w = 1;
    }

    public vec4(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    public vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public vec4(float[] f){
        this.x = f[0];
        this.y = f[1];
        this.z = f[2];
        this.w = f[3];
    }

    public float[] getAsFloatArray(){
        return new float[]{x,y,z,w};
    }

    public vec3 xyz(){
        return new vec3(this.x, this.y, this.z);
    }

    public static vec4 matrixMult(Matrix44 A, vec4 b){
        float[] bFloat = b.getAsFloatArray();
        float[] temp = new float[4];
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++) {
                temp[i] += A.getValue(i, j) * bFloat[i];
            }
        }
        return new vec4(temp);
    }

    @Override
    public String toString() {
        return "["  + this.x + " " + this.y + " " + this.z + " " + this.w + "]";
    }
}
