package com.jakob.joglfx.gui;

import com.jakob.joglfx.geometry.BufferManager;
import com.jakob.joglfx.geometry.primitives.Cube;
import com.jakob.joglfx.gl.ShaderProgram;
import com.jakob.joglfx.model.Settings;
import com.jakob.joglfx.object.OBJloader;
import com.jakob.joglfx.object.ObjectModel;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import org.joml.Matrix4f;
import org.joml.Vector3f;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private int colorLoc = 0;

    float points[] = {
            0, 0, 0,
            1.0f, 0.0f,  0.0f,
            0.0f, 1.0f,  0.0f
    };

    float colors[] = {
            0.0f,  1f,  0.0f,
            1f, 0f,  0.0f,
            0f, 0f,  1.0f
    };

    FloatBuffer b = Buffers.newDirectFloatBuffer(points);
    FloatBuffer c = Buffers.newDirectFloatBuffer(colors);

    private FloatBuffer mvpBuffer = Buffers.newDirectFloatBuffer(16);
    private Vector3f modelPosition;
    private Matrix4f pMat = new Matrix4f(); // projection matrix
    private Matrix4f vMat = new Matrix4f(); // view matrix
    private Matrix4f mMat = new Matrix4f(); // model matrix
    private Matrix4f mvpMat = new Matrix4f(); // model-view-projection matrix
    private int mvpLoc;
    private float aspect;

    private Vector3f eye = new Vector3f(5,1,5);
    private Vector3f center = new Vector3f(0,0,0);
    private Vector3f up = new Vector3f(0,1,0);

    

    private long k = 0;
    private BufferManager bufferManager;
    private FloatBuffer modelVertexFloatBuffer;
    private FloatBuffer modelNormalFloatBuffer;
    private FloatBuffer modelColorFloatBuffer;
    private Vector3f zaxis;
    private Vector3f xaxis;
    private Vector3f yaxis;
    private Matrix4f view;


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

        bufferManager = new BufferManager();
        //bufferManager.addObject(new Cube(1,1,1, 0.5,0.5,0.5));
        bufferManager.addObject(new Cube(0,0,0, 1,1,1));
        OBJloader loader = new OBJloader("teapot.obj");
        bufferManager.addObject(loader.load());

        this.modelVertexFloatBuffer = bufferManager.getBufferedVertexData();
        this.modelNormalFloatBuffer = bufferManager.getBufferedNormalData();
        this.modelColorFloatBuffer = bufferManager.getBufferedColorData();


        gl.glGenBuffers(3, vbos, 0);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, this.modelVertexFloatBuffer.capacity()* 4L, this.modelVertexFloatBuffer, GL.GL_STATIC_DRAW);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        gl.glBufferData(gl.GL_ARRAY_BUFFER, this.modelColorFloatBuffer.capacity()*4L, this.modelColorFloatBuffer, GL.GL_STATIC_DRAW);

        gl.glGenVertexArrays(1, vaos, 0);
        gl.glBindVertexArray(vaos[0]);
        gl.glEnableVertexAttribArray(this.vaos[0]);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        gl.glVertexAttribPointer(this.colorLoc, 3, GL.GL_FLOAT, false, 0, 0);



        this.vertLoc = gl.glGetAttribLocation(this.programID, "a_vert");
        this.colorLoc = gl.glGetAttribLocation(this.programID, "a_color");


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

        mvpLoc = gl.glGetUniformLocation(this.programID, "mvp");
        // Model matrix

        mvpMat.identity();
        //mMat.rotate((float)(Math.PI/100), 0, 1, 0);
        Matrix4f fl = new Matrix4f();
        eye = new Vector3f((float) (5* Math.cos(Math.PI * k/100)), 5f, (float) (5* Math.sin(Math.PI * k/100)));
        view.lookAt(eye, center, up, fl);

        mvpMat.mul(fl);
        //mvpMat.mul(vMat);
        mvpMat.mul(mMat);
        k++;


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