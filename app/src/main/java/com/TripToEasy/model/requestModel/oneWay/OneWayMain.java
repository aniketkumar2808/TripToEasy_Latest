package com.TripToEasy.model.requestModel.oneWay;

import java.util.List;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class OneWayMain {
    private String AdultCount;
    private String ChildCount;
    private String InfantCount;
    private String JourneyType;
    private List<OneWaySegment> Segments = null;
    private String PreferredAirlines;

    public OneWayMain(String adultCount, String childCount, String infantCount,
                      String journeyType, List<OneWaySegment> segments,
                      String preferredAirlines) {
        this.AdultCount = adultCount;
        this.ChildCount = childCount;
        this.InfantCount = infantCount;
        this.JourneyType = journeyType;
        this.Segments = segments;
        this.PreferredAirlines = preferredAirlines;
    }

}