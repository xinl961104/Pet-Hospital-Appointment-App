package com.comp90018.drpet;

/**
 * @author xinli
 * @date 2019-09-30
 */
public class HospitalModel {
    private String hospitalId;
    private String hospitalName;
    private String hospitalBackground;
    private String hospitalOpenhours;
    private String hospitalAddress;
    private String hospitalPhone;

    public HospitalModel(String hospitalId, String hospitalName, String hospitalBackground, String hospitalOpenhours, String hospitalAddress, String hospitalPhone) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalBackground = hospitalBackground;
        this.hospitalOpenhours = hospitalOpenhours;
        this.hospitalAddress = hospitalAddress;
        this.hospitalPhone = hospitalPhone;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalBackground() {
        return hospitalBackground;
    }

    public void setHospitalBackground(String hospitalBackground) {
        this.hospitalBackground = hospitalBackground;
    }

    public String getHospitalOpenhours() {
        return hospitalOpenhours;
    }

    public void setHospitalOpenhours(String hospitalOpenhours) {
        this.hospitalOpenhours = hospitalOpenhours;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }
}
