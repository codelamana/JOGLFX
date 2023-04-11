package com.jakob.joglfx;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.primitives.Cube;
import com.jakob.joglfx.gl.ShaderProgram;
import com.jakob.joglfx.model.scene.SceneModel;
import com.jakob.joglfx.object.OBJloader;
import org.joml.Quaternionf;

public class Project {
    /**
     * Main class for managing and storing all data related items
     * A project is passed to the main application and all data needed can be retrieved from the project instance
     *
     */


    String projectName;

    SceneModel sceneModel;

    GeometryObject rootGeometryObject;

    ShaderProgram shaderProgram;


    public Project(String projectName) {
        this.projectName = projectName;
    }


    public SceneModel getSceneModel() {
        return sceneModel;
    }

    public GeometryObject getRootGeometryObject() {
        return rootGeometryObject;
    }

    public void setSceneModel(SceneModel sceneModel) {
        this.sceneModel = sceneModel;
    }

    public void setRootGeometryObject(GeometryObject rootGeometryObject) {
        this.rootGeometryObject = rootGeometryObject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
