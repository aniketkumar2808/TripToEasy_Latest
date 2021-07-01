package com.TripToEasy.model.parsingModel;

import java.io.Serializable;

/**
 * Created by ptblr-1174 on 10/12/18.
 */

public class PromoCodeInfo implements Serializable {
    String module;
    String promo_code;
    String description;
    String expiry_date;
    String status;

    public String getPromo_code_image_path() {
        return promo_code_image_path;
    }

    public void setPromo_code_image_path(String promo_code_image_path) {
        this.promo_code_image_path = promo_code_image_path;
    }

    String promo_code_image_path;

    public PromoCodeInfo(String module, String promo_code, String description, String expiry_date, String status,String promo_code_image_path) {
        this.module = module;
        this.promo_code = promo_code;
        this.description = description;
        this.expiry_date = expiry_date;
        this.status = status;
        this.promo_code_image_path = promo_code_image_path;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
