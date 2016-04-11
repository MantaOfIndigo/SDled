package com.nololed.andreamantani.nololed.Model;

import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.util.Calendar;

/**
 * Created by andreamantani on 08/04/16.
 */
public class Turn {

    Calendar begin;
    Calendar end;

    public Turn(Calendar begin, Calendar end){
        this.begin = begin;
        this.end = end;
    }

    public Calendar getBegin(){
        return this.begin;
    }

    public Calendar getEnd(){
        return this.end;
    }

    public String toString(){
        return CalendarUtils.getTimeFormatted(begin.get(Calendar.HOUR_OF_DAY), begin.get(Calendar.MINUTE)) + " - " + CalendarUtils.getTimeFormatted(end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
    }

}
