package com.jakob.joglfx.model.settingsitems;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FloatSettingsItem extends SettingsItem<Double> implements Initializable {

    public enum FloatSettingType{
        SPINNER,
        SLIDER
    }

    @FXML
    Slider valueSlider;
    SimpleObjectProperty<Double> sliderProperty;

    @FXML
    Spinner<Double> spinner;
    SpinnerValueFactory<Double> spinnerFactory;




    FloatSettingType settingType;

    public FloatSettingsItem(String title, FloatSettingType type) {
        super(title);
        this.settingType = type;

        fxml = "settings/sliderfloatsetting.fxml";

        if(settingType == FloatSettingType.SLIDER){
            fxml = "settings/sliderfloatsetting.fxml";
        } else if (settingType == FloatSettingType.SPINNER) {
            fxml = "settings/spinnerfloatsetting.fxml";
        }

        changeListener = new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observableValue, Double aDouble, Double t1) {

            }
        };

        property = new SimpleObjectProperty<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(settingType == FloatSettingType.SLIDER){

            this.sliderProperty = new SimpleObjectProperty<>();

            this.valueSlider.valueProperty().addListener((observableValue, number, t1) -> sliderProperty.set(t1.doubleValue()));

            this.sliderProperty.bindBidirectional(property);
            this.sliderProperty.addListener(changeListener);

        } else if (settingType == FloatSettingType.SPINNER) {

            spinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5.0, 5.0, 3);
            this.spinner.setValueFactory(spinnerFactory);

            this.spinnerFactory.valueProperty().addListener(changeListener);
            this.spinnerFactory.valueProperty().bindBidirectional(property);
        }
    }

    @Override
    public ChangeListener<Double> getChangeListener() {
        return changeListener;
    }

    @Override
    public SimpleObjectProperty<Double> getProperty() {
        return property;
    }



}
