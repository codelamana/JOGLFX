package com.example.joglfx.gui;


import com.example.joglfx.model.Settings;

import javax.swing.*;

public class GlobalModelSettingsPane extends JPanel {

    Settings settings;

    JSlider eye;
    public GlobalModelSettingsPane(Settings s) {
        eye = new JSlider();
    }


}
