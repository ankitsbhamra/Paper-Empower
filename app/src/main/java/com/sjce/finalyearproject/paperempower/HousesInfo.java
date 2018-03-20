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
    public HousesInfo(String name,String address,String number,String zip,double latitude, double longitude,String key){
        this.fullname=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.phonenumber=number;
        this.zipcode = zip;
        this.key = key;
    }
}
