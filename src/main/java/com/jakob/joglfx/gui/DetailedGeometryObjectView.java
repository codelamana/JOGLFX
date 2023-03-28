package com.jakob.joglfx.gui;

import com.jakob.joglfx.geometry.GeometryObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailedGeometryObjectView implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    TableView<GeometryObject> childTableView;

    GeometryObject currentObject;

    public DetailedGeometryObjectView(GeometryObject currentObject) {
        this.currentObject = currentObject;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        childTableView.setPlaceholder(new Label("This GeometryObject has no Children"));

        TableColumn<GeometryObject, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(celldata -> celldata.getValue().nameProperty());

        childTableView.getColumns().clear();
        childTableView.getColumns().add(nameColumn);

        childTableView.getItems().clear();
        childTableView.getItems().addAll(currentObject.getChildren());

        this.nameField.setText(currentObject.getName());
        this.nameField.textProperty().addListener((observableValue, s, t1) -> currentObject.setName(t1));

    }

    public void setCurrentObject(GeometryObject newObject){
        this.currentObject = newObject;
        this.initialize(null,null);
    }
}
