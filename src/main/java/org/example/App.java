package org.example;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX org.example.App
 */
public class App extends Application {

    public static String fingerprintDir = "src/main/capturedFingerprints";
    private static Scene scene;
    public static String fingerprintBgPath = "src/main/resources/org/example/fingerprint_bg.png";

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static void changeTo(String fxml, Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml + ".fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @SuppressWarnings("All")
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage = new Stage();
        primaryStage.setTitle("Fingerprint Auth");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        scene = new Scene(root, 600, 450);
//
//        Parent root = FXMLLoader.load(getClass().getResource("hand.fxml"));
//
//        scene = new Scene(root, 800, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}