package com.example.joglfx;

import com.example.joglfx.gl.VertexShader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MainApp extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("mainwindow-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 1000);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        File vertexShader = new File("vertex_shader.glsl");
        InputStream in = MainApp.class.getClassLoader().getResourceAsStream(vertexShader.getPath());


        if(vertexShader.exists() && !vertexShader.isDirectory()) {
            System.out.println("Datei existiert");
        }

    }

    public static void main(String[] args) {
        launch();
    }

}