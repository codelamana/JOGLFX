package com.jakob.joglfx.model.settingscontroller;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.joml.Vector3f;

import java.net.URL;
import java.util.ResourceBundle;

public class VectorSettingController extends SettingsController<Vector3f> implements Initializable{

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

    Vector3f vector = new Vector3f(0,0,0);

    public VectorSettingController(String t) {
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

        this.xFactory.valueProperty().addListener((observableValue, aDouble, t1) -> {
            vector.x = t1.floatValue();
            propertyToBind.set(vector);
        });
        this.yFactory.valueProperty().addListener((observableValue, aDouble, t1) -> {
            vector.y = t1.floatValue();
            propertyToBind.set(vector);
        });
        this.zFactory.valueProperty().addListener((observableValue, aDouble, t1) -> {
            vector.z = t1.floatValue();
            propertyToBind.set(vector);
        });
    }




    @Override
    public void addChangeListener(ChangeListener<Vector3f> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void bindProperty(SimpleObjectProperty<Vector3f> property) {
        this.propertyToBind = property;
    }

}
