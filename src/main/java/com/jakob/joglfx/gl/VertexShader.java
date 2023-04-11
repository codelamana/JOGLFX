package com.jakob.joglfx.gl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VertexShader extends Shader {

    /**
     * Shader subclass for Vertex Shader
     * @param gl current gl context
     * @param path path to glsl file
     */

    public VertexShader(GL4 gl, String path) {
        super(gl, path);
    }

    /**
     * create shader
     * @return vertex shader id
     */
    @Override
    public int createShader() {

        // setup file stream from glsl file and get shader code
        File vertexShader = new File(pathName);
        System.out.println(vertexShader);

        String shaderCode;
        try (InputStream in = new FileInputStream(vertexShader)) {

            shaderCode = new Scanner(in, StandardCharsets.UTF_8).useDelimiter("\\A").next();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // create vertex shader in GL context
        this.shader = gl.glCreateShader(gl.GL_VERTEX_SHADER);
        gl.glShaderSource(this.shader, 1, new String[] { shaderCode }, null);
        gl.glCompileShader(this.shader);

        // get compile status
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetShaderiv(this.shader, GL2.GL_COMPILE_STATUS, intBuffer);

        // print out info log ig needed
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
        System.out.println("Vertex Shader compiled correctly");
        return this.shader;
    }
}
