package edu.fsu.cs.mobile.firebaseautoconnecttest;

/**
 * WRAPPER CLASS FOR USER DATA
 */

public class MyUser {
    private String email;
    private String id;

    public MyUser(){
        email = "";
        id = "";
    }

    public MyUser (String EMAIL, String ID){
        email = EMAIL;
        id = ID;
    }

    public String getEmail(){
        return email;
    }
    public String getId(){
        return id;
    }
}
