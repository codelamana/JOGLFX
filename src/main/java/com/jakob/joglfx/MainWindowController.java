package com.jakob.joglfx;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.primitives.Cube;
import com.jakob.joglfx.gui.DetailedGeometryObjectView;
import com.jakob.joglfx.gui.ObjectTreeTableView;
import com.jakob.joglfx.gui.SettingsPaneBuilder;
import com.jakob.joglfx.gui.ModelViewer;

import com.jakob.joglfx.model.SettingNode;
import com.jakob.joglfx.model.scene.SceneModel;
import com.jakob.joglfx.model.settingsitems.ContainerItem;
import com.jakob.joglfx.model.settingsitems.FloatSettingsItem;
import com.jakob.joglfx.object.OBJloader;
import com.jogamp.opengl.*;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.json.*;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.jakob.joglfx.model.SettingNode.*;
import static com.jakob.joglfx.model.settingsitems.FloatSettingsItem.*;

public class MainWindowController implements Initializable {
    /**
     *  Main Application Controller
     *  
     *  This class is for managing all components of the Window.
     *  Respectively all interactions between components are set up in this class
     * 
     */

    @FXML
            
    SwingNode glcanvas;
    @FXML
            
    Button animatorToggle;
    @FXML
    SplitPane splitPane;

    @FXML
    VBox glSettingsContainer;

    @FXML
    AnchorPane treeViewContainer;

    @FXML
    TitledPane detailedGeometryObjectView;

    DetailedGeometryObjectView geometryObjectViewController;

    ObjectTreeTableView objectTreeTableController;

    ModelViewer modelViewer;

    Project currentProject;


    /**
     * Initialize Main Window Controller
     * 
     * @param currentProject the project to load
     */
    public MainWindowController(Project currentProject) {
        this.currentProject = currentProject;
    }

    /**
     * Initialize all Components after their FXML objects are loaded (after FXMLLoader.load())
     * 
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        // get the current OpenGL profile for distribution in programs
        final GLProfile glProfile = GLProfile.getDefault();
        final GLCapabilities capabilities = new GLCapabilities(glProfile);

        splitPane.setDividerPositions(0.2, 0.85);
        
        // setup the GeometryObjet views, these are a TreeView and a detailed view of the selected objects
        setupObjectTreeTableView(currentProject.getRootGeometryObject());
        setupDetailedGeometryObjectView(currentProject.getRootGeometryObject());
        
        // Setup the ModelViewer in the program. 
        // This is the core component of the program and renders a view of the scene
        modelViewer = new ModelViewer(capabilities, currentProject.getSceneModel());
        currentProject.getSceneModel().getCamera().eyeProperty().set(new Vector3f(-5,0,5));
        
        // GLJPanel is a Swing Component. It has to be added to the SwingNode after initialization
        SwingUtilities.invokeLater(() -> {
            glcanvas.setContent(modelViewer);
            modelViewer.startAnimation();
        });
        
        // Settings on the right put in new class
        SettingNode settingsRoot = new SettingNode("Main Container", null, SettingsType.CONTAINER);

        SettingNode glSettings = new SettingNode("GL Setting Container", new ContainerItem("GL Settings"), SettingsType.CONTAINER);
        settingsRoot.addNode(glSettings);

        SettingNode framerateNode = createSingleSettingNode("Framerate", modelViewer.getFramerateProperty());
        glSettings.addNode(framerateNode);

        SettingsPaneBuilder.populatePane(glSettingsContainer, glSettings);
        glSettingsContainer.setPadding(new Insets(3));

    }

    /**
     * Load TreeTableView of the GeometryObjects in Scene
     * 
     * Only the Node is loaded and put in the window. Setup of data and View is happening in the 
     * respective controller class
     * 
     * @param rootGeometryObject Root Object from which the TreeView is built
     */
    private void setupObjectTreeTableView(GeometryObject rootGeometryObject) {
        
        // Create concrete controller for tree view
        objectTreeTableController = new ObjectTreeTableView(rootGeometryObject);

        // load node from fxml file and assign the controller to it
        FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("treegeometryobjectview.fxml"));
        loader.setController(objectTreeTableController);

        // load node
        Node n;
        try {
            n = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // embed Node in window
        AnchorPane.setLeftAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        this.treeViewContainer.setMinSize(20,20);
        this.treeViewContainer.getChildren().add(n);

    }

    /**
     * Load detaild view of a GeometryObject in Scene
     *
     * Only the Node is loaded and put in the window. Setup of data and view is happening in the
     * respective controller class
     *
     * @param currentObject initial object to be shown
     */
    private void setupDetailedGeometryObjectView(GeometryObject currentObject) {


        // Create concrete controller for tree view
        geometryObjectViewController = new DetailedGeometryObjectView(currentObject);

        // load node from fxml file and assign the controller to it
        FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource("detailedgeometryobjectview.fxml"));
        loader.setController(geometryObjectViewController);

        // load node
        Node n;
        try {
            n = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // embed Node in window
        this.detailedGeometryObjectView.setContent(n);
        this.detailedGeometryObjectView.setText("Detailed GeometryObject View");

        /*objectTreeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<GeometryObject>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<GeometryObject>> observableValue, TreeItem<GeometryObject> geometryObjectTreeItem, TreeItem<GeometryObject> t1) {
                geometryObjectViewController.setCurrentObject(t1.getValue());
            }
        });*/

    }


    @FXML
    public void onToggleAnimation(){
        modelViewer.toggleAnimation();
    }

    @FXML
    public void onToJSON(){
        System.out.println("Erstelle JSON");
        ProjectLoader.saveProject(currentProject);
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
