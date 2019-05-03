package com.github.sundeepk.compactcalendarview.domain;

import android.support.annotation.Nullable;

public class Event {

    private int color;
    private long timeInMillis;
    private Object data;
    private String eventName;


    public Event(int color, long timeInMillis,String eventName) {
        this.color = color;
        this.timeInMillis = timeInMillis;
        this.eventName=eventName;
    }

    public Event(int color, long timeInMillis, Object data,String eventName) {
        this.color = color;
        this.timeInMillis = timeInMillis;
        this.data = data;
        this.eventName=eventName;

    }

    public String getEventName(){return eventName;
}

    public int getColor() {
        return color;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (color != event.color) return false;
        if (timeInMillis != event.timeInMillis) return false;
        if (data != null ? !data.equals(event.data) : event.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color;
        result = 31 * result + (int) (timeInMillis ^ (timeInMillis >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "color=" + color +
                ", timeInMillis=" + timeInMillis +
                ", data=" + data +
                ", eventName=" + eventName +
                '}';
    }
}
