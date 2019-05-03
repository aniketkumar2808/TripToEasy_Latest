package com.github.sundeepk.compactcalendarview.domain;

/**
 * Created by ptblr-1109 on 9/2/18.
 */

public class Holiday  {

    private int color;
    private long timeInMillis;


    public Holiday(int color, long timeInMillis){
        this.color=color;
        this.timeInMillis=timeInMillis;
    }



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
