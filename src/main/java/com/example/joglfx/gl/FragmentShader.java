package com.example.joglfx.gl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

public class FragmentShader extends Shader {
    /**
     * Class for generating a fragment shader for the OGL application
     *
     * @param gl current JOGL GL2 context
     * @param path path to the glsl file for the fragment shader
     */


    public FragmentShader(GL4 gl, String path) {
        // invoke Shader and set gl context and path
        super(gl, path);
    }


    /**
     *  Create actual shader and print error if shader cannot be compiled
     *
     * @return shader id for use in OGL program
     */
    @Override
    public int createShader() {

        // setup input stream with fragment shader file
        File fragmentShader = new File(pathName);
        String shaderCode;
        try (InputStream in = new FileInputStream(fragmentShader)) {
            shaderCode = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // create and compile shader in gl context
        this.shader = gl.glCreateShader(gl.GL_FRAGMENT_SHADER);
        gl.glShaderSource(this.shader, 1, new String[] { shaderCode }, null);
        gl.glCompileShader(this.shader);

        // get compile status of shader
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetShaderiv(this.shader, GL2.GL_COMPILE_STATUS, intBuffer);

        // check wether shader compiled correctly, print out shader log if needed
        if (intBuffer.get(0) != 1) {
            gl.glGetShaderiv(this.shader, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            if (size > 0) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl.glGetShaderInfoLog(this.shader, size, intBuffer, byteBuffer);
                System.out.println(new String(byteBuffer.array()));
            }
            System.out.println("Error creating shader");
        }

        // return shader id
        return this.shader;
    }
}
