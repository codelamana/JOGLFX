package com.jakob.joglfx.gl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;

public class Shader {
    /**
     * Super class for shaders, stores path to shader glsl file and shader id for later use
     */

    int shader;
    String pathName;
    GL4 gl;

    /**
     * set gl context and path file
     * @param gl gl context of app
     * @param path path to shader file
     */
    public Shader(GL4 gl, String path) {
        this.pathName = path;
        this.gl = gl;
        this.shader = 0;
    }

    /**
     * Template function for creating a shader, has to be overridden by sub classes to work properly
     * @return
     */
    public int createShader(){return 0;}
}
