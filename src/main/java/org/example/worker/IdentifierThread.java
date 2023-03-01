package org.example.worker;

import org.example.api.RohingaIdentifierApi;
import org.example.interfaces.FingerprintReader;
import org.example.models.IdentifierForm;
import org.example.models.RohingaDomainModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IdentifierThread extends Thread {
    FingerprintReader fingerprintReader;
    RohingaIdentifierApi rohingaIdentifierApi;
    IdentifierForm identifierForm;


    public IdentifierThread(IdentifierForm identifierForm, FingerprintReader fingerprintReader) {
        this.identifierForm = identifierForm;
        this.fingerprintReader = fingerprintReader;
        rohingaIdentifierApi = new RohingaIdentifierApi();
    }

    @Override
    public void run() {
        super.run();
        System.out.println("identifier thread started");
        identifierForm.promptUserForFingerprint();
        try {
            while (!interrupted()) {
                byte[] fingerprint = null;
                if (identifierForm.getReadMode().equals("File")) {
                    String filePath = identifierForm.getSelectedFilePath();
                    if (filePath != null) {
                        fingerprint = Files.readAllBytes(Paths.get(filePath));
                    }
                } else {
                    fingerprint = fingerprintReader.readFingerprint();
                }
                if (fingerprint != null) {
                    System.out.println("Captured fingerprint ...");
                    onCaptureOK(fingerprint);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("identifier thread closed");
        }
    }

    private void onCaptureOK(byte[] fingerprint) {
        try {
//            Platform.runLater(() -> {
            identifierForm.onCapture(fingerprint);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }


            String selectedFinger = identifierForm.getSelectedFingerName();
            System.out.println(selectedFinger);
            RohingaDomainModel rohingaDomainModel = selectedFinger == null ? rohingaIdentifierApi.match(fingerprint) : rohingaIdentifierApi.matchSpecificFinger(fingerprint, selectedFinger);
            if (rohingaDomainModel != null) {
                onMatch(rohingaDomainModel);
            } else {
                onNomatch();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void onMatch(RohingaDomainModel rohingaDomainModel) {
        identifierForm.onMatch(rohingaDomainModel);


    }

    void onNomatch() {
        identifierForm.onNoMatch();

    }

}
