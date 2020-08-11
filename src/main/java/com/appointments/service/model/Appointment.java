package com.appointments.service.model;

import java.sql.Timestamp;

public class Appointment {

    private String appointmentId;
    private String organization;
    private String userName;
    private String adminName;
    private Timestamp startTime;
    private Timestamp endTime;

    public Appointment() {
    }

    public Appointment(String appointmentId, String organization, String userName, String adminName, Timestamp startTime, Timestamp endTime) {
        this.appointmentId = appointmentId;
        this.organization = organization;
        this.userName = userName;
        this.adminName = adminName;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
