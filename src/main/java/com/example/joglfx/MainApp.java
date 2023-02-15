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
    /**
     * Main Application - Program starts here
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {

        // Load Main Window and setup
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("mainwindow-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 1000);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}