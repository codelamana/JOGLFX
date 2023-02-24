package com.jakob.joglfx.model;


import com.jakob.joglfx.model.settingscontroller.FloatSettingController;
import com.jakob.joglfx.model.settingscontroller.IntSettingController;
import com.jakob.joglfx.model.settingscontroller.PathSettingController;

public class SettingsItem<T>{

    public enum SettingsType{
        PATH,
        VEC3,
        INT
    }

   SettingsController settingsController;

   T Value;

   String title;
   String fxml;


    public SettingsItem(String title, SettingsType type) {
        this.title = title;
        if(type == SettingsType.VEC3){
            settingsController = new FloatSettingController(title);
            fxml = "settings/floatsetting.fxml";
        } else if (type == SettingsType.PATH) {
            settingsController = new PathSettingController(title);
            fxml = "settings/pathsetting.fxml";
        } else if (type == SettingsType.INT) {
        settingsController = new IntSettingController(title);
        fxml = "settings/intsetting.fxml";
    }

    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    public String getFxml() {
        return fxml;
    }
}
