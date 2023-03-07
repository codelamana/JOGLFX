package com.jakob.joglfx;

import com.jakob.joglfx.geometry.BufferManager;
import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.primitives.Cube;
import com.jakob.joglfx.gui.SettingsPaneBuilder;
import com.jakob.joglfx.gui.ModelViewer;

import com.jakob.joglfx.model.SettingNode;
import com.jakob.joglfx.model.settingsitems.ContainerItem;
import com.jakob.joglfx.model.settingsitems.FloatSettingsItem;
import com.jakob.joglfx.object.OBJloader;
import com.jogamp.opengl.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.jakob.joglfx.model.SettingNode.*;
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

    @FXML
    TreeTableView<GeometryObject> objectTreeTable;

    ModelViewer modelViewer;

    BufferManager bufferManager;


    @FXML
    public void onToggleAnimation(){
        modelViewer.toggleAnimation();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final GLProfile glProfile = GLProfile.getDefault();
        final GLCapabilities capabilities = new GLCapabilities(glProfile);

        splitPane.setDividerPositions(0.2, 0.85);

        bufferManager = new BufferManager();

        ArrayList<GeometryObject> roots = loadGeometryObjects();

        bufferManager.addObjects(roots);

        modelViewer = new ModelViewer(capabilities);
        modelViewer.setBufferManager(bufferManager);

        setupObjectTreeTableView(bufferManager);

        SwingUtilities.invokeLater(() -> {
            glcanvas.setContent(modelViewer);
            modelViewer.startAnimation();
        });

        SettingNode settingsRoot = new SettingNode("Main Container", null, SettingsType.CONTAINER);

        SettingNode glSettings = new SettingNode("GL Setting Container", new ContainerItem("GL Settings"), SettingsType.CONTAINER);
        settingsRoot.addNode(glSettings);


        SettingNode framerateNode = createSingleSettingNode("Framerate", modelViewer.getFramerateProperty());
        glSettings.addNode(framerateNode);


        SettingNode eyeVectorNode = createVectorSettingNode("Eye", modelViewer.getEyeXProperty(),
                modelViewer.getEyeYProperty(),
                modelViewer.getEyeZProperty());
        glSettings.addNode(eyeVectorNode);

        SettingNode centerVectorNode = createVectorSettingNode("Center", modelViewer.getEyeXProperty(),
                modelViewer.getEyeYProperty(),
                modelViewer.getEyeZProperty());
        glSettings.addNode(centerVectorNode);

        SettingsPaneBuilder.populatePane(glSettingsContainer, glSettings);
        glSettingsContainer.setPadding(new Insets(3));

    }

    private ArrayList<GeometryObject> loadGeometryObjects() {

        GeometryObject root1 = new GeometryObject("Würfel");
        GeometryObject c1 = new GeometryObject("Container");
        c1.addChild(new Cube("Another Cube", 2,2,2, 0.25, 0.4, 1), new Cube("W1", 0,0,0, 1,1,1));
        root1.addChild(c1);
        root1.addChild(new Cube( "W2", 1,1,1, 0.5,0.5,0.5));

        GeometryObject root2 = new GeometryObject("Andere Würfel");
        root2.addChild(new Cube("dritter Würfel", -1,1,1, 0.25, 0.25, 0.25));
        root2.addChild(new Cube( "W4", 1,-1,1, 0.5,0.5,0.5));
        OBJloader obJloader = new OBJloader("teapot.obj");
        //root2.addChild(obJloader.load());

        ArrayList<GeometryObject> temp = new ArrayList<>();
        temp.add(root1);
        temp.add(root2);

        return temp;

    }

    public void setupObjectTreeTableView(BufferManager bufferManager){

        objectTreeTable.setShowRoot(true);

        TreeTableColumn<GeometryObject, String> nameColumn = new TreeTableColumn<>("Name");
        TreeTableColumn<GeometryObject, Integer> verticesColumn = new TreeTableColumn<>("#Faces");

        nameColumn.setMinWidth(100);
        verticesColumn.setMinWidth(50);

        objectTreeTable.getColumns().add(nameColumn);
        objectTreeTable.getColumns().add(verticesColumn);

        ArrayList<GeometryObject> objects = bufferManager.getObjects();

        GeometryObject rootGeometryObjectNode = new GeometryObject("Projekt");
        rootGeometryObjectNode.addChild(objects);
        TreeItem<GeometryObject> root = new TreeItem<>(rootGeometryObjectNode);

        for(GeometryObject g: objects){
            if(g.getChildren().size() == 0){
                TreeItem<GeometryObject> temp = new TreeItem<>(g);
                root.getChildren().add(temp);
            } else {
                root.getChildren().add(populateTreeItem(g));
            }
        }

        nameColumn.setCellValueFactory(p -> p.getValue().getValue().nameProperty());
        verticesColumn.setCellValueFactory(p -> p.getValue().getValue().numberOfFacesProperty().asObject());

        objectTreeTable.setRoot(root);

    }

    private TreeItem<GeometryObject> populateTreeItem(GeometryObject g){
        TreeItem<GeometryObject> temp = new TreeItem<>(g);

        if(g.getChildren().size() != 0){
            for(GeometryObject p : g.getChildren()){
                temp.getChildren().add(populateTreeItem(p));
            }
        }
        return temp;
    }

    public SettingNode createSingleSettingNode(String title, SimpleDoubleProperty property){

        SettingNode temp = new SettingNode(title, new ContainerItem(title), SettingsType.CONTAINER);

        SettingNode framerateNode = new SettingNode(title, new FloatSettingsItem(title, FloatSettingType.SLIDER), SettingsType.INT);
        framerateNode.bindToProperty(property);
        temp.addNode(framerateNode);

        return temp;
    }

    public SettingNode createVectorSettingNode(String title, SimpleDoubleProperty xProperty,
                                                             SimpleDoubleProperty yProperty,
                                                             SimpleDoubleProperty zProperty){

        SettingNode temp = new SettingNode(title, new ContainerItem(title), SettingsType.CONTAINER);

        SettingNode eyeVectorX = new SettingNode("Eye X", new FloatSettingsItem("Eye X", FloatSettingType.SPINNER), SettingsType.INT);
        eyeVectorX.bindToProperty(xProperty);
        temp.addNode(eyeVectorX);

        SettingNode eyeVectorY = new SettingNode("Eye Y", new FloatSettingsItem("Eye Y", FloatSettingType.SPINNER), SettingsType.INT);
        eyeVectorY.bindToProperty(yProperty);
        temp.addNode(eyeVectorY);

        SettingNode eyeVectorZ = new SettingNode("Eye Z", new FloatSettingsItem("Eye Z", FloatSettingType.SPINNER), SettingsType.INT);
        eyeVectorZ.bindToProperty(zProperty);
        temp.addNode(eyeVectorZ);

        return temp;

    }

}
