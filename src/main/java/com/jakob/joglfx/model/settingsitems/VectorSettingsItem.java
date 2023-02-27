package com.jakob.joglfx.model.settingsitems;

import com.jakob.joglfx.model.SettingsItem;
import com.jakob.joglfx.model.settingscontroller.VectorSettingController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.joml.Vector3f;

public class VectorSettingsItem extends SettingsItem<Vector3f> {

    public VectorSettingsItem(String title) {
        super(title);

        settingsController = new VectorSettingController("title");
        fxml = "settings/vectorsetting.fxml";

        changeListener = new ChangeListener<Vector3f>() {
            @Override
            public void changed(ObservableValue<? extends Vector3f> observableValue, Vector3f vector3f, Vector3f t1) {
                System.out.println(t1.toString());
            }
        };

        property = new SimpleObjectProperty<>(new Vector3f(0,0,0));
    }

    @Override
    public ChangeListener<Vector3f> getChangeListener() {
        return changeListener;
    }

    @Override
    public SimpleObjectProperty<Vector3f> getProperty() {
        return property;
    }

}
