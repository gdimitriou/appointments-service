package com.appointments.service.model.DTO;

public class OneAppointmentByIdRequestDTO {

    private String appointmentId;
    private String organization;

    public OneAppointmentByIdRequestDTO() {
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
