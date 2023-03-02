package com.jakob.joglfx;

import com.jakob.joglfx.gui.SettingsPaneBuilder;
import com.jakob.joglfx.gui.ModelViewer;

import com.jakob.joglfx.model.SettingNode;
import com.jakob.joglfx.model.settingsitems.FloatSettingsItem;
import com.jogamp.opengl.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.VBox;


import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import static com.jakob.joglfx.model.settingsitems.FloatSettingsItem.*;

public class MainWindowController implements Initializable {

    @FXML
    SwingNode glcanvas;

    @FXML
    Button animatorToggle;

    @FXML
    SplitPane splitPane;

    @FXML
    VBox glSettingsContainer;

    ModelViewer modelViewer;



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


        SwingUtilities.invokeLater(() -> {
            glcanvas.setContent(modelViewer);
            modelViewer.startAnimation();
        });

        splitPane.setDividerPositions(0.2, 0.8);

        SettingNode settingsRoot = new SettingNode("Main Container", null, SettingNode.SettingsType.CONTAINER);
        SettingNode glSettings = new SettingNode("GL Setting Container", null, SettingNode.SettingsType.CONTAINER);
        settingsRoot.addNode(glSettings);


        SettingNode framerateNode = new SettingNode("Framerate", new FloatSettingsItem("Framerate", FloatSettingType.SLIDER), SettingNode.SettingsType.INT);
        framerateNode.bindToProperty(modelViewer.getFramerateProperty());
        glSettings.addNode(framerateNode);

        SettingNode eyeVectorNode = new SettingNode("Eye", null, SettingNode.SettingsType.CONTAINER);

        SettingNode eyeVectorX = new SettingNode("Eye X", new FloatSettingsItem("Eye X", FloatSettingType.SPINNER), SettingNode.SettingsType.INT);
        eyeVectorX.bindToProperty(modelViewer.getEyeXProperty());
        eyeVectorNode.addNode(eyeVectorX);

        SettingNode eyeVectorY = new SettingNode("Eye Y", new FloatSettingsItem("Eye Y", FloatSettingType.SPINNER), SettingNode.SettingsType.INT);
        eyeVectorY.bindToProperty(modelViewer.getEyeYProperty());
        eyeVectorNode.addNode(eyeVectorY);

        SettingNode eyeVectorZ = new SettingNode("Eye Z", new FloatSettingsItem("Eye Z", FloatSettingType.SPINNER), SettingNode.SettingsType.INT);
        eyeVectorZ.bindToProperty(modelViewer.getEyeZProperty());
        eyeVectorNode.addNode(eyeVectorZ);

        glSettings.addNode(eyeVectorNode);


        SettingsPaneBuilder builder = new SettingsPaneBuilder();
        builder.populatePane(glSettingsContainer, glSettings);
    }

}
