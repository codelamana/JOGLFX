package com.example.joglfx.gui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        super();
    }

    public void setupMenuBar(){
        JMenu file = new JMenu("File");

        JMenuItem open = new JMenuItem("Open File...");
        JMenuItem save = new JMenuItem("Save File...");

        file.add(open);
        file.add(save);

        this.add(file);
    }
}
