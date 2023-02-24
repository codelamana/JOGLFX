package com.example.joglfx.model;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class IntSettingController extends SettingsController implements Initializable {

    @FXML
    Slider valueSlider;

    @FXML
    Label title;

    String titleString;

    public IntSettingController(String titleString) {
        this.titleString = titleString;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.title.setText(titleString);
    }
}
