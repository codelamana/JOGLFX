package com.jakob.joglfx.model.settingsitems;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PathSettingsItem extends SettingsItem<String> implements Initializable {

    @FXML
    TextField path;

    @FXML
    Button fileChooser;

    String titleString;

    File chosenFile;

    public PathSettingsItem(String title) {
        super(title);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        path.textProperty().addListener(changeListener);
        this.path.textProperty().bindBidirectional(property);
    }

}
