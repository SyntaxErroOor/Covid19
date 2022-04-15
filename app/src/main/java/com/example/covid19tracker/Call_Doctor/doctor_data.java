package com.example.covid19tracker.Call_Doctor;

public class doctor_data {

    private  int image_dactor;
    private String  name_doctor;
    private String Phone_doctor;

    public doctor_data(int image_dactor, String name_doctor, String phone_doctor) {
        this.image_dactor = image_dactor;
        this.name_doctor = name_doctor;
        this.Phone_doctor = phone_doctor;
    }

    public void setImage_dactor(int image_dactor) {
        this.image_dactor = image_dactor;
    }

    public void setName_doctor(String name_doctor) {
        this.name_doctor = name_doctor;
    }

    public void setPhone_doctor(String phone_doctor) {
        Phone_doctor = phone_doctor;
    }

    public int getImage_dactor() {
        return image_dactor;
    }

    public String getName_doctor() {
        return name_doctor;
    }

    public String getPhone_doctor() {
        return Phone_doctor;
    }
}
