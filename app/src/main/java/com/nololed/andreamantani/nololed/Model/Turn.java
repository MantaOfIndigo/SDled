package com.nololed.andreamantani.nololed.Model;

import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.util.Calendar;

/**
 * Created by andreamantani on 08/04/16.
 */
public class Turn {

    Calendar begin;
    Calendar end;
    int hoursCount;

    public Turn(Calendar begin, Calendar end){
        this.begin = begin;
        this.end = end;
        this.hoursCount = countHours();
    }

    public Turn(int hoursCount){
        this.hoursCount = hoursCount;
        this.begin = CalendarUtils.newInitializedCalendar();
        this.end = begin;
    }
    private int countHours(){
        if(end.get(Calendar.HOUR_OF_DAY) == 0){
            if(begin.get(Calendar.HOUR_OF_DAY) != 0) {
                return Math.abs(24 - begin.get(Calendar.HOUR_OF_DAY));
            }else{
                return 0;
            }
        }else {
            return Math.abs(end.get(Calendar.HOUR_OF_DAY) - begin.get(Calendar.HOUR_OF_DAY));
        }
    }

    public Calendar getBegin(){
        return this.begin;
    }

    public Calendar getEnd(){
        return this.end;
    }

    public int getHoursCount(){
        return this.hoursCount;
    }

    public String toString(){
        return CalendarUtils.getTimeFormatted(begin.get(Calendar.HOUR_OF_DAY), begin.get(Calendar.MINUTE)) + " - " + CalendarUtils.getTimeFormatted(end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
    }

}
