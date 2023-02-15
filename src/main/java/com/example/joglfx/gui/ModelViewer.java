package com.example.joglfx.gui;

import com.example.joglfx.geometry.BufferManager;
import com.example.joglfx.geometry.primitives.Cube;
import com.example.joglfx.geometry.primitives.Plane;
import com.example.joglfx.gl.ShaderProgram;
import com.example.joglfx.model.Settings;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;



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
    private double[] center = new double[]{0,0,0};
    private double[] up = new double[]{0,1,0};

    private int programID;

    //ObjectModel model;

    private int[] vaos = new int[1];
    private int[] vbos = new int[3];

    int vertLoc = 0;
    int normLoc = 0;
    int colorLoc;

    //FloatBuffer data; // = Buffers.newDirectFloatBuffer(vertex_data);

    //OBJloader loader = new OBJloader("src/testProject/teapot.obj");
    //ObjectModel model = loader.load();

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
     * @param gl2 gl context
     * @param width width of viewport
     * @param height height of viewport
     */
    protected void setup(GL2 gl2, int width, int height) {

        bufferManager = new BufferManager();
        //bufferManager.addObject(new Cube(1,1,1, 0.5,0.5,0.5));
        bufferManager.addObject(new Cube(-1,-1,-1, 0.5,0.5,0.5));
        bufferManager.addObject(new Plane(-2, 2,2,
                                            -2,-2, 2,
                                            -2,2,-2,
                                            -2, -2,-2));

        Plane p = new Plane(0.5f,0,0, 0,1,0, 1,0,0, 1,1,0);
        //bufferManager.addObject(p);

        this.modelVertexFloatBuffer = bufferManager.getBufferedVertexData();
        this.modelNormalFloatBuffer = bufferManager.getBufferedNormalData();
        this.modelColorFloatBuffer = bufferManager.getBufferedColorData();

        //Plane p = new Plane(0,0,0, 0,1,0, 1,0,0, 1,1,0);
        //BufferManager.printFloatBuffer(p.getBufferedVertexData());

        gl2.glLoadIdentity();

        // get coordinates for eye, center and up vector from settings
        this.eye = this.settingsContext.getGlCoordinates().get("eye");
        this.up = this.settingsContext.getGlCoordinates().get("up");
        this.center = this.settingsContext.getGlCoordinates().get("center");


        // setup camera matrix using glu
        this.glu = new GLU();
        glu.gluPerspective(65, (width / height) >= 1 ? (width / height): (height/width), 0.1, 500.0);
        glu.gluLookAt(eye[0], eye[1], eye[2], center[0], center[1], center[2], up[0], up[1], up[2]);


        // set vertex and normal buffer
        this.modelVertexFloatBuffer = bufferManager.getBufferedVertexData();
        this.modelNormalFloatBuffer = bufferManager.getBufferedNormalData(); // generate vertexBuffer
        this.modelColorFloatBuffer = bufferManager.getBufferedColorData();

        gl2.glGenVertexArrays(1, vaos, 0);
        gl2.glBindVertexArray(vaos[0]);

        gl2.glGenBuffers(3, vbos, 0);

        gl2.glBindBuffer(gl2.GL_ARRAY_BUFFER, vbos[0]);
        gl2.glBufferData(GL.GL_ARRAY_BUFFER, modelVertexFloatBuffer.capacity()*4, this.modelVertexFloatBuffer, GL.GL_STATIC_DRAW);

        gl2.glBindBuffer(gl2.GL_ARRAY_BUFFER, vbos[1]);
        gl2.glBufferData(GL.GL_ARRAY_BUFFER, this.modelNormalFloatBuffer.capacity()*4, this.modelNormalFloatBuffer, GL.GL_STATIC_DRAW);

        gl2.glBindBuffer(gl2.GL_ARRAY_BUFFER, vbos[2]);
        gl2.glBufferData(GL.GL_ARRAY_BUFFER, this.modelColorFloatBuffer.capacity()*4, this.modelColorFloatBuffer, GL.GL_STATIC_DRAW);

        this.vertLoc = gl2.glGetAttribLocation(this.programID, "a_vert");
        this.normLoc = gl2.glGetAttribLocation(this.programID, "a_norm");
        this.colorLoc = gl2.glGetAttribLocation(this.programID, "a_col");

        // change gl settings
        gl2.glEnable(gl2.GL_DEPTH_TEST);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glEnable(gl2.GL_LIGHTING);
    }

    /**
     * rendering function of modelviewer. this function is called repeatedly
     *
     * @param gl2 current gl context
     * @param width width of viewport
     * @param height height of viewport
     */
    protected  void render(GL2 gl2, int width, int height) {
        // reset screen
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);


        gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, vbos[0]);
        gl2.glVertexAttribPointer(this.vertLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl2.glEnableVertexAttribArray(this.vertLoc);

        gl2.glBindBuffer(gl2.GL_ARRAY_BUFFER, vbos[1]);
        gl2.glVertexAttribPointer(this.normLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl2.glEnableVertexAttribArray(this.normLoc);

        gl2.glBindBuffer(gl2.GL_ARRAY_BUFFER, vbos[2]);
        gl2.glVertexAttribPointer(this.colorLoc, 3, GL.GL_FLOAT, false, 0, 0);
        gl2.glEnableVertexAttribArray(this.colorLoc);

        // activate shader program, must be called everytime render is called
        gl2.glUseProgram(programID);

        gl2.glRotatef(1, 0, 1, 0);

        //gl2.glColor3d(0.6, 0.1, 0);

        gl2.glDrawArrays(GL.GL_TRIANGLES, 0, bufferManager.numberOfFaces()*4);

        gl2.glDisableVertexAttribArray(this.vertLoc);
        gl2.glDisableVertexAttribArray(this.normLoc);
        gl2.glDisableVertexAttribArray(this.colorLoc);

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
        GL2 gl = drawable.getGL().getGL2();

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
        GL2 gl = drawable.getGL().getGL2();
        gl.glDeleteProgram(programID);
        gl.glDeleteBuffers(2, vbos, 0);
    }

    /**
     * This function is called when view is refreshed, every frame
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        render( drawable.getGL().getGL2(), drawable.getSurfaceWidth(), drawable.getSurfaceHeight() );
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
        this.setup(drawable.getGL().getGL2(), width, height );
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