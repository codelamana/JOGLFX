package com.jakob.joglfx.model;

import javafx.beans.property.Property;

import java.util.ArrayList;
import java.util.List;

public class SettingNode{

    public enum SettingsType{
        CONTAINER,
        PATH,
        VEC3,
        INT
    }

    SettingsItem settingsItem;

    ArrayList<SettingNode> children;
    SettingNode parent;

    String title;
    SettingsType type;

    static String fxml;

    public SettingNode(String title, SettingsItem settingsItem, SettingsType type) {
        this.title = title;
        this.type = type;
        this.settingsItem = settingsItem;
        this.children = new ArrayList<>();
    }

    public void addNode(SettingNode child){
        this.children.add(child);
    }

    public void bindToProperty(Property tProperty){
        this.settingsItem.getProperty().bindBidirectional(tProperty);
    }

    public SettingsItem getSettingsItem () {
        return settingsItem;
    }

    public void setSettingsItem (SettingsItem settingsItem){
        this.settingsItem = settingsItem;
    }

    public ArrayList<SettingNode> getChildren () {
        return children;
    }

    public void setChildren (ArrayList < SettingNode > children) {
        this.children = children;
    }

    public SettingNode getParent () {
        return parent;
    }

    public void setParent (SettingNode parent){
        this.parent = parent;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public static String getFxml () {
        return fxml;
    }

    public static void setFxml (String fxml){
        SettingNode.fxml = fxml;
    }

}
