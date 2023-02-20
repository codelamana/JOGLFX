package com.example.joglfx.model;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FloatSettingController extends SettingsController implements Initializable{

    @FXML
    Label title;

    @FXML
    Spinner<Double> xSpinner;

    @FXML
    Spinner<Double> ySpinner;

    @FXML
    Spinner<Double> zSpinner;

    SpinnerValueFactory<Double> xFactory;
    SpinnerValueFactory<Double> yFactory;
    SpinnerValueFactory<Double> zFactory;

    String titleString;

    public FloatSettingController(String t) {
        this.titleString = t;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.title.setText(this.titleString);

        xFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5.0, 5.0, 0);
        yFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5.0, 5.0, 0);
        zFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5.0, 5.0, 0);

        xSpinner.setValueFactory(xFactory);
        ySpinner.setValueFactory(yFactory);
        zSpinner.setValueFactory(zFactory);
    }
}
