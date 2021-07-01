package com.TripToEasy.model.requestModel.login;

/**
 * Created by ptblr-1174 on 18/5/18.
 */

public class UserSignUp {
    String first_name;
    String last_name;
    String phone;
    String email;
    String password;
    String confirm_password;
    String tc;

    public UserSignUp(String first_name,String last_name, String phone, String email, String password, String confirm_password, String tc) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
        this.tc = tc;
    }
}
