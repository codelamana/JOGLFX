package com.example.joglfx.gl;

import com.jogamp.opengl.GL2;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ShaderProgram {
    /**
     * Class for creating a OGL shading pipeline using a VertexShader and FragmentShader
     *
     * Takes over all steps of creating a shader program in OGL
     */

    VertexShader v = null;
    FragmentShader f = null;

    GL2 gl;
    int programID;

    /**
     * Setting up Program class
     * @param gl current OGL Context
     */
    public ShaderProgram(GL2 gl) {
        this.gl = gl;
    }

    /**
     * Create a vertex shader for the current program with the given glsl file
     * @param path path to glsl file
     */
    public void addVertexShader(String path){
        this.v = new VertexShader(gl, path);
    }

    /**
     * Create a fragment shader for the current program with the given glsl file
     * @param path path to glsl file
     */
    public void addFragmentShader(String path){
        this.f = new FragmentShader(gl, path);
    }

    /**
     * creating a shader program with vertex and fragment shader.
     * print out error log when needed.
     *
     * @return program id for use inn OGL program
     */
    public int createProgram(){

        // create program
        this.programID = gl.glCreateProgram();

        // attach the previously created shaders and link program
        // TODO check wether shader ids are 0
        gl.glAttachShader(this.programID, v.createShader());
        gl.glAttachShader(this.programID, f.createShader());

        gl.glLinkProgram(this.programID);

        // check wether program linked correctly
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(programID, GL2.GL_LINK_STATUS, intBuffer);

        // when error occurs print out info log
        if (intBuffer.get(0) != 1) {
            gl.glGetProgramiv(programID, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            if (size > 0) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl.glGetProgramInfoLog(programID, size, intBuffer, byteBuffer);
                System.out.println(new String(byteBuffer.array()));
            }
            System.out.println("Error creating shader program");
        }

        return this.programID;
    }
}
