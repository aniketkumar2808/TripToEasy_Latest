package com.TripToEasy.model.requestModel.holiday;

/**
 * Created by ptblr-1195 on 18/4/18.
 */

public class SendQuery {
    String package_id;
    String first_name;
    String phone;
    String email;
    String place;
    String message;

    public SendQuery(String package_id, String first_name, String phone,
                     String email, String place, String message) {
        this.package_id = package_id;
        this.first_name = first_name;
        this.phone = phone;
        this.email = email;
        this.place = place;
        this.message = message;
    }
}