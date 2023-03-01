package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import org.example.interfaces.FingerprintReader;
import org.example.models.IdentifierForm;
import org.example.worker.IdentifierThread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Hand implements Initializable {

    @FXML
    Ellipse LI;
    @FXML
    Ellipse LM;
    @FXML
    Ellipse LR;
    @FXML
    Ellipse LL;
    @FXML
    Ellipse LT;
    @FXML
    Ellipse RI;
    @FXML
    Ellipse RM;
    @FXML
    Ellipse RR;
    @FXML
    Ellipse RL;
    @FXML
    Ellipse RT;
    @FXML
    Circle LIC;
    @FXML
    Circle LMC;
    @FXML
    Circle LRC;
    @FXML
    Circle LLC;
    @FXML
    Circle LTC;
    @FXML
    Circle RIC;
    @FXML
    Circle RMC;
    @FXML
    Circle RRC;
    @FXML
    Circle RLC;
    @FXML
    Circle RTC;

    @FXML
    ImageView fingerprintSearchImageView;
    @FXML
    ImageView fingerprintImageView;
    @FXML
    ImageView placeFingerImageView;

    @FXML
    StackPane fingerprintPane;

    @FXML
    Label nameText;
    @FXML
    Label fatherNameText;
    @FXML
    Label motherNameText;
    @FXML
    Label genderText;
    @FXML
    Label religionText;
    @FXML
    Label ageText;
    @FXML
    Label placeOfBirthText;
    @FXML
    Label dateOfBirthText;
    @FXML
    Label villageText;
    @FXML
    Label districtText;
    @FXML
    Label countryText;
    @FXML
    Label addressText;
    @FXML
    Label policeStationText;
    @FXML
    Label nationalityText;
    @FXML
    Label dateOfEntryText;
    @FXML
    Label createdByText;
    @FXML
    Label creationDateText;
    @FXML
    Label machineIdText;

    @FXML
    ImageView photoImageView;
    @FXML
    ImageView notFoundImageview;
    @FXML
    AnchorPane infoPane;
    @FXML
    RadioButton readFromFile;
    @FXML
    RadioButton readFromSensor;
    @FXML
    Button fileChooserButton;

    HashMap<Ellipse, Circle> fingerMapping;
    IdentifierForm identifierForm;
    IdentifierThread identifierThread;


    FingerprintReader fingerprintReader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        identifierForm = new IdentifierForm(LI, LM, LR, LL, LT, RI, RM, RR, RL, RT,
                LIC, LMC, LRC, LLC, LTC, RIC, RMC, RRC, RLC, RTC, fingerprintSearchImageView, fingerprintImageView,
                placeFingerImageView, photoImageView, notFoundImageview, fingerprintPane, infoPane, nameText, fatherNameText, motherNameText, genderText,
                religionText, ageText, placeOfBirthText, dateOfBirthText, villageText, districtText, countryText,
                addressText, policeStationText, nationalityText, dateOfEntryText,
                createdByText, creationDateText, machineIdText, readFromFile, readFromSensor, fileChooserButton);
    }

    @FXML
    void mouseEnterOnFingers(MouseEvent event) {
        identifierForm.mouseEnterOnFingers(event);
//        Ellipse finger = ((Ellipse) event.getTarget());
//        finger.setFill(Color.color(0, 0, 0, 0.25));
    }

    @FXML
    void mouseExitFromFingers(MouseEvent event) {
        identifierForm.mouseExitFromFingers(event);
//        Ellipse finger = ((Ellipse) event.getTarget());
//        finger.setFill(Color.color(0, 0, 0, 0));
    }

    @FXML
    void fingerSelected(MouseEvent event) {
        identifierForm.fingerSelected(event);
        captureFingerprint(event);
        System.out.println("used " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000);
        System.out.println("total " + Runtime.getRuntime().totalMemory() / 1000);
        System.out.println("free" + Runtime.getRuntime().freeMemory() / 1000);
    }

    @FXML
    void identifyButtonPressed(ActionEvent event) {
        captureFingerprint(event);
//        new Thread(() -> {
//            Platform.runLater(() -> {
//                        if (placeFingerImageView.getImage() == null) {
//                            placeFingerImageView.setImage(new Image(this.getClass().getResource("fingerprint_place.gif").toExternalForm()));
//                        }
//                        placeFingerImageView.setVisible(true);
//                    }
//            );
//        }
//        ).start();
    }

    private void captureFingerprint(Event event) {
        if (identifierThread == null || !identifierThread.isAlive()) {
            HashMap<String, Object> extra = (HashMap<String, Object>) ((Node) event.getSource()).getScene().getWindow().getUserData();
            fingerprintReader = (FingerprintReader) extra.get("fingerprintReader");
            identifierThread = new IdentifierThread(identifierForm, fingerprintReader);
            identifierThread.start();

        }
    }


    @FXML
    void backButtonAction(MouseEvent event) throws IOException, InterruptedException {
//        fingerprintPane.setVisible(false);
        if (identifierThread != null) {
            identifierThread.interrupt();
            Thread.sleep(500);
            System.gc();
        }
        App.changeTo("home", event);
    }

    @FXML
    void toggleReadMode(ActionEvent event) {
        identifierForm.toggleReadMode(event);
//        System.out.println("toggle readmode");
    }

    @FXML
    void chooseFile(ActionEvent event){
        identifierForm.chooseFile(event);
    }
}
