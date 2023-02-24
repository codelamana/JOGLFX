package com.example.joglfx.gui;

import com.example.joglfx.MainWindowController;
import com.example.joglfx.geometry.base.vec3;
import com.example.joglfx.gl.Shader;
import com.example.joglfx.gl.VertexShader;
import com.example.joglfx.model.SettingsController;
import com.example.joglfx.model.SettingsItem;
import com.example.joglfx.model.SettingsItem.SettingsType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.lang.reflect.Array;
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
        this.items.add(new SettingsItem<vec3>("Center", SettingsType.VEC3));
        this.items.add(new SettingsItem<vec3>("Camera", SettingsType.VEC3));
        this.items.add(new SettingsItem<vec3>("Up", SettingsType.VEC3));
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
