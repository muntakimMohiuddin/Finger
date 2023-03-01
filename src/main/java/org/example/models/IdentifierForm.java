package org.example.models;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import org.example.IdentifyController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class IdentifierForm {
    Label firstNameText;
    Label lastNameText;
    Label nationalIdText;
    Label phoneNumberText;
    Label fingerprintCapturedLabel;
    Label userIDText;


    Ellipse LI;
    Ellipse LM;
    Ellipse LR;
    Ellipse LL;
    Ellipse LT;
    Ellipse RI;
    Ellipse RM;
    Ellipse RR;
    Ellipse RL;
    Ellipse RT;
    Circle LIC;
    Circle LMC;
    Circle LRC;
    Circle LLC;
    Circle LTC;
    Circle RIC;
    Circle RMC;
    Circle RRC;
    Circle RLC;
    Circle RTC;

    ImageView fingerprintSearchImageView;
    ImageView fingerprintImageView;
    ImageView placeFingerImageView;

    StackPane fingerprintPane;

    Label nameText;
    Label fatherNameText;
    Label motherNameText;
    Label genderText;
    Label religionText;
    Label ageText;
    Label placeOfBirthText;
    Label dateOfBirthText;
    Label villageText;
    Label districtText;
    Label countryText;
    Label addressText;
    Label policeStationText;
    Label nationalityText;
    Label dateOfEntryText;
    Label createdByText;
    Label creationDateText;
    Label machineIdText;
    ImageView photoImageview;
    ImageView notFoundImageview;
    AnchorPane infoPane;
    HashMap<Ellipse, Circle> fingerMapping = new HashMap<>();
    private Circle selectedCircle;
    HashMap<Circle, String> fingerNameMapping = new HashMap<>();
    String readMode;
    RadioButton readFromFile;
    RadioButton readFromSensor;
    HashMap<RadioButton, String> radioButtonReadModeMapping = new HashMap<>();
    Button fileChooserButton;

    String selectedFilePath;

    public IdentifierForm(Ellipse LI, Ellipse LM, Ellipse LR, Ellipse LL, Ellipse LT, Ellipse RI, Ellipse RM, Ellipse RR, Ellipse RL, Ellipse RT, Circle LIC, Circle LMC, Circle LRC, Circle LLC, Circle LTC, Circle RIC, Circle RMC, Circle RRC, Circle RLC, Circle RTC, ImageView fingerprintSearchImageView, ImageView fingerprintImageView, ImageView placeFingerImageView, ImageView photoImageview, ImageView notFoundImageview, StackPane fingerprintPane, AnchorPane infoPane, Label nameText, Label fatherNameText, Label motherNameText, Label genderText, Label religionText, Label ageText, Label placeOfBirthText, Label dateOfBirthText, Label villageText, Label districtText, Label countryText, Label addressText, Label policeStationText, Label nationalityText, Label dateOfEntryText, Label createdByText, Label creationDateText, Label machineIdText, RadioButton readFromFile, RadioButton readFromSensor, Button fileChooserButton) {
        this.LI = LI;
        this.LM = LM;
        this.LR = LR;
        this.LL = LL;
        this.LT = LT;
        this.RI = RI;
        this.RM = RM;
        this.RR = RR;
        this.RL = RL;
        this.RT = RT;
        this.LIC = LIC;
        this.LMC = LMC;
        this.LRC = LRC;
        this.LLC = LLC;
        this.LTC = LTC;
        this.RIC = RIC;
        this.RMC = RMC;
        this.RRC = RRC;
        this.RLC = RLC;
        this.RTC = RTC;
        this.fingerprintSearchImageView = fingerprintSearchImageView;
        this.fingerprintImageView = fingerprintImageView;
        this.placeFingerImageView = placeFingerImageView;
        this.photoImageview = photoImageview;
        this.notFoundImageview = notFoundImageview;
        this.fingerprintPane = fingerprintPane;
        this.infoPane = infoPane;
        this.nameText = nameText;
        this.fatherNameText = fatherNameText;
        this.motherNameText = motherNameText;
        this.genderText = genderText;
        this.religionText = religionText;
        this.ageText = ageText;
        this.placeOfBirthText = placeOfBirthText;
        this.dateOfBirthText = dateOfBirthText;
        this.villageText = villageText;
        this.districtText = districtText;
        this.countryText = countryText;
        this.addressText = addressText;
        this.policeStationText = policeStationText;
        this.nationalityText = nationalityText;
        this.dateOfEntryText = dateOfEntryText;
        this.createdByText = createdByText;
        this.creationDateText = creationDateText;
        this.machineIdText = machineIdText;
        this.readFromFile = readFromFile;
        this.readFromSensor = readFromSensor;
        this.fileChooserButton = fileChooserButton;
        init();
    }

    private void init() {
        fingerMapping.put(LI, LIC);
        fingerMapping.put(LM, LMC);
        fingerMapping.put(LR, LRC);
        fingerMapping.put(LL, LLC);
        fingerMapping.put(LT, LTC);
        fingerMapping.put(RI, RIC);
        fingerMapping.put(RM, RMC);
        fingerMapping.put(RR, RRC);
        fingerMapping.put(RL, RLC);
        fingerMapping.put(RT, RTC);
        fingerNameMapping.put(LIC, "LI");
        fingerNameMapping.put(LMC, "LM");
        fingerNameMapping.put(LRC, "LR");
        fingerNameMapping.put(LLC, "LL");
        fingerNameMapping.put(LTC, "LT");
        fingerNameMapping.put(RIC, "RI");
        fingerNameMapping.put(RMC, "RM");
        fingerNameMapping.put(RRC, "RR");
        fingerNameMapping.put(RLC, "RL");
        fingerNameMapping.put(RTC, "RT");
        radioButtonReadModeMapping.put(readFromSensor, "Sensor");
        radioButtonReadModeMapping.put(readFromFile, "File");
        ToggleGroup toggleGroup = new ToggleGroup();
        this.readFromSensor.setToggleGroup(toggleGroup);
        this.readFromFile.setToggleGroup(toggleGroup);
        clearAllSettings();
    }

    void clearAllSettings() {
        readMode = "Sensor";
        clearAllFingerSelections();
        clearVisibilityForAll();
        readFromSensor.setSelected(true);
    }

    public IdentifierForm(Label userIDText, Label firstNameText, Label lastNameText, Label nationalIdText, Label phoneNumberText, Label fingerprintCapturedLabel, ImageView fingerprintImageView) {
        this.userIDText = userIDText;
        this.firstNameText = firstNameText;
        this.lastNameText = lastNameText;
        this.nationalIdText = nationalIdText;
        this.phoneNumberText = phoneNumberText;
        this.fingerprintCapturedLabel = fingerprintCapturedLabel;
        this.fingerprintImageView = fingerprintImageView;
//        fingerprintPane.setVisible(false);
    }

    public void onCapture(byte[] fingerprint) {
//        fingerprintImageView.setImage(new Image(new ByteArrayInputStream(fingerprint)));
        scanCompleted(fingerprint);
//        fingerprintCapturedLabel.setText("Searching...");
//        fingerprintCapturedLabel.setTextFill(Color.color(1, .7, 0));
    }

    public void onMatch(RohingaDomainModel rohingaDomainModel) {
        clearVisibilityForAll();
        Platform.runLater(() -> {
            infoPane.setVisible(true);
            photoImageview.setImage(null);
            nameText.setText(rohingaDomainModel.getNameEng());
            fatherNameText.setText(rohingaDomainModel.getFatherNameEng());
            motherNameText.setText(rohingaDomainModel.getMotherNameEng());
            genderText.setText(rohingaDomainModel.getGender());
            religionText.setText(rohingaDomainModel.getReligion());
            ageText.setText(String.valueOf(rohingaDomainModel.getAge()));
            placeOfBirthText.setText(rohingaDomainModel.getPlaceOfBirth());
            dateOfBirthText.setText(String.valueOf(rohingaDomainModel.getDateOfBirth()));
            villageText.setText(rohingaDomainModel.getVillage());
            districtText.setText(rohingaDomainModel.getDistrict());
            countryText.setText(rohingaDomainModel.getCountry());
            addressText.setText(rohingaDomainModel.getAddress());
            policeStationText.setText(rohingaDomainModel.getPoliceStation());
            nationalityText.setText(rohingaDomainModel.getNationality());
            dateOfEntryText.setText(String.valueOf(rohingaDomainModel.getDateOfBirth()));
            createdByText.setText(rohingaDomainModel.getCreatedBy());
            creationDateText.setText(String.valueOf(rohingaDomainModel.getCreationDate()));
            machineIdText.setText(rohingaDomainModel.getMachineId());
//        fingerprintCapturedLabel.setText("Found");
//        fingerprintCapturedLabel.setTextFill(Color.color(0, 1, 0));
            photoImageview.setImage(new Image(new ByteArrayInputStream(rohingaDomainModel.getPhoto())));
            fingerprintPane.setVisible(false);
        });
    }

    public void onNoMatch() {
        System.out.println("No match");
        Platform.runLater(() -> {
            clearVisibilityForAll();
            notFoundImageview.setVisible(true);
        });
    }

    public void mouseEnterOnFingers(MouseEvent event) {
        Ellipse finger = ((Ellipse) event.getTarget());
        finger.setFill(Color.color(0, 0, 0, 0.25));
    }

    public void mouseExitFromFingers(MouseEvent event) {
        Ellipse finger = ((Ellipse) event.getTarget());
        finger.setFill(Color.color(0, 0, 0, 0));
    }

    public void fingerSelected(MouseEvent event) {
        Ellipse finger = ((Ellipse) event.getTarget());
        clearAllFingerSelections();
        fingerMapping.get(finger).setFill(Color.color(0, 1, 0, 1));
        selectedCircle = fingerMapping.get(finger);
        System.out.println("finger selected");
//        scanCompleted();
    }

    public void promptUserForFingerprint() {
        new Thread(() -> {
            Platform.runLater(() -> {
                        clearVisibilityForAll();
                        if (readMode.equals("Sensor")) {
                            if (placeFingerImageView.getImage() == null) {
                                placeFingerImageView.setImage(new Image(IdentifyController.class.getResource("fingerprint_place.gif").toExternalForm()));
                            }
                            placeFingerImageView.setVisible(true);

                        } else if (readMode.equals("File")) {
                            fileChooserButton.setVisible(true);
                        }
                    }
            );
        }
        ).start();
    }

    void clearAllFingerSelections() {
        for (Map.Entry<Ellipse, Circle> finger : fingerMapping.entrySet()) {
            finger.getValue().setFill(Color.color(0, 0, 0, 0));
        }
        selectedCircle = null;

    }


    public void scanCompleted(byte[] fingerprint) {
        new Thread(() -> Platform.runLater(() -> {
            clearVisibilityForAll();
            fingerprintPane.setVisible(true);
            fingerprintImageView.setImage(new Image(new ByteArrayInputStream(fingerprint)));
            if (fingerprintSearchImageView.getImage() == null) {
                fingerprintSearchImageView.setImage(new Image(IdentifyController.class.getResource("fingerprint_scan.gif").toExternalForm()));
            }
        })).start();
    }

    public void toggleReadMode(ActionEvent event) {
        for (Map.Entry<RadioButton, String> item : radioButtonReadModeMapping.entrySet()) {
            if (item.getKey() == event.getSource()) {
                readMode = item.getValue();
            }
        }
        System.out.println(readMode);
    }


    public String getSelectedFingerName() {
        return fingerNameMapping.get(selectedCircle);

    }

    void clearVisibilityForAll() {
        notFoundImageview.setVisible(false);
        infoPane.setVisible(false);
        fingerprintPane.setVisible(false);
        placeFingerImageView.setVisible(false);
        fileChooserButton.setVisible(false);

    }

    public String getSelectedFilePath() {
        return selectedFilePath;
    }

    public String getReadMode() {
        return readMode;
    }

    @SuppressWarnings("All")
    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            selectedFilePath = file.toString();
        }
        System.out.println(file);
    }
}
