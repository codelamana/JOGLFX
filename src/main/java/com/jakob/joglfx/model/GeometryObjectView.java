package com.jakob.joglfx.model;

import com.jakob.joglfx.geometry.GeometryObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GeometryObjectView implements Initializable {


    @FXML
    Label name;

    GeometryObject currentGeometryObject;

    public GeometryObjectView(GeometryObject currentGeometryObject) {
        this.currentGeometryObject = currentGeometryObject;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (currentGeometryObject != null) {
            this.name.setText(currentGeometryObject.getName());
        }
    }

    public void setCurrentGeometryObject(GeometryObject newGeometryObject){
        this.currentGeometryObject = newGeometryObject;
        this.initialize(null, null);
    }
}
