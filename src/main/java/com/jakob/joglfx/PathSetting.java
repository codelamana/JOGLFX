package com.jakob.joglfx;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PathSetting implements Initializable {
    @javafx.fxml.FXML
    private Label titleLabel;
    @javafx.fxml.FXML
    private TextField pathToFileField;
    @javafx.fxml.FXML
    private Button choosePathButton;

    String title = "";

    public PathSetting(String title) {
        this.title = title;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
