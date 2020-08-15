package com.appointments.service.controller;

import com.appointments.service.model.Appointment;
import com.appointments.service.model.DTO.AppointmentRequestDTO;
import com.appointments.service.model.DTO.OrganizationRequestDTO;
import com.appointments.service.transaction.AppointmentTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class AppointmentsController {

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

}
