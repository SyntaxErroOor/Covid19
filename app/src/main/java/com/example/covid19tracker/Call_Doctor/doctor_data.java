package com.example.covid19tracker.Call_Doctor;

import android.graphics.Bitmap;

public class doctor_data {

    private Bitmap image_dactor;
    private String  name_doctor;
    private String Phone_doctor;
    private String  image_url;

    public doctor_data(String image_url, String name_doctor, String phone_doctor) {
        this.image_url = image_url;
        this.name_doctor = name_doctor;
        this.Phone_doctor = phone_doctor;
    }

    void change_data(String text)
    {
      Phone_doctor=text;

    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setImage_dactor(Bitmap image_dactor) {
        this.image_dactor = image_dactor;
    }

    public void setName_doctor(String name_doctor) {
        this.name_doctor = name_doctor;
    }

    public void setPhone_doctor(String phone_doctor) {
        Phone_doctor = phone_doctor;
    }

    public Bitmap getImage_dactor() {
        return image_dactor;
    }

    public String getName_doctor() {
        return name_doctor;
    }

    public String getImage_url() {
        return image_url;
    }
    public String getPhone_doctor() {
        return Phone_doctor;
    }
}
