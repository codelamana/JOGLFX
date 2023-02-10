package com.example.joglfx;

import com.example.joglfx.gui.ModelViewer;
import com.example.joglfx.model.Settings;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author Jakob Schmid
 */

public class MainWindow extends JFrame {

    /**
     * Main window of application. Structures all needed settings panes and the main Model Viewer.
     */

    // main components of application
    ModelViewer model;
    JMenuBar menu;
    JPanel left_settings;
    JPanel right_settings;
    JPanel status_bar;

    Settings settings;

    /**
     * initialize MainWindow and invoke all setup components.
     * Settings Context is set for all components
     * @throws HeadlessException
     */
    MainWindow() throws HeadlessException {
        super();
        this.setLayout(new BorderLayout());

        // setup all Components
        setupModelViewer();
        setupMenuBar();

        // create settings
        this.settings = new Settings();
        this.settings.addModelViewer(this.model);

        // add settings context to all components
        this.model.setSettingsContext(this.settings);

        // add keylistener if needed
        this.addKeyListener(this.model);
        this.model.addKeyListener(this.model);

        // make window visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setTitle("JOGL");
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

    }

    /**
     * Set up MenuBar and add to MainWindow
     */
    private void setupMenuBar() {
        this.menu = new JMenuBar();
        JMenu men = new JMenu("File");
        this.menu.add(men);
        this.setJMenuBar(this.menu);
    }

    /**
     * Set up ModelView and add to MainWindow
     */
    private void setupModelViewer(){
        // get gl context
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );

        // initialize model viewer with gl context
        this.model = new ModelViewer(glcapabilities);

        // add model to main window
        this.getContentPane().add(this.model, BorderLayout.CENTER);
        this.model.startAnimation();
    }


}
