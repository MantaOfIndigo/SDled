package com.nololed.andreamantani.nololed.Utils;

import android.app.Application;

import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.Model.HolidayPeriod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andreamantani on 08/04/16.
 */
public class StandardWorkHours extends Application {
    private static DailyHours[] week = new DailyHours[7];
    private static List<Date> singleDays = new ArrayList<>();
    private static List<HolidayPeriod> periods = new ArrayList<>();

    public static void setStandards(){
        for(int i = 0; i <  7; i++){
            Calendar bAMstandard = Calendar.getInstance();
            bAMstandard.set(Calendar.HOUR_OF_DAY, 9);
            bAMstandard.set(Calendar.MINUTE, 0);

            Calendar eAMstandard = Calendar.getInstance();
            eAMstandard.set(Calendar.HOUR_OF_DAY, 13);
            eAMstandard.set(Calendar.MINUTE, 0);

            Calendar bPMstandard = Calendar.getInstance();
            bPMstandard.set(Calendar.HOUR_OF_DAY, 14);
            bPMstandard.set(Calendar.MINUTE, 0);

            Calendar ePMstandard = Calendar.getInstance();
            ePMstandard.set(Calendar.HOUR_OF_DAY, 19);
            ePMstandard.set(Calendar.MINUTE, 0);

            week[i] = new DailyHours();
            week[i].setAMTurn(bAMstandard, eAMstandard);
            week[i].setPMTurn(bPMstandard, ePMstandard);
        }
    }

    public static void setDailyHours(String dayName, DailyHours item){
        switch (dayName){
            case "Monday":
                week[0] = item;
                break;
            case "Tuesday":
                week[1] = item;
                break;
            case "Thursday":
                week[2] = item;
                break;
            case "Wednesday":
                week[3] = item;
                break;
            case "Friday":
                week[4] = item;
                break;
            case "Saturday":
                week[5] = item;
                break;
            case "Sunday":
                week[6] = item;
                break;
        }
    }

    public static DailyHours getDayStandard(String dayName){
        switch (dayName){
            case "Monday":
                return week[0];
            case "Tuesday":
                return week[1];
            case "Thursday":
                return week[2];
            case "Wednesday":
                return week[3];
            case "Friday":
                return week[4];
            case "Saturday":
                return week[5];
            case "Sunday":
                return week[6];
            default:
                return null;

        }
    }
    public static List<HolidayPeriod> getPeriods(){
        return periods;
    }

    public static boolean isEasterEnabled(){
        for(int i = 0; i < periods.size(); i++){
            if(periods.get(i).isInPeriod(CalendarDates.getCurrentYearEaster().getTime())){
                return false;
            }
        }
        return true;
    }

    public static boolean isChristmasEnabled(){
        for(int i = 0; i < periods.size(); i++){
            if(periods.get(i).isInPeriod(CalendarDates.getChristmas().getTime())){
                return false;
            }
        }
        return true;
    }

    public static boolean isFirstOfYearEnabled(){
        for(int i = 0; i < periods.size(); i++){
            if(periods.get(i).isInPeriod(CalendarDates.getFirstOfYear().getTime())){
                return false;
            }
        }
        return true;
    }


    public static void addSingleDay(Date newDay){
        singleDays.add(newDay);
    }
    public static List<Date> getDaysList(){
        return singleDays;
    }
    public static void setPeriods(List<HolidayPeriod> periodsList){
        periods = periodsList;
    }
}
