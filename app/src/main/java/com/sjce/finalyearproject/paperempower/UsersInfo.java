package com.sjce.finalyearproject.paperempower;

/**
 * Created by Ankit on 3/19/2018.
 */

public class UsersInfo {
    public String fullname;
    public String emailid;
    public String phonenumber;

    public UsersInfo(){

    }
    public UsersInfo(String name,String email,String number){
        this.fullname = name;
        this.emailid = email;
        this.phonenumber = number;
    }
}
