package com.example.covid19tracker.Hospitals;

import android.graphics.Bitmap;

public class Hospitals_Data {


    private Bitmap image_hospital;
    private String  name_hospital;
    private String hospital_address;
    private String  image_url;
    private String  phone_hospital;


    public Hospitals_Data( String name_hospital, String hospital_address, String image_url,String  phone_hospital) {
        this.name_hospital = name_hospital;
        this.hospital_address = hospital_address;
        this.image_url = image_url;
        this.phone_hospital=phone_hospital;
    }

    public void setImage_hospital(Bitmap image_hospital) {
        this.image_hospital = image_hospital;
    }

    public void setName_hospital(String name_hospital) {
        this.name_hospital = name_hospital;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Bitmap getImage_hospital() {
        return image_hospital;
    }

    public String getName_hospital() {
        return name_hospital;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getPhone_hospital() {
        return phone_hospital;
    }
}
