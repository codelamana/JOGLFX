package com.jakob.joglfx.model.settingscontroller;

import com.jakob.joglfx.model.SettingsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PathSettingController extends SettingsController implements Initializable {

    @FXML
    Label title;

    @FXML
    TextField path;

    @FXML
    Button fileChooser;

    String titleString;

    File chosenFile;

    @FXML
    public void onChooseFile(){

    }

    public PathSettingController(String t) {
        this.titleString = t;
    }

    public void setChosenFile(File f){
        this.chosenFile = f;
        this.path.setText(f.getPath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText(titleString);
    }
}
