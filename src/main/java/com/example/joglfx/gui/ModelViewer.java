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


    private GLUT glut;
    private GLU glu;
    private FPSAnimator animator;

    private Settings settingsContext;

    private double[] eye = new double[]{10,10,10};

    private int programID;

    //ObjectModel model;

    private int[] vaos = new int[1];
    private int[] vbos = new int[3];

    int vertLoc = 0;
    int normLoc = 0;
    int colorLoc = 0;

    private FloatBuffer mvpBuffer = Buffers.newDirectFloatBuffer(16);
    private Vector3f modelPosition = new Vector3f(0,0,0);
    private Matrix4f pMat = new Matrix4f(); // projection matrix
    private Matrix4f vMat = new Matrix4f(); // view matrix
    private Matrix4f mMat = new Matrix4f(); // model matrix
    private Matrix4f mvpMat = new Matrix4f(); // model-view-projection matrix
    private int mvpLoc;
    private float aspect;

    BufferManager bufferManager;

    //Cube c;

    FloatBuffer modelVertexFloatBuffer;
    FloatBuffer modelNormalFloatBuffer;
    FloatBuffer modelColorFloatBuffer;


    /**
     * Initialize ModelViewer Object, set GL context, add GLUT and Animator to Frame
     * @param userCapsRequest
     * @throws GLException
     */
    public ModelViewer(GLCapabilitiesImmutable userCapsRequest) throws GLException {
        super(userCapsRequest);
        this.addGLEventListener(this);
        this.glut =  new GLUT();
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
        bufferManager.addObject(new Cube(0,0,0, 0.5,0.5,0.5));
        bufferManager.addObject(new Plane(-2, 2,2,
                                            -2,-2, 2,
                                            -2,2,-2,
                                            -2, -2,-2));

        

        this.modelVertexFloatBuffer = bufferManager.getBufferedVertexData();
        this.modelNormalFloatBuffer = bufferManager.getBufferedNormalData();
        this.modelColorFloatBuffer = bufferManager.getBufferedColorData();

        //Plane p = new Plane(0,0,0, 0,1,0, 1,0,0, 1,1,0);
        BufferManager.printFloatBuffer(this.modelVertexFloatBuffer);

        gl.glViewport(x,y,width, height);

        aspect = (float) width / (float) height;
        System.out.println("Aspect" + aspect);
        pMat.identity().setPerspective((float) Math.toRadians(65.0f), aspect, 0.1f, 1000.0f);

        mvpLoc = gl.glGetUniformLocation(programID, "mvp");

        // View matrix
        vMat.identity();
        vMat.setTranslation(0f, 0f, -5);
        vMat.setRotationXYZ(0,0,0);
        // Model matrix
        mMat.identity().setTranslation(0,0,0);
        // Model-View-Projection matrix
        mvpMat.identity();
        mvpMat.mul(pMat);
        mvpMat.mul(vMat);
        mvpMat.mul(mMat);
        // Uniform location

        gl.glUniformMatrix4fv(mvpLoc, 1, false, mvpMat.get(mvpBuffer));

        gl.glGenVertexArrays(1, vaos, 0);
        gl.glBindVertexArray(vaos[0]);

        gl.glGenBuffers(3, vbos, 0);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, modelVertexFloatBuffer.capacity()*4, this.modelVertexFloatBuffer, GL.GL_STATIC_DRAW);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, this.modelNormalFloatBuffer.capacity()*4, this.modelNormalFloatBuffer, GL.GL_STATIC_DRAW);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[2]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, this.modelColorFloatBuffer.capacity()*4, this.modelColorFloatBuffer, GL.GL_STATIC_DRAW);

        this.vertLoc = gl.glGetAttribLocation(this.programID, "a_vert");
        this.normLoc = gl.glGetAttribLocation(this.programID, "a_norm");
        this.colorLoc = gl.glGetAttribLocation(this.programID, "a_col");

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

        gl.glBindVertexArray(vaos[0]);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[0]);
        gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(this.vertLoc);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        gl.glVertexAttribPointer(this.normLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(this.normLoc);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[2]);
        gl.glVertexAttribPointer(this.colorLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(this.colorLoc);

        mvpLoc = gl.glGetUniformLocation(programID, "mvp");

        mvpMat.identity();
        mvpMat.mul(pMat);
        mvpMat.mul(vMat);
        mvpMat.mul(mMat);

        gl.glUniformMatrix4fv(mvpLoc, 1, false, mvpMat.get(mvpBuffer));

        // activate shader program, must be called everytime render is called
        gl.glUseProgram(programID);


        //gl.glColor3d(0.6, 0.1, 0);
        System.out.println(bufferManager.getBufferedVertexData().capacity() + " " + bufferManager.numberOfFaces());

        gl.glDrawArrays(GL.GL_TRIANGLES, 0, bufferManager.numberOfFaces()*4);

        gl.glDisableVertexAttribArray(this.vertLoc);
        gl.glDisableVertexAttribArray(this.normLoc);
        gl.glDisableVertexAttribArray(this.colorLoc);

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