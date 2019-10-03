package com.comp90018.drpet;

/**
 * @author xinli
 * @date 2019-09-30
 */
public class TimeSlotModel {
    private String slotId;
    private String date;
    private String doctorId;
    private String flag;
    private String period;
    private String startTime;

    public TimeSlotModel(){}

    public TimeSlotModel(String slotId, String date, String doctorId, String flag, String period, String startTime) {
        this.slotId = slotId;
        this.date = date;
        this.doctorId = doctorId;
        this.flag = flag;
        this.period = period;
        this.startTime = startTime;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
