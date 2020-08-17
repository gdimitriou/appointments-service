package com.appointments.service.model.DTO;

public class AllAppointmentsPerUserDTO {

    private String userId;
    private String organization;

    public AllAppointmentsPerUserDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
