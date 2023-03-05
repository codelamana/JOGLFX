package com.jakob.joglfx.model.settingsitems;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.IndexedCheckModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ContainerItem extends SettingsItem<Object> implements Initializable {

    @FXML
    Label title;

    @FXML
    public HBox content;

    public ContainerItem(String title) {
        super(title);
        fxml = "settings/settingscontainer.fxml";
    }

    @Override
    public ChangeListener<Object> getChangeListener() {
        return (observableValue, o, t1) -> {};
    }

    @Override
    public SimpleObjectProperty<Object> getProperty() {
        return new SimpleObjectProperty<>();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.title.setText(this.titleString);
    }
}
