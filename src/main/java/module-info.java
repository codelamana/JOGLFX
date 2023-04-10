module com.jakob.joglfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires jogl.all;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.swing;
    requires gluegen.rt;
    requires org.joml;
    requires java.json;
    requires javax


    opens com.jakob.joglfx to javafx.fxml;
    opens com.jakob.joglfx.model to javafx.fxml;
    opens com.jakob.joglfx.gui to javafx.fxml;
    opens com.jakob.joglfx.geometry to com.google.gson;
    //opens com.jakob.joglfx.geometry t
    //opens java.nio to com.google.gson;
    exports com.jakob.joglfx;
    opens com.jakob.joglfx.model.settingsitems to javafx.fxml;
}