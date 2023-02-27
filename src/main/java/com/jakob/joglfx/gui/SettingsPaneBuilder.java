package com.jakob.joglfx.gui;

import com.jakob.joglfx.MainWindowController;
import com.jakob.joglfx.model.SettingNode;
import com.jakob.joglfx.model.settingscontroller.SettingsController;
import com.jakob.joglfx.model.settingsitems.IntSettingsItem;
import com.jakob.joglfx.model.settingsitems.PathSettingsItem;
import com.jakob.joglfx.model.settingsitems.VectorSettingsItem;
import com.jakob.joglfx.model.SettingsItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class SettingsPaneBuilder {

    public SettingsPaneBuilder() {

    }

    public static void populatePane(Pane p, SettingNode root){
        for(SettingNode item : root.getChildren()){
            Node n = loadNode(item.getSettingsItem());
            p.getChildren().add(n);
        }
    }

    public static Node loadNode(SettingsItem item){
        Node n;
        FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource(item.getFxml()));

        SettingsController c = item.getSettingsController();
        c.addChangeListener(item.getChangeListener());

        c.bindProperty(item.getProperty());

        loader.setController(item.getSettingsController());

        try {
            n = loader.load();
        } catch (IOException e) {
            return null;
        }

        return n;
    }

}
