package com.jakob.joglfx;

import com.jakob.joglfx.gui.GlSettingsPaneBuilder;
import com.jakob.joglfx.gui.ModelViewer;
import com.jakob.joglfx.model.Settings;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.VBox;


import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    SwingNode glcanvas;

    @FXML
    Button animatorToggle;

    @FXML
    SplitPane splitPane;

    @FXML
    VBox glSettingsContainer;

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
                modelViewer.startAnimation();
            }
        });

        splitPane.setDividerPositions(0.2, 0.8);

        GlSettingsPaneBuilder builder = new GlSettingsPaneBuilder();

        builder.addTestItems();
        builder.populatePane(glSettingsContainer);
    }



}
