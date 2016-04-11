package com.nololed.andreamantani.nololed.Model;

import android.util.Log;

import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 31/03/16.
 */
public class HolidayPeriod implements Serializable{

    Date begin;
    Date end;
    int daysNumber;
    int index;

    //Aggiungi metodi per verificare la pasqua di anno in anno

    public HolidayPeriod(Date begin, Date end, int index){
        this.begin = begin;
        this.end = end;
        this.daysNumber = countDays();

        this.index = index;

    }
    public HolidayPeriod(Calendar begin, Calendar end){
        this.begin = begin.getTime();
        this.end = end.getTime();
        this.daysNumber = countDays();

        this.index = -1;
    }

    private int countDays(){
        return CalendarUtils.daysBetween(this.begin, this.end);
    }

    public String toString(){
        String returner = "[ ";

        returner += "index : " + this.index + " , " + "begin : " + CalendarUtils.getDateFormatted(this.begin) + " , " + "end : " + CalendarUtils.getDateFormatted(this.end) + " , " + "dayNumber : " + this.daysNumber;

        return returner + " ]";
    }

    public HolidayPeriod getDeserializeHolidayPeriod(String value){
        String replacer1 = value.replace("[", "");
        replacer1 = replacer1.replace("]", "");
        String[] splitter1 = replacer1.split(Pattern.quote(","));

        for (int i = 0; i< splitter1.length;i++){

                Log.v("timecheck","result : " + splitter1[i].split(Pattern.quote(":"))[1]);

        }

        return null;//new HolidayPeriod();
    }

    private int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    private int getDays(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public boolean equals(HolidayPeriod obj) {
        if(getMonth(this.getBegin()) == getMonth(obj.getBegin())) {
            if (getDays(this.getBegin()) == getDays(obj.getBegin())) {
                return true;
            }
        }

        Log.v("clockchecker", "conr: " + this.getBeginString() + " = " + obj.getBeginString());
        Log.v("clockchecker", "conr: " + this.getEndString() + " = " + obj.getEndString());

        if(getMonth(this.getEnd()) == getMonth(obj.getEnd())) {
            if (getDays(this.getEnd()) == getDays(obj.getEnd())) {
                return true;
            }
        }

        if(CalendarUtils.isInBetweenPeriods(this, obj)){
            return true;
        }

        return false;
    }


    public String getBeginString(){
        return CalendarUtils.getDateFormatted(this.begin);
    }
    public String getEndString(){
        return CalendarUtils.getDateFormatted(this.end);
    }
    public Date getBegin(){
        return this.begin;
    }
    public Date getEnd(){
        return this.end;
    }
    public boolean isInPeriod(Date date){
        boolean check1 = this.begin.before(date) && this.end.after(date);
        Calendar dateIsEqualCheck = CalendarUtils.newInitializedCalendar();
        Calendar beginCheck = CalendarUtils.newInitializedCalendar();
        Calendar endCheck = CalendarUtils.newInitializedCalendar();

        dateIsEqualCheck.setTime(date);
        beginCheck.setTime(this.begin);
        endCheck.setTime(this.end);

        if(dateIsEqualCheck.get(Calendar.DAY_OF_YEAR) != beginCheck.get(Calendar.DAY_OF_YEAR) && dateIsEqualCheck.get(Calendar.DAY_OF_YEAR) != endCheck.get(Calendar.DAY_OF_YEAR)){
            if(!check1){
                return false;
            }
        }

        return true;

    }



}
