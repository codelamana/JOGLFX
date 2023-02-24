package com.example.joglfx.gui;

import com.example.joglfx.geometry.BufferManager;
import com.example.joglfx.geometry.primitives.Cube;
import com.example.joglfx.geometry.primitives.Plane;
import com.example.joglfx.gl.ShaderProgram;
import com.example.joglfx.model.Settings;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import org.joml.Matrix4f;
import org.joml.Vector3f;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.Buffer;
import java.nio.FloatBuffer;

public class ModelViewer extends GLJPanel implements GLEventListener, KeyListener {
    /**
     * Main Model View, OGL drawing takes place here, as well as animation
     * and response for key events
     *
     * All Viewing related stuff is accessed over this class
     */

    private FPSAnimator animator;

    private Settings settingsContext;
    private int programID;

    //ObjectModel model;

    private int[] vaos = new int[1];
    private int[] vbos = new int[3];

    int vertLoc = 0;

    float points[] = {
            0.0f,  0.5f,  0.0f,
            0.5f, -0.5f,  0.0f,
            -0.5f, -0.5f,  0.0f
    };

    FloatBuffer b = Buffers.newDirectFloatBuffer(points);


    /**
     * Initialize ModelViewer Object, set GL context, add GLUT and Animator to Frame
     * @param userCapsRequest
     * @throws GLException
     */
    public ModelViewer(GLCapabilitiesImmutable userCapsRequest) throws GLException {
        super(userCapsRequest);
        this.addGLEventListener(this);
        this.animator = new FPSAnimator(this, 35);

    }

    /**
     * Setup GL related stuff
     * @param gl gl context
     * @param width width of viewport
     * @param height height of viewport
     */
    protected void setup(GL4 gl, int x, int y, int width, int height) {


        gl.glGenBuffers(3, vbos, 0);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, b.capacity()* 4L, this.b, GL.GL_STATIC_DRAW);

        gl.glGenVertexArrays(1, vaos, 0);
        gl.glBindVertexArray(vaos[0]);
        gl.glEnableVertexAttribArray(this.vaos[0]);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);



        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);


        this.vertLoc = gl.glGetAttribLocation(this.programID, "a_vert");

        // change gl settings
        gl.glEnable(gl.GL_DEPTH_TEST);

    }

    /**
     * rendering function of modelviewer. this function is called repeatedly
     *
     * @param gl current gl context
     * @param width width of viewport
     * @param height height of viewport
     */
    protected  void render(GL4 gl, int width, int height) {
        // reset screen
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glUseProgram(programID);
        gl.glBindVertexArray(vaos[0]);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[0]);
        gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(this.vertLoc);

        gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);

        gl.glDisableVertexAttribArray(this.vertLoc);

    }

    /**
     * Public function for starting animation
     */
    public void startAnimation(){
        this.animator.start();
    }

    /**
     * Public function for stopping animation
     */
    public void stopAnimation(){
        this.animator.stop();
    }

    /**
     * Public function for toggling animation state
     */
    public void toggleAnimation(){
        if(this.animator.isAnimating()) this.animator.stop();
        else this.animator.start();
    }

    /**
     * Init function for GL, this is called in the beginning to setup everything gl related
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        // setup shader program and add shader stages
        ShaderProgram p = new ShaderProgram(gl);
        p.addVertexShader(settingsContext.getGlFiles().get("vertexShader"));
        p.addFragmentShader(settingsContext.getGlFiles().get("fragmentShader"));
        this.programID = p.createProgram();

    }

    /**
     * Function is called when GLJPane is destroyed. Dispose the shaders
     * @param drawable
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glDeleteProgram(programID);
        gl.glDeleteBuffers(2, vbos, 0);
    }

    /**
     * This function is called when view is refreshed, every frame
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        render( drawable.getGL().getGL4(), drawable.getSurfaceWidth(), drawable.getSurfaceHeight() );
    }

    /**
     * Called after init and everytime the GLJPanel is resized
     * @param drawable
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.setup(drawable.getGL().getGL4(), x, y, width, height );
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if(e.getKeyChar() == ' ') this.toggleAnimation();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setSettingsContext(Settings s){
        this.settingsContext = s;
    }
}