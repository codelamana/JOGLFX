package com.jakob.joglfx.gui;

import com.jakob.joglfx.geometry.GeometryObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ObjectTreeTableView  implements Initializable {

    GeometryObject root;

    @FXML
    TreeTableView<GeometryObject> objectTreeTable;

    public ObjectTreeTableView(GeometryObject root) {
        this.root = root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        objectTreeTable.setShowRoot(true);

        TreeTableColumn<GeometryObject, String> nameColumn = new TreeTableColumn<>("Name");
        TreeTableColumn<GeometryObject, Integer> verticesColumn = new TreeTableColumn<>("#Faces");

        nameColumn.setMinWidth(100);
        verticesColumn.setMinWidth(50);

        objectTreeTable.getColumns().add(nameColumn);
        objectTreeTable.getColumns().add(verticesColumn);


        TreeItem<GeometryObject> rootTreeItem = new TreeItem<>(root);
        rootTreeItem.setExpanded(true);

        for(GeometryObject g: root.getChildren()){
            if(g.getChildren().size() == 0){
                TreeItem<GeometryObject> temp = new TreeItem<>(g);
                rootTreeItem.getChildren().add(temp);
            } else {
                rootTreeItem.getChildren().add(populateTreeItem(g));
            }
        }

        nameColumn.setCellValueFactory(p -> p.getValue().getValue().nameProperty());
        verticesColumn.setCellValueFactory(p -> p.getValue().getValue().numberOfFacesProperty().asObject());

        objectTreeTable.setRoot(rootTreeItem);

    }

    private TreeItem<GeometryObject> populateTreeItem(GeometryObject g){
        TreeItem<GeometryObject> temp = new TreeItem<>(g);

        if(g.getChildren().size() != 0){
            for(GeometryObject p : g.getChildren()){
                temp.getChildren().add(populateTreeItem(p));
            }
        }
        return temp;
    }
}
