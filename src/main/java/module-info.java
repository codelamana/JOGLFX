module com.example.joglfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires jogl.all;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.swing;
    requires gluegen.rt;
    requires org.joml;


    opens com.example.joglfx to javafx.fxml;
    opens com.example.joglfx.model to javafx.fxml;
    exports com.example.joglfx;
}