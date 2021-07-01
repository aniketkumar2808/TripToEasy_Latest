package com.TripToEasy.model.parsingModel.bus;

public class PickUpBean {

	private String provider_id;
	private String pickupid;
	private String pickuptime;
	private String pickupname;
	private String Address;
	private String landmark;
	private String ProviderPickupId;
	private String contact="";

	public String getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
	}

	public String getPickupid() {
		return pickupid;
	}

	public void setPickupid(String pickupid) {
		this.pickupid = pickupid;
	}

	public String getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(String pickuptime) {
		this.pickuptime = pickuptime;
	}

	public String getPickupname() {
		return pickupname;
	}

	public void setPickupname(String pickupname) {
		this.pickupname = pickupname;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getProviderPickupId() {
		return ProviderPickupId;
	}

	public void setProviderPickupId(String providerPickupId) {
		ProviderPickupId = providerPickupId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}