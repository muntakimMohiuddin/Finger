module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires ZKFingerReader;
    requires com.machinezoo.sourceafis;
    requires java.logging;
    requires java.desktop;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires okhttp3;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.models to com.fasterxml.jackson.databind;

}
