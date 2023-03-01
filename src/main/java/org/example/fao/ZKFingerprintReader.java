package org.example.fao;

import com.zkteco.biometric.FingerprintSensorEx;
import org.example.interfaces.FingerprintReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZKFingerprintReader implements FingerprintReader {
    static byte[] template = new byte[2048];
    static byte[] imgBuf;
    int fpWidth, fpHeight;
    long fingerprintDevice, fingerprintDB;
    int[] templateLen = new int[1];
    int nFmt = 0;
    int readCount=0;
    public ZKFingerprintReader() throws Exception {
        init();
    }

    void init() throws Exception {
        FingerprintSensorEx.Init();
        int deviceCount = FingerprintSensorEx.GetDeviceCount();
        if (deviceCount < 1)
            throw new Exception("Fingerprint Device not Found");
        fingerprintDevice = FingerprintSensorEx.OpenDevice(0);
//        fingerprintDB = FingerprintSensorEx.DBInit();
//        FingerprintSensorEx.DBSetParameter(fingerprintDB, 5010, nFmt);
        byte[] paramValue = new byte[4];
        int[] size = new int[1];
        size[0] = 4;
        FingerprintSensorEx.GetParameters(fingerprintDevice, 1, paramValue, size);
        fpWidth = byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(fingerprintDevice, 2, paramValue, size);
        fpHeight = byteArrayToInt(paramValue);
        imgBuf = new byte[fpWidth * fpHeight];
    }

    public static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    public static byte[] intToByteArray(final int number) {
        byte[] abyte = new byte[4];
        // "&" ä¸Žï¼ˆANDï¼‰ï¼Œå¯¹ä¸¤ä¸ªæ•´åž‹æ“�ä½œæ•°ä¸­å¯¹åº”ä½�æ‰§è¡Œå¸ƒå°”ä»£æ•°ï¼Œä¸¤ä¸ªä½�éƒ½ä¸º1æ—¶è¾“å‡º1ï¼Œå�¦åˆ™0ã€‚
        abyte[0] = (byte) (0xff & number);
        // ">>"å�³ç§»ä½�ï¼Œè‹¥ä¸ºæ­£æ•°åˆ™é«˜ä½�è¡¥0ï¼Œè‹¥ä¸ºè´Ÿæ•°åˆ™é«˜ä½�è¡¥1
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

    public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight, String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth + 3) / 4) * 4);
        int bfType = 0x424d; // ä½�å›¾æ–‡ä»¶ç±»åž‹ï¼ˆ0â€”1å­—èŠ‚ï¼‰
        int bfSize = 54 + 1024 + w * nHeight;// bmpæ–‡ä»¶çš„å¤§å°�ï¼ˆ2â€”5å­—èŠ‚ï¼‰
        int bfReserved1 = 0;// ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—ï¼Œå¿…é¡»ä¸º0ï¼ˆ6-7å­—èŠ‚ï¼‰
        int bfReserved2 = 0;// ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—ï¼Œå¿…é¡»ä¸º0ï¼ˆ8-9å­—èŠ‚ï¼‰
        int bfOffBits = 54 + 1024;// æ–‡ä»¶å¤´å¼€å§‹åˆ°ä½�å›¾å®žé™…æ•°æ�®ä¹‹é—´çš„å­—èŠ‚çš„å��ç§»é‡�ï¼ˆ10-13å­—èŠ‚ï¼‰

        dos.writeShort(bfType); // è¾“å…¥ä½�å›¾æ–‡ä»¶ç±»åž‹'BM'
        dos.write(changeByte(bfSize), 0, 4); // è¾“å…¥ä½�å›¾æ–‡ä»¶å¤§å°�
        dos.write(changeByte(bfReserved1), 0, 2);// è¾“å…¥ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—
        dos.write(changeByte(bfReserved2), 0, 2);// è¾“å…¥ä½�å›¾æ–‡ä»¶ä¿�ç•™å­—
        dos.write(changeByte(bfOffBits), 0, 4);// è¾“å…¥ä½�å›¾æ–‡ä»¶å��ç§»é‡�

        int biSize = 40;// ä¿¡æ�¯å¤´æ‰€éœ€çš„å­—èŠ‚æ•°ï¼ˆ14-17å­—èŠ‚ï¼‰
        int biWidth = nWidth;// ä½�å›¾çš„å®½ï¼ˆ18-21å­—èŠ‚ï¼‰
        int biHeight = nHeight;// ä½�å›¾çš„é«˜ï¼ˆ22-25å­—èŠ‚ï¼‰
        int biPlanes = 1; // ç›®æ ‡è®¾å¤‡çš„çº§åˆ«ï¼Œå¿…é¡»æ˜¯1ï¼ˆ26-27å­—èŠ‚ï¼‰
        int biBitcount = 8;// æ¯�ä¸ªåƒ�ç´ æ‰€éœ€çš„ä½�æ•°ï¼ˆ28-29å­—èŠ‚ï¼‰ï¼Œå¿…é¡»æ˜¯1ä½�ï¼ˆå�Œè‰²ï¼‰ã€�4ä½�ï¼ˆ16è‰²ï¼‰ã€�8ä½�ï¼ˆ256è‰²ï¼‰æˆ–è€…24ä½�ï¼ˆçœŸå½©è‰²ï¼‰ä¹‹ä¸€ã€‚
        int biCompression = 0;// ä½�å›¾åŽ‹ç¼©ç±»åž‹ï¼Œå¿…é¡»æ˜¯0ï¼ˆä¸�åŽ‹ç¼©ï¼‰ï¼ˆ30-33å­—èŠ‚ï¼‰ã€�1ï¼ˆBI_RLEBåŽ‹ç¼©ç±»åž‹ï¼‰æˆ–2ï¼ˆBI_RLE4åŽ‹ç¼©ç±»åž‹ï¼‰ä¹‹ä¸€ã€‚
        int biSizeImage = w * nHeight;// å®žé™…ä½�å›¾å›¾åƒ�çš„å¤§å°�ï¼Œå�³æ•´ä¸ªå®žé™…ç»˜åˆ¶çš„å›¾åƒ�å¤§å°�ï¼ˆ34-37å­—èŠ‚ï¼‰
        int biXPelsPerMeter = 0;// ä½�å›¾æ°´å¹³åˆ†è¾¨çŽ‡ï¼Œæ¯�ç±³åƒ�ç´ æ•°ï¼ˆ38-41å­—èŠ‚ï¼‰è¿™ä¸ªæ•°æ˜¯ç³»ç»Ÿé»˜è®¤å€¼
        int biYPelsPerMeter = 0;// ä½�å›¾åž‚ç›´åˆ†è¾¨çŽ‡ï¼Œæ¯�ç±³åƒ�ç´ æ•°ï¼ˆ42-45å­—èŠ‚ï¼‰è¿™ä¸ªæ•°æ˜¯ç³»ç»Ÿé»˜è®¤å€¼
        int biClrUsed = 0;// ä½�å›¾å®žé™…ä½¿ç”¨çš„é¢œè‰²è¡¨ä¸­çš„é¢œè‰²æ•°ï¼ˆ46-49å­—èŠ‚ï¼‰ï¼Œå¦‚æžœä¸º0çš„è¯�ï¼Œè¯´æ˜Žå…¨éƒ¨ä½¿ç”¨äº†
        int biClrImportant = 0;// ä½�å›¾æ˜¾ç¤ºè¿‡ç¨‹ä¸­é‡�è¦�çš„é¢œè‰²æ•°(50-53å­—èŠ‚)ï¼Œå¦‚æžœä¸º0çš„è¯�ï¼Œè¯´æ˜Žå…¨éƒ¨é‡�è¦�

        dos.write(changeByte(biSize), 0, 4);// è¾“å…¥ä¿¡æ�¯å¤´æ•°æ�®çš„æ€»å­—èŠ‚æ•°
        dos.write(changeByte(biWidth), 0, 4);// è¾“å…¥ä½�å›¾çš„å®½
        dos.write(changeByte(biHeight), 0, 4);// è¾“å…¥ä½�å›¾çš„é«˜
        dos.write(changeByte(biPlanes), 0, 2);// è¾“å…¥ä½�å›¾çš„ç›®æ ‡è®¾å¤‡çº§åˆ«
        dos.write(changeByte(biBitcount), 0, 2);// è¾“å…¥æ¯�ä¸ªåƒ�ç´ å� æ�®çš„å­—èŠ‚æ•°
        dos.write(changeByte(biCompression), 0, 4);// è¾“å…¥ä½�å›¾çš„åŽ‹ç¼©ç±»åž‹
        dos.write(changeByte(biSizeImage), 0, 4);// è¾“å…¥ä½�å›¾çš„å®žé™…å¤§å°�
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// è¾“å…¥ä½�å›¾çš„æ°´å¹³åˆ†è¾¨çŽ‡
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// è¾“å…¥ä½�å›¾çš„åž‚ç›´åˆ†è¾¨çŽ‡
        dos.write(changeByte(biClrUsed), 0, 4);// è¾“å…¥ä½�å›¾ä½¿ç”¨çš„æ€»é¢œè‰²æ•°
        dos.write(changeByte(biClrImportant), 0, 4);// è¾“å…¥ä½�å›¾ä½¿ç”¨è¿‡ç¨‹ä¸­é‡�è¦�çš„é¢œè‰²æ•°

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth) {
            filter = new byte[w - nWidth];
        }

        for (int i = 0; i < nHeight; i++) {
            dos.write(imageBuf, (nHeight - 1 - i) * nWidth, nWidth);
            if (w > nWidth)
                dos.write(filter, 0, w - nWidth);
        }
        dos.flush();
        dos.close();
        fos.close();
    }
