package com.nololed.andreamantani.nololed.Utils;

import android.app.Application;
import android.util.Log;

import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Model.Tecnology;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andreamantani on 28/03/16.
 */

public class Utilities extends Application {

    //add this variable declaration:

    private static SystemTec system;

    private static int imageCounter = 0;
    public static int tecnologyIndexDisplayed = -1;
    private static int holidaysCounter = 0;
    public static int holidayIndexDisplayed = -1;

    public static List<String> locations = new ArrayList<>();

    public static void addLocation(String value){
        if(locations.size() == 0){
            locations.add(value);
            return;
        }

        if(!locations.contains(value)){
            locations.add(value);
        }
    }

    public static void setSystem(SystemTec value){
        system = value;
    }
    public static SystemTec getSystem(){
        return system;
    }

    public static boolean itemInNewPlace = false;
    private static Tecnology oldItem;

    private static Calendar todayCalendar;

    private static Utilities singleton;

    public static Utilities getInstance() {
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
    public static void setOldItem(Tecnology value){
        if(oldItem == null){
            oldItem = value;
        }
    }
    public static boolean isNotOldItem(Tecnology value){
        if(!(value.getLocation().equals(oldItem.getLocation())) && !(value.getLocation().replace(" ", "").equals(oldItem.getLocation().replace(" ", "")))){
            if(oldItem.getInfos().getModel().equals(value.getInfos().getModel())){
                return true;
            }
        }

        return false;
    }
    public static void resetOldItem(){
        oldItem = null;
    }
}