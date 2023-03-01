package org.example;

import imagelib.wsq_imageio.src.org.jnbis.Bitmap;
import imagelib.wsq_imageio.src.org.jnbis.WSQEncoder;
import org.example.fao.SourceAFISFingerprintMatcher;
import org.example.fao.ZKFingerprintReader;
import org.example.interfaces.FingerprintMatcher;
import org.example.interfaces.FingerprintReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
//        FingerprintReader fingerprintReader = new ZKFingerprintReader();
        String filename="/home/muntakim/Fingerprint-2/103_3.tif";
//        boolean t;
//        while (!(t = fingerprintReader.isFingerPlaced())) {
////           System.out.println(t);
//        }
//        System.out.println(t);
//        fingerprintReader.writeFingerprintToFile("temp.bmp");
//        FingerprintMatcher fingerprintMatcher = new SourceAFISFingerprintMatcher("src/main/capturedFingerprints");
//        System.out.println(fingerprintMatcher.match(filename));

        BufferedImage bufferedImage=ImageIO.read(new File(filename));

// 2. Convert Bit depth to 8-gray (This is what i had to do to solve this problem)
        BufferedImage img = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();

// 3. Convert file format to byte[] and convert to type Bitmap
        WritableRaster raster = img.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
        Bitmap bitmap = new Bitmap(data.getData(), bufferedImage.getWidth(), bufferedImage.getHeight(), 500, 256, 1);
        OutputStream outputStream = new FileOutputStream("c.wsq");
        double bitrate = 0.75f;
        String commentText = "";

// 5. Write the input file to the generated wsq file
        WSQEncoder.encode(outputStream, bitmap, bitrate, commentText);
        outputStream.close();
    }
}
