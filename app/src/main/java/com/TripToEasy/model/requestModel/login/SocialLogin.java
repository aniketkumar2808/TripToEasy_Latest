package com.TripToEasy.model.requestModel.login;

/**
 * Created by ptblr-1195 on 16/4/18.
 */

public class SocialLogin {
    String user_logged_in;
    String email;
    String first_name;
    String last_name;

    public SocialLogin(String user_logged_in, String email,
                       String first_name, String last_name) {
        this.user_logged_in = user_logged_in;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}