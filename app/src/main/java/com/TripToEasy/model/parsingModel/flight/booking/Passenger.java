package com.TripToEasy.model.parsingModel.flight.booking;

/**
 * Created by ptblr-1195 on 14/5/18.
 */

public class Passenger {
    private String passenger_type;
    private String lead_passenger;
    private String Title;
    private String FirstName;
    private String LastName;
    private String PassportIssueCountry;
    private String PassportNumber;
    private String PassportExpiry;
    private String DateOfBirth;
    private String Gender;

    public Passenger(String passenger_type, String lead_passenger, String title,
                     String firstName, String lastName, String passportIssueCountry,
                     String passportNumber, String passportExpiry, String dateOfBirth,
                     String gender) {
        this.passenger_type = passenger_type;
        this.lead_passenger = lead_passenger;
        Title = title;
        FirstName = firstName;
        LastName = lastName;
        PassportIssueCountry = passportIssueCountry;
        PassportNumber = passportNumber;
        PassportExpiry = passportExpiry;
        DateOfBirth = dateOfBirth;
        Gender = gender;
    }
}