//    private void OnCatpureOK(byte[] imgBuf, String filename) {
//        try {
//            fingerprintImageView.setImage(new Image(new FileInputStream(filename)));
//            fingerprintLabel.setText("Fingerprint Captured");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="æŒ‰ä½�æˆ–èµ‹å€¼ã€‚
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    public boolean isFingerPlaced() {
        templateLen[0] = 2048;
        int t = FingerprintSensorEx.AcquireFingerprint(fingerprintDevice, imgBuf, template, templateLen);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 0 == t;
    }

    @Override
    public byte[] readFingerprint() {
        readCount++;
        if(readCount==100){
            readCount=0;
           System.gc();
            System.out.println("gc ran");
        }

        templateLen[0] = 2048;
        int status = FingerprintSensorEx.AcquireFingerprint(fingerprintDevice, imgBuf, template, templateLen);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (status == 0) {
            try {
                writeFingerprintToFile("temp.bmp");
                return Files.readAllBytes(Paths.get("temp.bmp"));
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public void writeFingerprintToFile(String filepath) throws IOException {
        writeBitmap(imgBuf, fpWidth, fpHeight, filepath);
    }


    private void freeSensor() {
//        mbStop = true;
        try {        //wait for thread stopping
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (0 != fingerprintDB) {
            FingerprintSensorEx.DBFree(fingerprintDB);
            fingerprintDB = 0;
        }
        if (0 != fingerprintDevice) {
            FingerprintSensorEx.CloseDevice(fingerprintDevice);
            fingerprintDevice = 0;
        }
        FingerprintSensorEx.Terminate();
    }
}
