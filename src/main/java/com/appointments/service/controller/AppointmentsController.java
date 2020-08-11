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

        return appointmentTransactionManager
                .getAllAppointmentsPerOrganization(organization);
    }

}
