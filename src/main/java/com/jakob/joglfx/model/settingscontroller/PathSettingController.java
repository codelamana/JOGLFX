package com.jakob.joglfx.model.settingscontroller;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PathSettingController extends SettingsController<String> implements Initializable {

    @FXML
    Label title;

    @FXML
    TextField path;

    @FXML
    Button fileChooser;

    String titleString;

    File chosenFile;

    ChangeListener<String> changeListener;
    Property<String> propertyToBind;

    public PathSettingController(String t) {
        this.titleString = t;
    }


    @FXML
    public void onChooseFile(){

    }

    @FXML
    public void setChosenFile(File f){
        this.chosenFile = f;
        this.path.setText(f.getPath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText(titleString);
        path.textProperty().addListener(changeListener);
        this.path.textProperty().bindBidirectional(propertyToBind);
    }

    @Override
    public void addChangeListener(ChangeListener<String> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void bindProperty(SimpleObjectProperty<String> property) {
        this.propertyToBind = property;
    }
}
