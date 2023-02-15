package com.example.joglfx;

import com.example.joglfx.gl.VertexShader;
import com.example.joglfx.gui.ModelViewer;
import com.example.joglfx.model.Settings;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.embed.swing.SwingNode;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable, GLEventListener {
    @FXML
    private Label welcomeText;

    @FXML
    SwingNode glcanvas;

    @FXML
    Menu fileMenu;

    @FXML
    Button animatorToggle;

    private GLU glu = new GLU();

    ModelViewer modelViewer;
    Settings settings;


    @FXML
    public void onToggleAnimation(){
        modelViewer.toggleAnimation();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final GLProfile glProfile = GLProfile.getDefault();
        final GLCapabilities capabilities = new GLCapabilities(glProfile);

        //GLJPanel canvas = new GLJPanel(capabilities);


        //canvas.addGLEventListener(this);

        modelViewer = new ModelViewer(capabilities);
        this.settings = new Settings();
        settings.addModelViewer(modelViewer);
        modelViewer.setSettingsContext(settings);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                glcanvas.setContent(modelViewer);

            }
        });
    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        // Clear The Screen And The Depth Buffer
        gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        //gl.glLoadIdentity(); // Reset The View

        gl.glRotatef(1f, 0,1,0);

        gl.glBegin( GL2.GL_TRIANGLES );

        //drawing triangle in all dimensions
        // Front
        gl.glColor3f( 1.0f, 0.0f, 0.0f ); // Red
        gl.glVertex3f( 1.0f, 0.0f, 0.0f ); // Top Of Triangle (Front)

        gl.glColor3f( 0.0f, 1.0f, 0.0f ); // Green
        gl.glVertex3f( 0.0f, 1.0f, 0.0f ); // Left Of Triangle (Front)

        gl.glColor3f( 0.0f, 0.0f, 1.0f ); // Blue
        gl.glVertex3f( 0.0f, 0.0f, 1.0f ); // Right Of Triangle (Front)


        gl.glEnd(); // Done Drawing 3d triangle (Pyramid)
        gl.glFlush();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();

        System.out.println(width + " " + height);

        gl.glLoadIdentity();


        // setup camera matrix using glu
        this.glu = new GLU();
        glu.gluPerspective(75, (width / height) >= 1 ? (width / height): (height/width), 0.1, 500.0);
        glu.gluLookAt(5,5,5,0,0,0,0,1,0);


        // change gl settings
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        //gl.glEnable(gl.GL_LIGHTING);
    }
}
