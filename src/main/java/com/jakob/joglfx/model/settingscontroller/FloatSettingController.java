package com.jakob.joglfx.model.settingscontroller;

import com.jakob.joglfx.model.settingsitems.FloatSettingsItem;
import com.jakob.joglfx.model.settingsitems.FloatSettingsItem.FloatSettingType;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FloatSettingController extends SettingsController<Double> implements Initializable {

    @FXML
    Slider valueSlider;
    SimpleObjectProperty<Double> sliderProperty;

    @FXML
    Spinner<Double> spinner;

    SpinnerValueFactory<Double> spinnerFactory;

    @FXML
    Label title;

    ChangeListener<Double> changeListener;
    Property<Double> propertyToBind;

    FloatSettingType settingType;

    public FloatSettingController(String titleString, FloatSettingType type) {
        this.titleString = titleString;
        this.settingType = type;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.title.setText(titleString);
        if(settingType == FloatSettingType.SLIDER){

            this.sliderProperty = new SimpleObjectProperty<>();

            this.valueSlider.valueProperty().addListener((observableValue, number, t1) -> sliderProperty.set(t1.doubleValue()));

            this.sliderProperty.bindBidirectional(propertyToBind);
            this.sliderProperty.addListener(changeListener);

        } else if (settingType == FloatSettingType.SPINNER) {

            spinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5.0, 5.0, 3);
            this.spinner.setValueFactory(spinnerFactory);

            this.spinnerFactory.valueProperty().addListener(changeListener);
            this.spinnerFactory.valueProperty().bindBidirectional(propertyToBind);
        }

    }


    @Override
    public void addChangeListener(ChangeListener<Double> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void bindProperty(SimpleObjectProperty<Double> property) {
        this.propertyToBind = property;
    }


}
