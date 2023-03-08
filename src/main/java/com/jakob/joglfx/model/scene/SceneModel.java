package com.jakob.joglfx.model.scene;

import com.jakob.joglfx.geometry.GeometryObject;

import java.util.ArrayList;
import java.util.HashSet;

public class SceneModel {

    Camera camera;
    GeometryObject rootGeometryObject;

    public SceneModel(GeometryObject rootGeometryObject) {
        camera = new Camera();
        this.rootGeometryObject = rootGeometryObject;
    }

    public SceneModel(Camera camera, GeometryObject rootGeometryObject) {
        this.camera = camera;
        this.rootGeometryObject = rootGeometryObject;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public GeometryObject getRootGeometryObject() {
        return rootGeometryObject;
    }

    public void setRootGeometryObject(GeometryObject rootGeometryObject) {
        this.rootGeometryObject = rootGeometryObject;
    }

    public HashSet<GeometryObject> getObjects() {
        return getObjects(rootGeometryObject);
    }

    public HashSet<GeometryObject> getObjects(GeometryObject geometryObject) {
        HashSet<GeometryObject> temp = new HashSet<>();

        if(geometryObject.getChildren().size() != 0){
            for (GeometryObject n: geometryObject.getChildren()){
                if(getObjects(n) != null) temp.addAll(getObjects(n));
            }
        }
        if (geometryObject.getFaces().size() != 0){
            System.out.println(geometryObject.numberOfFaces());
            temp.add(geometryObject);
        }

        return temp.size() == 0 ? null : temp;
    }
    
}
