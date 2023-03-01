package org.example.models;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class EnrollmentForm {
    TextField firstNameTextField;
    TextField lastNameTextField;
    TextField nationalIdTextField;
    TextField phoneNumberTextField;
    Label fingerprintCapturedLabel;
    ImageView fingerprintImageView;

    public EnrollmentForm(TextField firstNameTextField, TextField lastNameTextField, TextField nationalIdTextField, TextField phoneNumberTextField, Label fingerprintCapturedLabel, ImageView fingerprintImageView) {
        this.firstNameTextField = firstNameTextField;
        this.lastNameTextField = lastNameTextField;
        this.nationalIdTextField = nationalIdTextField;
        this.phoneNumberTextField = phoneNumberTextField;
        this.fingerprintCapturedLabel = fingerprintCapturedLabel;
        this.fingerprintImageView = fingerprintImageView;
    }

    public void onCapture(String filename) throws FileNotFoundException {
        fingerprintImageView.setImage(new Image(new FileInputStream(filename)));
        fingerprintCapturedLabel.setText("Fingerprint Captured");
    }

    public void clear() throws URISyntaxException {
        this.firstNameTextField.setText("");
        this.lastNameTextField.setText("");
        this.nationalIdTextField.setText("");
        this.phoneNumberTextField.setText("");
        this.fingerprintCapturedLabel.setText("");
        this.fingerprintImageView.setImage(new Image(new File(App.fingerprintBgPath).toURI().toString()));
    }




}
