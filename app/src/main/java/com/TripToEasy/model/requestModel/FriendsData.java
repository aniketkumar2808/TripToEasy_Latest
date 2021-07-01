package com.TripToEasy.model.requestModel;

import java.io.Serializable;

public class FriendsData implements Serializable {


    private String user_id;
    private String image;
    private String first_name;
    private String last_name;
    private String friend_status;
    private String policies;
    private String privacy;
    private String Email;



    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private boolean isTagged=false;


    public boolean isTagged() {
        return isTagged;
    }

    public void setTagged(boolean tagged) {
        isTagged = tagged;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFriend_status() {
        return friend_status;
    }

    public void setFriend_status(String friend_status) {
        this.friend_status = friend_status;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

}
