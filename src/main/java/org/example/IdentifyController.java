package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.models.IdentifierForm;
import org.example.worker.IdentifierThread;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IdentifyController implements Initializable {
    public static String firstName;
    public static String lastName;
    public static String nationalId;
    public static String phoneNumber;

    public static byte[] image;

    @FXML
    Label firstNameText;
    @FXML
    Label lastNameText;
    @FXML
    Label nationalIdText;
    @FXML
    Label phoneNumberText;
    @FXML
    Label fingerprintCapturedLabel;

    @FXML
    Label userIDText;

    @FXML
    ImageView fingerprintImageView;



    IdentifierThread identifierThread;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        identifierThread = new IdentifierThread(fingerprintImageView, userIDText, firstNameText, lastNameText, nationalIdText, phoneNumberText, HomeController.fingerprintReader, HomeController.fingerprintMatcher);
        identifierThread = new IdentifierThread(new IdentifierForm(userIDText, firstNameText, lastNameText, nationalIdText, phoneNumberText, fingerprintCapturedLabel, fingerprintImageView), HomeController.fingerprintReader);
        identifierThread.start();
    }


    @FXML
    void backButtonAction(MouseEvent event) throws IOException, InterruptedException {
        identifierThread.interrupt();
        Thread.sleep(500);
        App.setRoot("home");
    }
}
