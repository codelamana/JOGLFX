package com.jakob.joglfx.gui;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.gl.ShaderProgram;
import com.jakob.joglfx.model.scene.SceneModel;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.FloatBuffer;
import java.util.HashSet;

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
    private SimpleObjectProperty<Vector3f> eye;
    private SimpleObjectProperty<Vector3f> center;
    private SimpleObjectProperty<Vector3f> up ;

    SceneModel sceneModel;
    HashSet<GeometryObject> objectsToDraw;


    // buffer Manager and buffers for loading into GL
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
    public ModelViewer(GLCapabilitiesImmutable userCapsRequest, SceneModel sceneModel) throws GLException {
        super(userCapsRequest);
        this.addGLEventListener(this);
        this.animator = new FPSAnimator(this, 5);

        this.sceneModel = sceneModel;

        this.objectsToDraw = sceneModel.getObjects();
        System.out.println(objectsToDraw);

        this.eye = new SimpleObjectProperty<>(sceneModel.getCamera().getEye());
        this.center = new SimpleObjectProperty<>(sceneModel.getCamera().getCenter());
        this.up = new SimpleObjectProperty<>(sceneModel.getCamera().getUp());

        this.eye.bindBidirectional(sceneModel.getCamera().eyeProperty());
        this.center.bind(sceneModel.getCamera().centerProperty());
        this.center.bind(sceneModel.getCamera().upProperty());

        // add ChangeListeners to all properties
        this.framerate.addListener((observableValue, number, t1) -> setFramerate(t1.intValue()));
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

        // Generate Buffers for Vertex, Normal and Color Data
        gl.glGenBuffers(3, vbos, 0);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[0]);
        //gl.glBufferData(GL.GL_ARRAY_BUFFER, this.modelVertexFloatBuffer.capacity() * 4L, this.modelVertexFloatBuffer, GL.GL_STATIC_DRAW);

        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbos[1]);
        //gl.glBufferData(gl.GL_ARRAY_BUFFER, this.modelColorFloatBuffer.capacity() * 4L, this.modelColorFloatBuffer, GL.GL_STATIC_DRAW);

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

        gl.glBindVertexArray(vaos[0]);

        aspect = (float) width / (float) height;
        view = new Matrix4f().perspective((float) Math.toRadians(60.0f), aspect, 0.1f, 1000.0f);

        Matrix4f fl = new Matrix4f();
        view.lookAt(eye.get(), center.get(), up.get(), fl);

        for(GeometryObject currentObject : this.objectsToDraw) {

            //calculate View-Perspective Part
            // adding the model matrix
            mvpMat.identity();
            mvpMat.mul(fl);
            Matrix4f mMat = currentObject.getModelMatrix().rotate(new Quaternionf().rotationXYZ((float)(Math.PI * k/ 100),
                                                                                                (float)(Math.PI * k/ 50),
                                                                                                (float)(Math.PI * k/ 20)));
            mvpMat.mul(mMat);

            gl.glUniformMatrix4fv(mvpLoc, 1, false, mvpMat.get(mvpBuffer));

            gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[0]);
            gl.glBufferData(GL.GL_ARRAY_BUFFER, currentObject.getBufferedVertexData().capacity() * 4L, currentObject.getBufferedVertexData(), GL.GL_STATIC_DRAW);
            gl.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(this.vertLoc);

            gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[1]);
            gl.glBufferData(GL.GL_ARRAY_BUFFER, currentObject.getBufferedColorData().capacity() * 4L, currentObject.getBufferedColorData(), GL.GL_STATIC_DRAW);
            gl.glVertexAttribPointer(this.colorLoc, 3, GL.GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(this.colorLoc);

            gl.glDrawArrays(GL.GL_TRIANGLES, 0, currentObject.getFaces().size() * 3);

        }

        gl.glDisableVertexAttribArray(this.vertLoc);
        gl.glDisableVertexAttribArray(this.colorLoc);
        k++;

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



    /**
     * Init function for GL, this is called in the beginning to set up everything gl related
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