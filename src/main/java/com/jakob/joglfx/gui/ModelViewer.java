package com.jakob.joglfx.gui;

import com.jakob.joglfx.geometry.BufferManager;
import com.jakob.joglfx.geometry.primitives.Cube;
import com.jakob.joglfx.gl.ShaderProgram;
import com.jakob.joglfx.object.OBJloader;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import javafx.beans.property.SimpleDoubleProperty;
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

    // Frame Animator and frame counter
    private FPSAnimator animator;
    private long k = 0;

    // Program pointer
    private int programID;

    // VAO and VBO buffer pointers
    private int[] vaos = new int[1];
    private int[] vbos = new int[3];


    private int vertLoc = 0;
    private int colorLoc = 0;

    // Model View Projection GL matrix pointer
    private int mvpLoc;

    // buffer for mvp
    private FloatBuffer mvpBuffer = Buffers.newDirectFloatBuffer(16);

    private Vector3f modelPosition;
    private Matrix4f mMat = new Matrix4f(); // model matrix
    private Matrix4f mvpMat = new Matrix4f(); // model-view-projection matrix
    private float aspect;

    // Look-at variables
    private Vector3f eye = new Vector3f(5,1,5);
    private Vector3f center = new Vector3f(0,0,0);
    private Vector3f up = new Vector3f(0,1,0);


    // buffer Manager and buffers for loading into GL
    private BufferManager bufferManager;
    private FloatBuffer modelVertexFloatBuffer;
    private FloatBuffer modelNormalFloatBuffer;
    private FloatBuffer modelColorFloatBuffer;
    private Matrix4f view;

    // properties for changing variables from outside the class
    private SimpleDoubleProperty framerate = new SimpleDoubleProperty(50);
    private SimpleDoubleProperty eyeXProperty = new SimpleDoubleProperty();
    private SimpleDoubleProperty eyeYProperty = new SimpleDoubleProperty();
    private SimpleDoubleProperty eyeZProperty = new SimpleDoubleProperty();


    /**
     * Initialize ModelViewer Object, set GL context, add Animator to Frame
     * @param userCapsRequest
     * @throws GLException
     */
    public ModelViewer(GLCapabilitiesImmutable userCapsRequest) throws GLException {
        super(userCapsRequest);
        this.addGLEventListener(this);
        this.animator = new FPSAnimator(this, 35);

        // add ChangeListeners to all properties
        this.framerate.addListener((observableValue, number, t1) -> setFramerate(t1.intValue()));
        this.eyeXProperty.addListener((observableValue, number, t1) -> setEyeX(t1.floatValue()));
        this.eyeYProperty.addListener((observableValue, number, t1) -> setEyeY(t1.floatValue()));
        this.eyeZProperty.addListener((observableValue, number, t1) -> setEyeZ(t1.floatValue()));
    }

    /**
     * Setup GL related stuff
     * @param gl gl context
     * @param width width of viewport
     * @param height height of viewport
     */
    protected void setup(GL4 gl, int x, int y, int width, int height) {

        // setup objects in Buffer Manager
        //OBJloader loader = new OBJloader("teapot.obj");
        //bufferManager.addObject(loader.load());

        // load Data into Buffers
        this.modelVertexFloatBuffer = bufferManager.getBufferedVertexData();
        this.modelNormalFloatBuffer = bufferManager.getBufferedNormalData();
        this.modelColorFloatBuffer = bufferManager.getBufferedColorData();

        // Generate Buffers for Vertex, Normal and Color Data
        gl.glGenBuffers(3, vbos, 0);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, this.modelVertexFloatBuffer.capacity()* 4L, this.modelVertexFloatBuffer, GL.GL_STATIC_DRAW);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        gl.glBufferData(gl.GL_ARRAY_BUFFER, this.modelColorFloatBuffer.capacity()*4L, this.modelColorFloatBuffer, GL.GL_STATIC_DRAW);

        // Generate one VAO for the three VBOs
        gl.glGenVertexArrays(1, vaos, 0);
        gl.glBindVertexArray(vaos[0]);
        gl.glEnableVertexAttribArray(this.vaos[0]);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        gl.glVertexAttribPointer(this.colorLoc, 3, GL.GL_FLOAT, false, 0, 0);


        // get locations for passing data to GLSL shaders
        this.vertLoc = gl.glGetAttribLocation(this.programID, "a_vert");
        this.colorLoc = gl.glGetAttribLocation(this.programID, "a_color");

        // Setup MVP Matrix
        mMat.identity();
        aspect = (float) width / (float) height;
        view = new Matrix4f().perspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

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

        // load location for passing mvp to GLSL Vertex Shader
        mvpLoc = gl.glGetUniformLocation(this.programID, "mvp");

        // Rotate Object
        mMat.identity();
        mMat.rotate((float) (Math.PI * k/ 100), 0,1,0);
        k++;

        //calculate View-Perspective Part
        aspect = (float) width / (float) height;
        view = new Matrix4f().perspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

        Matrix4f fl = new Matrix4f();
        view.lookAt(eye, center, up, fl);

        mvpMat.identity();
        mvpMat.mul(fl);
        mvpMat.mul(mMat);


        gl.glUniformMatrix4fv(mvpLoc, 1, false, mvpMat.get(mvpBuffer));

        gl.glBindVertexArray(vaos[0]);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[0]);
        gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(this.vertLoc);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[1]);
        gl.glVertexAttribPointer(this.colorLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(this.colorLoc);

        gl.glDrawArrays(GL.GL_TRIANGLES, 0, this.modelColorFloatBuffer.limit()/3);

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

    public void setFramerate(int fps){
        if(this.animator.isAnimating()) {
            this.animator.stop();
            this.animator.setFPS(fps);
            this.animator.start();
        } else {
            this.animator.setFPS(fps);
        }
    }

    public SimpleDoubleProperty getFramerateProperty(){
        return this.framerate;
    }


    public void setEyeX(float newX){
        this.eye.x = newX;
        System.out.println("New Eye Value is " + this.eye.x());
    }

    public void setEyeY(float newY){
        this.eye.y = newY;
        System.out.println("New Eye Value is " + this.eye.y());
    }

    public void setEyeZ(float newZ){
        this.eye.z = newZ;
        System.out.println("New Eye Value is " + this.eye.z());
    }

    public SimpleDoubleProperty getEyeXProperty(){
        return this.eyeXProperty;
    }
    public SimpleDoubleProperty getEyeYProperty(){
        return this.eyeYProperty;
    }
    public SimpleDoubleProperty getEyeZProperty(){
        return this.eyeZProperty;
    }

    public void setBufferManager(BufferManager bufferManager){
        this.bufferManager = bufferManager;
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
        p.addVertexShader("vertex_shader.glsl");
        p.addFragmentShader("fragment_shader.glsl");
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

}