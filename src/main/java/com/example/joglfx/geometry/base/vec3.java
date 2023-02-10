package com.example.joglfx.geometry.base;

public class vec3 {

    public float x, y, z;

    public vec3() {

    }

    public vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    static public vec3 sub(vec3 a, vec3 b){
        return new vec3(a.x - b.x, a.y -b.y, a.z-b.z);
    }

    static public vec3 cross(vec3 a, vec3 b){
        return new vec3(
                a.y*b.z - a.z*b.y,
                a.z*b.x - a.x*b.z,
                a.x*b.y - a.y*b.x
        );
    }

    @Override
    public String toString() {
        return "["  + this.x + " " + this.y + " " + this.z + " " +  "]";
    }

    public float[] asFloatArray(){
        return new float[]{x,y,z};
    }
}
