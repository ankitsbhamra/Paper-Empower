package com.sjce.finalyearproject.paperempower;

import java.util.Date;
import java.util.List;

/**
 * Created by Ankit on 3/19/2018.
 */

public class HousesInfo {
    public String door;
    public String fullname;
    public String main;
    public String cross;
    public String block;
    public String additionalDetails;
    public String areaKey;
    public String phonenumber;
    public String zipcode;
    public String key;
    public String lastcollect;
    public double latitude;
    public double longitude;
    public boolean completed;
    public HousesInfo(){

    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getCross() {
        return cross;
    }

    public void setCross(String cross) {
        this.cross = cross;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block= block;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(String areaKey) {this.areaKey = areaKey;}

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

    public HousesInfo(String name, String areaKey,String door,String main,String cross,String block,String additionalDetails, String number, String zip, String lastcollect, double latitude, double longitude, String key){
        this.fullname=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.areaKey=areaKey;
        this.main=main;
        this.cross=cross;
        this.block=block;
        this.additionalDetails=additionalDetails;
        this.phonenumber=number;
        this.zipcode = zip;
        this.key = key;
        this.lastcollect = lastcollect;
        this.completed=false;
        this.door=door;
    }
}
