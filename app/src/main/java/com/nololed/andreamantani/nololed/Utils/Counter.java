package com.nololed.andreamantani.nololed.Utils;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by andreamantani on 28/03/16.
 */

public class Counter extends Application {

    //add this variable declaration:
    private static int imageCounter = 0;
    public static int tecnologyIndexDisplayed = -1;
    private static int holidaysCounter = 0;
    public static int holidayIndexDisplayed = -1;

    private static Calendar todayCalendar;

    private static Counter singleton;

    public static Counter getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static int getImageCounter(){
        return  imageCounter++;
    }
    public static int getHolidaysCounter(){ return holidaysCounter++;}
    public static Date getTodayDate(){ return todayCalendar.getTime(); }
}