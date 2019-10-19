package com.comp90018.drpet;

public class Appointment {
    private String appointmentID;
    private String doctorID;
    private String userID;
    private String petID;
    private String petName;
    private String comment;
    private String startTime;
    private String date;
    private String userName;
    private String userEmail;
    private String status;

    private String doctorFirstName;
    private String doctorLastName;
    private String hospitalName;

    public Appointment() {
    }

    public Appointment(String appointmentID, String doctorID, String userID, String petID, String petName, String comment, String startTime, String date, String userName, String userEmail, String status, String doctorFirstName, String doctorLastName, String hospitalName) {
        this.appointmentID = appointmentID;
        this.doctorID = doctorID;
        this.userID = userID;
        this.petID = petID;
        this.petName = petName;
        this.comment = comment;
        this.startTime = startTime;
        this.date = date;
        this.userName = userName;
        this.userEmail = userEmail;
        this.status = status;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.hospitalName = hospitalName;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
