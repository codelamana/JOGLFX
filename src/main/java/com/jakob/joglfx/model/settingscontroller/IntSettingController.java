package com.jakob.joglfx.model.settingscontroller;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class IntSettingController extends SettingsController<Number> implements Initializable {

    @FXML
    Slider valueSlider;

    @FXML
    Label title;

    ChangeListener<Number> changeListener;
    Property<Number> propertyToBind;

    public IntSettingController(String titleString) {
        this.titleString = titleString;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.title.setText(titleString);
        this.valueSlider.valueProperty().addListener(changeListener);
        this.valueSlider.valueProperty().bindBidirectional(propertyToBind);
    }

    public void addChangeListener(ChangeListener<Number> changeListener){
        this.changeListener = changeListener;
    }

    @Override
    public void bindProperty(SimpleObjectProperty<Number> property) {
        this.propertyToBind = property;
    }
}
