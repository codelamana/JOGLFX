package com.jakob.joglfx.model.settingsitems;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;


public abstract class SettingsItem<T> {


   protected ChangeListener<T> changeListener;
   protected SimpleObjectProperty<T> property;

   String titleString;

   protected String fxml;

   @FXML
   public HBox content;



    public SettingsItem(String title) {
        this.titleString = title;
    }

    public abstract ChangeListener<T> getChangeListener();

    public abstract SimpleObjectProperty<T> getProperty();


    public String getFxml() {
        return fxml;
    }

}
