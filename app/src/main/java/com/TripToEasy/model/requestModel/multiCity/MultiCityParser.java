package com.TripToEasy.model.requestModel.multiCity;

import java.util.List;

/**
 * Created by ptblr-1195 on 1/3/18.
 */

public class MultiCityParser {
    private String AdultCount;
    private String ChildCount;
    private String InfantCount;
    private String JourneyType;
    private String PreferredAirlines;
    private Boolean mobile;
    private List<MultiCitySegment> Segments = null;

    public MultiCityParser(String adultCount, String childCount, String infantCount,
                           String journeyType, String preferredAirlines, Boolean mobile,
                           List<MultiCitySegment> segments) {
        AdultCount = adultCount;
        ChildCount = childCount;
        InfantCount = infantCount;
        JourneyType = journeyType;
        PreferredAirlines = preferredAirlines;
        this.mobile = mobile;
        Segments = segments;
    }
}
