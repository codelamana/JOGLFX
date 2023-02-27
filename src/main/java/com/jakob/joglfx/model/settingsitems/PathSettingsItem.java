package com.jakob.joglfx.model.settingsitems;

import com.jakob.joglfx.model.SettingsItem;
import com.jakob.joglfx.model.settingscontroller.PathSettingController;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PathSettingsItem extends SettingsItem<String> {

    public PathSettingsItem(String title) {
        super(title);

        settingsController = new PathSettingController(title);
        fxml = "settings/pathsetting.fxml";

        changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(s);
            }
        };

        property = new SimpleObjectProperty<>("Pfad");
    }

    @Override
    public ChangeListener<String> getChangeListener() {
        return changeListener;
    }

    @Override
    public SimpleObjectProperty<String> getProperty() {
        return property;
    }
}
