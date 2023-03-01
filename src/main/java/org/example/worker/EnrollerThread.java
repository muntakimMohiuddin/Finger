package org.example.worker;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.interfaces.FingerprintReader;
import org.example.models.EnrollmentForm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EnrollerThread extends Thread {
    ImageView fingerprintImageView;
    Label fingerprintLabel;
    FingerprintReader fingerprintReader;
    EnrollmentForm enrollmentForm;

    public EnrollerThread(ImageView fingerprintImageView, Label fingerprintLabel, FingerprintReader fingerprintReader) {
        super();
        this.fingerprintImageView = fingerprintImageView;
        this.fingerprintLabel = fingerprintLabel;
        this.fingerprintReader = fingerprintReader;
    }

    public EnrollerThread(EnrollmentForm enrollmentForm, FingerprintReader fingerprintReader) {
        super();
        this.enrollmentForm = enrollmentForm;
        this.fingerprintReader = fingerprintReader;
    }

    @Override
    public void run() {
        super.run();
        int[] templateLen = new int[1];
        int readStatus;
        byte[] template = new byte[2048];
        byte[] imgbuf = new byte[10];
        try {
            while (!interrupted()) {
                templateLen[0] = 2048;
                byte[] fingerprint=fingerprintReader.readFingerprint();
                if (fingerprint!=null) {
                     System.out.println("Captured fingerprint ...");
                    onCatpureOK("temp.bmp");
                }
            }
        } catch (Exception e) {
            System.out.println("thread closed");
        }
    }

    private void onCatpureOK(String filename) {
        try {
//            fingerprintReader.writeFingerprintToFile(filename);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // do your GUI stuff here
                    try {
//                        fingerprintImageView.setImage(new Image(new FileInputStream(filename)));
//                        fingerprintLabel.setText("Fingerprint Captured");
                        enrollmentForm.onCapture(filename);
                        System.out.println("Fingerprint Captured label set");


                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
