package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.HomeController;
import org.example.models.RohingaDomainModel;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RohingaIdentifierApi {
    String baseUrl = "http://" + HomeController.serverIp + ":9090/api/fingerprint";
    public RohingaDomainModel match(byte[] fingerprint) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).build();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "file_name.bmp",
                        RequestBody.create(MediaType.parse("image/*"), fingerprint)).build();
        Request request = new Request.Builder()
                .url(baseUrl + "/identify-from-fingerprint")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ObjectMapper objectMapper = new ObjectMapper();
        if (response.isSuccessful()) {
            RohingaDomainModel rohingaDomainModel = objectMapper.readValue(response.body().string(), RohingaDomainModel.class);

//        System.out.println((org.example.models.RohingaDomainModel)response.body());
            System.out.println(rohingaDomainModel);
            return rohingaDomainModel;
        }
        return null;
    }

    public RohingaDomainModel matchSpecificFinger(byte[] fingerprint, String finger) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).build();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "file_name.bmp",
                        RequestBody.create(MediaType.parse("image/*"), fingerprint)).
                addFormDataPart("finger", finger).build();
        Request request = new Request.Builder()
                .url(baseUrl + "/identify-from-fingerprint-specific")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ObjectMapper objectMapper = new ObjectMapper();
        if (response.isSuccessful()) {
            RohingaDomainModel rohingaDomainModel = objectMapper.readValue(response.body().string(), RohingaDomainModel.class);

//        System.out.println((org.example.models.RohingaDomainModel)response.body());
            System.out.println(rohingaDomainModel);
            return rohingaDomainModel;
        }
        return null;
    }
}
