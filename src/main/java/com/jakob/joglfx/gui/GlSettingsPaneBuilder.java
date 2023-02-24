package com.jakob.joglfx.gui;

import com.jakob.joglfx.MainWindowController;
import com.jakob.joglfx.gl.VertexShader;
import com.jakob.joglfx.model.SettingsController;
import com.jakob.joglfx.model.SettingsItem;
import com.jakob.joglfx.model.SettingsItem.SettingsType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;

public class GlSettingsPaneBuilder {

    ArrayList<SettingsItem> items;

    public GlSettingsPaneBuilder() {
        this.items = new ArrayList<>();
    }

    public void add(SettingsItem item){
        this.items.add(item);
    }

    public void addTestItems(){
        this.items.add(new SettingsItem<Vector3f>("Center", SettingsType.VEC3));
        this.items.add(new SettingsItem<Vector3f>("Camera", SettingsType.VEC3));
        this.items.add(new SettingsItem<Vector3f>("Up", SettingsType.VEC3));
        this.items.add(new SettingsItem<Integer>("Frame Rate", SettingsType.INT));
        this.items.add(new SettingsItem<VertexShader>("Vertex Shader Pfad", SettingsType.PATH));
        this.items.add(new SettingsItem<VertexShader>("Fragment Shader Pfad", SettingsType.PATH));
    }

    public void populatePane(Pane p){
        for(SettingsItem item : this.items){
            Node n = this.loadNode(item.getFxml(), item.getSettingsController());
            p.getChildren().add(n);
        }
    }

    public Node loadNode(String fxml, SettingsController c){
        Node n;
        FXMLLoader loader = new FXMLLoader(MainWindowController.class.getResource(fxml));
        loader.setController(c);

        try {
            n = loader.load();
        } catch (IOException e) {
            return null;
        }

        return n;
    }

}
