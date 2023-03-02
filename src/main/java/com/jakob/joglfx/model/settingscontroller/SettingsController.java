package com.jakob.joglfx.model.settingscontroller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import org.joml.Vector3f;

public abstract class SettingsController<T> {

    protected ChangeListener<T> changeListener;
    protected SimpleObjectProperty<T> propertyToBind;

    String titleString;

    public abstract void addChangeListener(ChangeListener<T> changeListener);

    public abstract void bindProperty(SimpleObjectProperty<T> property);


}
