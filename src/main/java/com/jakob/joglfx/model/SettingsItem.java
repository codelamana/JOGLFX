package com.jakob.joglfx.model;


import com.jakob.joglfx.model.settingscontroller.SettingsController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;


public abstract class SettingsItem<T> {

   protected SettingsController<T> settingsController;

   protected ChangeListener<T> changeListener;
   protected SimpleObjectProperty<T> property;

   String title;
   protected String fxml;


    public SettingsItem(String title) {
        this.title = title;
    }

    public abstract ChangeListener<T> getChangeListener();

    public abstract SimpleObjectProperty<T> getProperty();

    public SettingsController<T> getSettingsController() {
        return settingsController;
    }

    public String getFxml() {
        return fxml;
    }

}
