package com.appointments.service.controller;

import com.appointments.service.email.EmailManager;
import com.appointments.service.model.Appointment;
import com.appointments.service.model.DTO.AllAppointmentsPerUserDTO;
import com.appointments.service.model.DTO.AppointmentRequestDTO;
import com.appointments.service.model.DTO.OneAppointmentByIdRequestDTO;
import com.appointments.service.model.DTO.OrganizationRequestDTO;
import com.appointments.service.model.Organization;
import com.appointments.service.model.OrganizationList;
import com.appointments.service.transaction.AppointmentTransactionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RestController
public class AppointmentsController {

    @Value("${url.organizations}")
    private String ORGANIZATIONS_URL;

    @Autowired
    AppointmentTransactionManager appointmentTransactionManager;

    @CrossOrigin
    @PostMapping(value = "/getAllAppointments")
    public List<Appointment> getAllAppointments(
            @RequestBody OrganizationRequestDTO organizationRequestDTO
    ) throws SQLException {

        return appointmentTransactionManager
                .getAllAppointmentsPerOrganization(organizationRequestDTO);
    }

    @CrossOrigin
    @PostMapping(value = "/getOneAppointment")
    public Appointment getOneAppointment(
            @RequestBody OneAppointmentByIdRequestDTO oneAppointmentByIdRequestDTO) {

        return appointmentTransactionManager.getAppointmentPerId(oneAppointmentByIdRequestDTO);
    }

    @CrossOrigin
    @PostMapping(value = "/getAllAppointmentsPerUser")
    public List<Appointment>  getAllAppointmentsPerUser(
            @RequestBody AllAppointmentsPerUserDTO allAppointmentsPerUserDTO) throws ParseException {

        return appointmentTransactionManager.getAllAppointmentsPerUser(allAppointmentsPerUserDTO);
    }

    @CrossOrigin
    @PostMapping(value = "/deleteOneAppointment")
    public void deleteOneAppointment(
            @RequestBody OneAppointmentByIdRequestDTO oneAppointmentByIdRequestDTO) {

        appointmentTransactionManager.deleteAppointmentPerId(oneAppointmentByIdRequestDTO);
    }


    @CrossOrigin
    @PostMapping(value = "/storeAppointment")
    public Boolean storeAppointment(
        @RequestBody AppointmentRequestDTO appointmentRequestDTO
        ) throws SQLException, IOException, JSONException {

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentRequestDTO.getAppointmentId());
        appointment.setOrganizationSelected(appointmentRequestDTO.getOrganizationSelected());
        appointment.setUserName(appointmentRequestDTO.getUserName());
        appointment.setUserId(appointmentRequestDTO.getUserId());
        appointment.setAdminName(appointmentRequestDTO.getAdminName());
        appointment.setAdminId(appointmentRequestDTO.getAdminId());
        appointment.setStartTime(Timestamp.valueOf(appointmentRequestDTO.getStartTime()));
        appointment.setEndTime(Timestamp.valueOf(appointmentRequestDTO.getEndTime()));
        appointment.setEmail(appointmentRequestDTO.getEmail());

        Boolean appointmentStored = appointmentTransactionManager.storeAppointment(appointment);

        if(appointmentStored){

            String start = Timestamp.valueOf(appointmentRequestDTO.getStartTime()).toString();
            String end = Timestamp.valueOf(appointmentRequestDTO.getEndTime()).toString();
            String email = appointmentRequestDTO.getEmail();

            EmailManager emailManager = new EmailManager();
            emailManager.sendEmailToUser(email, start, end);

            return Boolean.valueOf(true);

        } else {

            return Boolean.valueOf(false);

        }
    }



    @CrossOrigin
    @GetMapping(value = "/organizations")
    private String sendGET() throws IOException, JSONException {

        URL obj = new URL("https://hr.apografi.gov.gr/api/public/organizations");
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
            return null;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/organizations/new")
    private List<OrganizationList> getOrgs() throws IOException, JSONException {

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

            ObjectMapper mapper = new ObjectMapper();
            List<OrganizationList> orgs = Arrays.asList(mapper.readValue(response.toString(), OrganizationList.class));

            appointmentTransactionManager.storeAllOrganizationsToDB(orgs);

            return orgs;
        } else {
            return null;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getAllOrganizationsFromDatabase")
    public List<Organization>  getAllOrganizationsFromDatabase(){
        return appointmentTransactionManager.getAllOrganizationsFromDatabase();
    }

}
