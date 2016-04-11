package com.nololed.andreamantani.nololed.Utils;

import android.util.Log;

import com.nololed.andreamantani.nololed.Model.HolidayPeriod;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 31/03/16.
 */
public class CalendarUtils {

    private static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static int daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        int daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    private static String getDayOfMonth(Date value){
        return (String) android.text.format.DateFormat.format("dd", value); //20
    }
    private static String getMonth(Date value){
        return (String) android.text.format.DateFormat.format("MM", value);
    }

    public static String getDateFormatted(Date value){
        return getDayOfMonth(value) + "/" + getMonth(value);
    }

    public static Calendar newInitializedCalendar(){
        Calendar newitem = Calendar.getInstance();
        newitem.set(Calendar.HOUR, newitem.getMinimum(Calendar.HOUR));
        newitem.set(Calendar.MINUTE, newitem.getMinimum(Calendar.MINUTE));
        newitem.set(Calendar.SECOND, newitem.getMinimum(Calendar.SECOND));
        newitem.set(Calendar.MILLISECOND, newitem.getMinimum(Calendar.MILLISECOND));
        return newitem;
    }

    private static boolean inBetweenPeriod(HolidayPeriod first, HolidayPeriod last){
        //Primo periodo
        Date min = first.getBegin();
        Date max = first.getEnd();

        boolean check1 = last.getBegin().after(min) && last.getBegin().before(max);
        boolean check2 = last.getEnd().after(min) && last.getEnd().before(max);

        Log.v("clockchecker", "check: " + check1 + check2);
        return (check1 || check2);
    }

    public static boolean isInBetweenPeriods(HolidayPeriod first, HolidayPeriod last){
        boolean check1 = inBetweenPeriod(first,last);
        boolean check2 = inBetweenPeriod(last,first);


        Log.v("clockchecker", "check: " + check1 + check2);

        return check1 || check2;
    }

    public static boolean inBetweenDate(Calendar first, Calendar last){

        Date min = first.getTime();
        Date max = last.getTime();

        boolean check = max.before(min);
        boolean check2 = max.equals(min);

        return !(check || check2);
    }

    public static Calendar getDateFromString(String value){

        if(value.isEmpty()){
            return null;
        }

        Calendar returner = Calendar.getInstance();
        String[] splitter = value.split(Pattern.quote("/"));

        if (splitter.length != 2){
            return null;
        }

        int day = Integer.parseInt(splitter[0]);
        int month = Integer.parseInt(splitter[1]);

        returner.set(Calendar.DAY_OF_MONTH, day);
        returner.set(Calendar.MONTH, month-1);

        return returner;

    }

    public static String getTimeFormatted(int hour, int minute){
        Calendar returner = Calendar.getInstance();

        returner.set(Calendar.HOUR_OF_DAY, hour);
        returner.set(Calendar.MINUTE, minute);

        DateFormat dtFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
        return dtFormatter.format(returner.getTime());
    }

    public static boolean checkHours(Calendar first, Calendar last){
        if (first.get(Calendar.HOUR_OF_DAY) <= last.get(Calendar.HOUR_OF_DAY)){

            if(first.get(Calendar.HOUR_OF_DAY) == last.get(Calendar.HOUR_OF_DAY)){
                if(first.get(Calendar.MINUTE) < last.get(Calendar.MINUTE)){
                    return true;
                }
            }else{
                return true;
            }
        }

        return false;
    }
}
