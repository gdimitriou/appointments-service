package com.appointments.service.controller;

import com.appointments.service.model.Appointment;
import com.appointments.service.model.DTO.AppointmentRequestDTO;
import com.appointments.service.model.DTO.OrganizationRequestDTO;
import com.appointments.service.transaction.AppointmentTransactionManager;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class AppointmentsController {

    @Value("${url.organizations}")
    private String ORGANIZATIONS_URL;

    @Autowired
    AppointmentTransactionManager appointmentTransactionManager;

    @PostMapping(value = "/getAllAppointments")
    public List<Appointment> getAllAppointments(
            @RequestBody OrganizationRequestDTO organizationRequestDTO
    ) throws SQLException {

        String organization = organizationRequestDTO.getOrganization();
        Timestamp from = Timestamp.valueOf(organizationRequestDTO.getStartTime());
        Timestamp to = Timestamp.valueOf(organizationRequestDTO.getEndTime());

        return appointmentTransactionManager
                .getAllAppointmentsPerOrganization(organization, from, to);
    }

    @PostMapping(value = "/storeAppointment")
    public Boolean storeAppointment(
        @RequestBody AppointmentRequestDTO appointmentRequestDTO
        ) throws SQLException {

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentRequestDTO.getAppointmentId());
        appointment.setOrganization(appointmentRequestDTO.getOrganization());
        appointment.setUserName(appointmentRequestDTO.getUserName());
        appointment.setUserId(appointmentRequestDTO.getUserId());
        appointment.setAdminName(appointmentRequestDTO.getAdminName());
        appointment.setAdminId(appointmentRequestDTO.getAdminId());
        appointment.setStartTime(Timestamp.valueOf(appointmentRequestDTO.getStartTime()));
        appointment.setEndTime(Timestamp.valueOf(appointmentRequestDTO.getEndTime()));

        return appointmentTransactionManager.storeAppointment(appointment);
    }

    @GetMapping(value = "/organizations")
    private String sendGET() throws IOException, JSONException {

        URL obj = new URL(ORGANIZATIONS_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            return "Something went wrong";
        }


    }

}
