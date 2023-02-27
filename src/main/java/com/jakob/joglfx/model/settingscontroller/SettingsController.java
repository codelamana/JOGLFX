package com.jakob.joglfx.model.settingscontroller;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;

public abstract class SettingsController<T> implements Initializable {


    String titleString;
    ChangeListener<T> changeListener;
    SimpleObjectProperty<T> propertyToBind;

    public abstract void addChangeListener(ChangeListener<T> changeListener);

    public abstract void bindProperty(SimpleObjectProperty<T> property);

}
