package com.appointments.service.model;

import java.sql.Timestamp;

public class Appointment {

    private String appointmentId;
    private String organizationSelected;
    private String userName;
    private String userId;
    private String adminName;
    private String adminId;
    private Timestamp startTime;
    private Timestamp endTime;

    public Appointment() {
    }

    public Appointment(String appointmentId, String organizationSelected, String userName, String userId, String adminName, String adminId, Timestamp startTime, Timestamp endTime) {
        this.appointmentId = appointmentId;
        this.organizationSelected = organizationSelected;
        this.userName = userName;
        this.userId = userId;
        this.adminName = adminName;
        this.adminId = adminId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getOrganizationSelected() {
        return organizationSelected;
    }

    public void setOrganizationSelected(String organizationSelected) {
        this.organizationSelected = organizationSelected;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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