package com.sjce.finalyearproject.paperempower;

/**
 * Created by Ankit on 3/19/2018.
 */

public class HousesInfo {
    public String fullname;
    public String address;
    public String phonenumber;
    public String zipcode;
    public String key;
    public double latitude;
    public double longitude;

    public HousesInfo(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public HousesInfo(String name, String address, String number, String zip, double latitude, double longitude, String key){
        this.fullname=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.phonenumber=number;
        this.zipcode = zip;
        this.key = key;
    }
}
