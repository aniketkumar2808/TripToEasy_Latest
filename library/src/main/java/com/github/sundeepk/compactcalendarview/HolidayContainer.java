package com.github.sundeepk.compactcalendarview;

import android.util.Log;

import com.github.sundeepk.compactcalendarview.domain.Holiday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ptblr-1109 on 9/2/18.
 */

public class HolidayContainer  {

    private Map<String, List<Holidays>> eventsByMonthAndYearMap = new HashMap<>();
    private Calendar holidaysCalendar;
    public HolidayContainer(Calendar eventsCalendar) {
        this.holidaysCalendar = eventsCalendar;
    }


    void addEvent(Holiday event) {
        holidaysCalendar.setTimeInMillis(event.getTimeInMillis());
        String key = getKeyForCalendarEvent(holidaysCalendar);
        Log.i("holiday_key",key);
        List<Holidays> eventsForMonth = eventsByMonthAndYearMap.get(key);
        if (eventsForMonth == null) {
            eventsForMonth = new ArrayList<>();
        }
        Holidays eventsForTargetDay = getEventDayEvent(event.getTimeInMillis());
        if (eventsForTargetDay == null) {
            List<Holiday> events = new ArrayList<>();
            events.add(event);
            eventsForMonth.add(new Holidays(event.getTimeInMillis(), events));
        } else {
            eventsForTargetDay.getHolidays().add(event);
        }
        eventsByMonthAndYearMap.put(key, eventsForMonth);
    }

    void removeAllEvents() {
        eventsByMonthAndYearMap.clear();
    }

    private String getKeyForCalendarEvent(Calendar cal) {
        return cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.MONTH);
    }

    private Holidays getEventDayEvent(long eventTimeInMillis){
        holidaysCalendar.setTimeInMillis(eventTimeInMillis);
        int dayInMonth = holidaysCalendar.get(Calendar.DAY_OF_MONTH);
        String keyForCalendarEvent = getKeyForCalendarEvent(holidaysCalendar);
        List<Holidays> eventsForMonthsAndYear = eventsByMonthAndYearMap.get(keyForCalendarEvent);
        if (eventsForMonthsAndYear != null) {
            for (Holidays holidays : eventsForMonthsAndYear) {
                holidaysCalendar.setTimeInMillis(holidays.getTimeInMillis());
                int dayInMonthFromCache = holidaysCalendar.get(Calendar.DAY_OF_MONTH);
                if (dayInMonthFromCache == dayInMonth) {
                    return holidays;
                }
            }
        }
        return null;
    }
    List<Holidays> getHolidayForMonthAndYear(int month, int year){
        Log.i("holiday_key1",year + "_" + month);
        return eventsByMonthAndYearMap.get(year + "_" + month);
    }
}
