package com.comp90018.drpet;

/**
 * @author xinli
 * @date 2019-09-30
 */
public class DoctorModel {
    private String doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorInfo;
    private String doctorPhone;
    private String hospitalId;
    public DoctorModel(){}
    public DoctorModel(String doctorId, String doctorFirstName, String doctorLastName, String doctorInfo, String doctorPhone, String hospitalId) {
        this.doctorId = doctorId;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.doctorInfo = doctorInfo;
        this.doctorPhone = doctorPhone;
        this.hospitalId = hospitalId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}

