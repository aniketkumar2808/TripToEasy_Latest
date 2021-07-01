package com.TripToEasy.model.parsingModel;

/**
 * Created by ptblr-1174 on 31/7/18.
 */

public class CurrencyConverter {
    String id,status,country,value,currency_symbol;
    public CurrencyConverter(String id, String status, String country,
                             String value, String currency_symbol){
        this.id=id;
        this.status=status;
        this.country=country;
        this.value=value;
        this.currency_symbol=currency_symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }
}
