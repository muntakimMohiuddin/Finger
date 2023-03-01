package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.fao.SourceAFISFingerprintMatcher;
import org.example.fao.ZKFingerprintReader;
import org.example.interfaces.FingerprintMatcher;
import org.example.interfaces.FingerprintReader;
import org.example.util.AppProperties;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public static FingerprintReader fingerprintReader;
    public static FingerprintMatcher fingerprintMatcher;
    public static String serverIp;
    @FXML
    private Button enrollButton;
    @FXML
    private Button indentifyButton;

    @FXML
    void enrollButtonAction(ActionEvent event) throws IOException {
        System.out.println("enroll");
        App.setRoot("enrollment");
    }

    @FXML
    void identifyButtonAction(ActionEvent event) throws Exception {
        System.out.println("identify");
        HashMap<String, Object> extra=new HashMap<>();
//        App.setRoot("identify");
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("hand.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        extra.put("fingerprintReader",fingerprintReader);
        stage.setUserData(extra);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverIp= AppProperties.getInstance().getProperty("serverIp");
        try {
            if (fingerprintReader == null) {

                fingerprintReader = new ZKFingerprintReader();
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }

    }
}
