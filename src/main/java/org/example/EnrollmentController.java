package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.models.EnrollmentForm;
import org.example.worker.EnrollerThread;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;

import static org.example.HomeController.fingerprintMatcher;

public class EnrollmentController implements Initializable {
    public static String firstName;
    public static String lastName;
    public static String nationalId;
    public static String phoneNumber;

    public static byte[] image;
    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField nationalIdTextField;
    @FXML
    TextField phoneNumberTextField;
    @FXML
    Label fingerprintCapturedLabel;
    @FXML
    ImageView fingerprintImageView;
    EnrollerThread enrollerThread;
    @FXML
    private Button enrollButton;

    public EnrollmentForm enrollmentForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        enrollerThread = new EnrollerThread(fingerprintImageView, fingerprintCapturedLabel, HomeController.fingerprintReader);
        enrollmentForm=new EnrollmentForm(firstNameTextField,lastNameTextField,nationalIdTextField,phoneNumberTextField,fingerprintCapturedLabel,fingerprintImageView);
        enrollerThread = new EnrollerThread(enrollmentForm, HomeController.fingerprintReader);
        enrollerThread.start();
    }

    @FXML
    void enrollButtonAction(ActionEvent event) throws IOException {
        System.out.println("enrolled");
        firstName = firstNameTextField.getText();
        lastName = lastNameTextField.getText();
        nationalId = nationalIdTextField.getText();
        phoneNumber = phoneNumberTextField.getText();
        String fileName = firstName + "####" + lastName + "####" + nationalId + "####" + phoneNumber + "####" + UUID.randomUUID() + ".bmp";
        Files.move(Paths.get("temp.bmp"), Paths.get(App.fingerprintDir + "/" + fileName));
        try {
            enrollmentForm.clear();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void backButtonAction(MouseEvent event) throws IOException, InterruptedException {
        enrollerThread.interrupt();
        Thread.sleep(500);
        App.setRoot("home");
    }
}
