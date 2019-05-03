package com.travojet.interfaces;

import com.travojet.model.parsingModel.LocationModel;
import com.travojet.model.parsingModel.hotel.AmenitiesModel;

import java.util.List;

/**
 * Created by ptblr-1195 on 26/3/18.
 */

public interface HotelFilterNotify {

    public void hotelFilter(String minPrice, String maxPrice,
                            List<AmenitiesModel> amenities, List<Integer> starHotel,List<LocationModel> hotelLocations);

}