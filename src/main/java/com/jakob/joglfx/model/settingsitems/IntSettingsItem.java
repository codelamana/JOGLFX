package com.jakob.joglfx.model.settingsitems;

import com.jakob.joglfx.model.SettingsItem;
import com.jakob.joglfx.model.settingscontroller.IntSettingController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class IntSettingsItem extends SettingsItem<Number> {

    public IntSettingsItem(String title) {
        super(title);

        settingsController = new IntSettingController(title);
        fxml = "settings/intsetting.fxml";

        changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(t1.doubleValue());
            }
        };

        property = new SimpleObjectProperty<>(10);
    }

    public ChangeListener<Number> getChangeListener() {
        return changeListener;
    }

    @Override
    public SimpleObjectProperty<Number> getProperty() {
        return property;
    }


}
