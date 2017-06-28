package com.example.rijunath.friendforever;

/**
 * Created by RIJU NATH on 4/27/2017.
 */
public class UserDetail {
    public String fname;
    public String lname;
    public String Email;
    public String RequestId;
    public String Online_Offline;

    public String getOnline_Offline() {
        return Online_Offline;
    }

    public void setOnline_Offline(String online_Offline_Flag) {
        Online_Offline = online_Offline_Flag;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }


}
