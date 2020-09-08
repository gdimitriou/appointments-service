package com.appointments.service.email;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;

public class EmailManager {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String url = "localhost:";

    public static final OkHttpClient client = new OkHttpClient();

    public EmailManager() {
    }

    public String sendEmailToUser(String emailAddress, String start, String organization) throws JSONException {

        String emailSubject = "NEW APPOINTMENT";

        String emailBody = "You have a new appointment with " +organization+ " at " + start + "";

        String json = new JSONObject()
                .put("emailSubject", emailSubject)
                .put("emailBody", emailBody)
                .put("emailAddress", emailAddress)
                .toString();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).post(body).build();

        try (Response response = client.newCall(request).execute()) {

            return response.body().string();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }
}
