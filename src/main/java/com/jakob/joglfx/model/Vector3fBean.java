package com.jakob.joglfx.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.joml.Vector3f;

public class Vector3fBean {

    SimpleFloatProperty x;
    SimpleFloatProperty y;
    SimpleFloatProperty z;

    Vector3f value;

    SimpleObjectProperty<Vector3f> valueProperty;

    public Vector3fBean() {
        this.x = new SimpleFloatProperty(0);
        this.y = new SimpleFloatProperty(0);
        this.z = new SimpleFloatProperty(0);

        value = new Vector3f(0,0,0);
        valueProperty = new SimpleObjectProperty<>(value);

        this.x.addListener((observableValue, number, t1) -> value.x = t1.floatValue());
        this.y.addListener((observableValue, number, t1) -> value.y = t1.floatValue());
        this.z.addListener((observableValue, number, t1) -> value.z = t1.floatValue());
    }

    public Vector3f getValueP() {
        return valueProperty.get();
    }

    public SimpleObjectProperty<Vector3f> valueProperty() {
        return valueProperty;
    }

    public float getX() {
        return x.get();
    }

    public SimpleFloatProperty xProperty() {
        return x;
    }

    public float getY() {
        return y.get();
    }

    public SimpleFloatProperty yProperty() {
        return y;
    }

    public float getZ() {
        return z.get();
    }

    public SimpleFloatProperty zProperty() {
        return z;
    }

    public Vector3f getValue() {
        return value;
    }
}
