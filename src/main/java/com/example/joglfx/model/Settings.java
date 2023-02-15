package com.example.joglfx.model;

import com.example.joglfx.gui.ModelViewer;

import java.util.HashMap;
public class Settings {

    HashMap<String, String> glFiles;
    HashMap<String, double[]> glCoordinates;
    HashMap<String, double[]> glLights;

    ModelViewer modelViewer;

    public Settings() {
        glFiles = new HashMap<>();
        glFiles.put("vertexShader", "vertex_shader.glsl");
        glFiles.put("fragmentShader", "fragment_shader.glsl");


        glCoordinates = new HashMap<>();
        glCoordinates.put("eye", new double[]{1,1,1});
        glCoordinates.put("center", new double[]{0, 0, 0});
        glCoordinates.put("up", new double[]{0f,1f,0f});
        glCoordinates.put("x_lim", new double[]{-100, 100});

        glLights = new HashMap<>();
        glLights.put("light0", new double[]{8,8, -3});
    }

    public void addModelViewer(ModelViewer m){
        this.modelViewer = m;
    }

    /**
     * Returns glFiles
     *
     * @return
     */
    public HashMap<String, String> getGlFiles() {
        return glFiles;
    }

    /**
     * Sets glFiles to given value
     *
     * @param glFiles
     */
    public void setGlFiles(HashMap<String, String> glFiles) {
        this.glFiles = glFiles;
    }

    /**
     * Returns glCoordinates
     *
     * @return
     */
    public HashMap<String, double[]> getGlCoordinates() {
        return glCoordinates;
    }

    /**
     * Sets glCoordinates to given value
     *
     * @param glCoordinates
     */
    public void setGlCoordinates(HashMap<String, double[]> glCoordinates) {
        this.glCoordinates = glCoordinates;
    }
}
