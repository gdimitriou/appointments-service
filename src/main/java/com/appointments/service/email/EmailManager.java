package com.appointments.service.email;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class EmailManager {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String url = "http://localhost:8082/sendemail";

    public static final OkHttpClient client = new OkHttpClient();

    public EmailManager() {
    }

    public String sendEmailToUser(String emailAddress, String start, String end, String organization) throws JSONException, UnsupportedEncodingException {

        String emailSubject = "NEW APPOINTMENT";

        System.out.println(organization);

        String emailBody = "You have a new appointment with " + organization + " at " + start + "";

        String json = new JSONObject()
                .put("emailSubject", emailSubject)
                .put("emailBody", new String(emailBody.getBytes(), StandardCharsets.UTF_8))
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
