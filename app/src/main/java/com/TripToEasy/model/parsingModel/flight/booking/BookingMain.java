package com.TripToEasy.model.parsingModel.flight.booking;

import java.util.List;

/**
 * Created by ptblr-1195 on 14/5/18.
 */

public class BookingMain {
    private String token_key;
    private String Email;
    private String ContactNo;
    private String AddressLine1;
    private String City;
    private String PinCode;
    private String CountryCode;
    private String CountryName;
    private String search_id;
    private String total_amount_val;
    private String currency;
    private String currency_symbol;
    private String convenience_fee;
    private String customer_id;
    private String payment_method;
    private List<Passenger> Passengers = null;

    public BookingMain(String token_key, String email, String contactNo,
                       String addressLine1, String city, String pinCode,
                       String countryCode, String countryName, String search_id,
                       String total_amount_val, String currency,
                       String currency_symbol, String convenience_fee,
                       String customer_id, String payment_method,
                       List<Passenger> passengers) {
        this.token_key = token_key;
        Email = email;
        ContactNo = contactNo;
        AddressLine1 = addressLine1;
        City = city;
        PinCode = pinCode;
        CountryCode = countryCode;
        CountryName = countryName;
        this.search_id = search_id;
        this.total_amount_val = total_amount_val;
        this.currency = currency;
        this.currency_symbol = currency_symbol;
        this.convenience_fee = convenience_fee;
        this.customer_id = customer_id;
        this.payment_method = payment_method;
        Passengers = passengers;
    }
}
