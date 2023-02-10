package com.example.joglfx.gl;

import com.example.joglfx.MainApp;
import com.example.joglfx.MainWindowController;
import com.example.joglfx.gui.ModelViewer;
import com.jogamp.opengl.GL2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

public class VertexShader extends Shader {

    /**
     * Shader subclass for Vertex Shader
     * @param gl current gl context
     * @param path path to glsl file
     */

    public VertexShader(GL2 gl, String path) {
        super(gl, path);
    }

    /**
     * create shader
     * @return vertex shader id
     */
    @Override
    public int createShader() {

        // setup file stream from glsl file and get shader code
        File vertexShader = new File("vertex_shader.glsl");
        System.out.println(vertexShader);
        InputStream in = MainApp.class.getClassLoader().getResourceAsStream(vertexShader.toURI().getPath());


        if(vertexShader.exists() && !vertexShader.isDirectory()) {
            System.out.println("Datei existiert nicht");
        }

        String shaderCode = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

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
        return this.shader;
    }
}
