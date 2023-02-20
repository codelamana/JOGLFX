package com.example.joglfx.model;


public class SettingsItem<T>{

    public enum SettingsType{
        PATH,
        VEC3
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
            settingsController = new PathsettingController(title);
            fxml = "settings/pathsetting.fxml";
        }

    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    public String getFxml() {
        return fxml;
    }
}
