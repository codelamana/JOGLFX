package com.jakob.joglfx.model.settingsitems;

import com.jakob.joglfx.model.SettingsItem;
import com.jakob.joglfx.model.settingscontroller.FloatSettingController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FloatSettingsItem extends SettingsItem<Double> {

    public enum FloatSettingType{
        SPINNER,
        SLIDER
    }

    FloatSettingType settingType;

    public FloatSettingsItem(String title, FloatSettingType type) {
        super(title);

        this.settingType = type;

        settingsController = new FloatSettingController(title, type);
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

        settingsController.addChangeListener(changeListener);

        property = new SimpleObjectProperty<>();
    }

    public ChangeListener<Double> getChangeListener() {
        return changeListener;
    }

    @Override
    public SimpleObjectProperty<Double> getProperty() {
        return property;
    }

}
