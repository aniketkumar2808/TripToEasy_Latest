package com.github.sundeepk.compactcalendarview;

import com.github.sundeepk.compactcalendarview.domain.Holiday;

import java.util.List;

/**
 * Created by ptblr-1109 on 9/2/18.
 */

public class Holidays {

    private final List<Holiday> holidays;
    private final long timeInMillis;

    Holidays(long timeInMillis, List<Holiday> events) {
        this.timeInMillis = timeInMillis;
        this.holidays = events;
    }

    long getTimeInMillis() {
        return timeInMillis;
    }

    List<Holiday> getHolidays() {
        return holidays;
    }

}
