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
    private static final DailyHours[] standardWeek = new DailyHours[7];
    private static List<Date> singleDays = new ArrayList<>();
    private static List<HolidayPeriod> periods = new ArrayList<>();


    private static DailyHours[] customWeek = new DailyHours[7];
    private static int daysToRemove = 0;


    private static int daysInYear;

    public static void set24hStandard(){
        setStandards(1);
    }

    public static void setSolarStandard(){

    }

    public static void setStandards(DailyHours value){
        for(int i = 0; i <  7; i++){
            Calendar bAMstandard = Calendar.getInstance();
            bAMstandard.set(Calendar.HOUR_OF_DAY, value.getBeginAMHour());
            bAMstandard.set(Calendar.MINUTE, value.getBeginAMMinute());

            Calendar eAMstandard = Calendar.getInstance();
            eAMstandard.set(Calendar.HOUR_OF_DAY, value.geteEndAMHour());
            eAMstandard.set(Calendar.MINUTE, value.getEndAMMinute());

            Calendar bPMstandard = Calendar.getInstance();
            bPMstandard.set(Calendar.HOUR_OF_DAY, value.getBeginPMHour());
            bPMstandard.set(Calendar.MINUTE, value.getBeginPMMinute());

            Calendar ePMstandard = Calendar.getInstance();
            ePMstandard.set(Calendar.HOUR_OF_DAY, value.getEndPMHour());
            ePMstandard.set(Calendar.MINUTE, value.getEndPMMinute());

            standardWeek[i] = new DailyHours();
            standardWeek[i].setAMTurn(bAMstandard, eAMstandard);
            standardWeek[i].setPMTurn(bPMstandard, ePMstandard);
            standardWeek[i].setEnable(true);
        }
    }

    public static int calculateAccurateHoursInYear(int[] weeklyHours){
        int returner = 0;

        Calendar currentYear = CalendarUtils.newInitializedCalendar();
        currentYear.set(Calendar.DAY_OF_YEAR, 0);

        List<Calendar> holidays = new ArrayList<>();

        if(periods.size() != 0) {
            // devo togliere tutti i giorni disabilitati considerando le ferie
            for (int i = currentYear.getMinimum(Calendar.DAY_OF_YEAR); i < currentYear.getMaximum(Calendar.DAY_OF_YEAR); i++) {
                if (checkPeriodsIndexes(i)) {
                    currentYear.set(Calendar.DAY_OF_YEAR, i);
                    int dayOfWeekIndex = currentYear.get(Calendar.DAY_OF_WEEK);
                    returner += weeklyHours[dayOfWeekIndex - 1];
                }
            }
        }else{
            for(int i = currentYear.getMinimum(Calendar.DAY_OF_YEAR); i < currentYear.getMaximum(Calendar.DAY_OF_YEAR); i++){
                currentYear.set(Calendar.DAY_OF_YEAR, i);
                int dayOfWeekIndex = currentYear.get(Calendar.DAY_OF_WEEK);
                returner += weeklyHours[dayOfWeekIndex - 1];
            }
        }

        for(int i = 0; i < singleDays.size(); i++){

            Calendar singleDayHolidayIterator = CalendarUtils.newInitializedCalendar();
            singleDayHolidayIterator.setTime(singleDays.get(i));

            int singleDayOfWeekIndex = singleDayHolidayIterator.get(Calendar.DAY_OF_WEEK);

            returner -= weeklyHours[singleDayOfWeekIndex - 1];
        }

        return returner;
    }

    private static boolean checkPeriodsIndexes(int index){

        for (int i = 0; i < periods.size(); i++){
            if(index >= periods.get(0).getBeginCalendar().get(Calendar.DAY_OF_YEAR) && index <= periods.get(0).getEndCalendar().get(Calendar.DAY_OF_YEAR)){
                return false;
            }
        }

        return true;
    }

    public static int daysCount(){
        Calendar currYear = Calendar.getInstance();

        daysInYear = currYear.getMaximum(Calendar.DAY_OF_YEAR);

        for(int i = 0; i < periods.size();i++){
            daysInYear -= periods.get(i).getDaysNumber();
        }

        daysInYear -= singleDays.size();

        //giorni lavorativi = giorni lavorativi - giornisettimanali da rimuovere per numero di settimane(numero massimo di settimane in un anno - circa settimane tolte dalle ferie)
        daysInYear -= daysToRemove * (currYear.getMaximum(Calendar.WEEK_OF_YEAR) - (daysInYear % 7));

        return daysInYear;
    }

    public static void setCustomStandards(){
        customWeek = null;
        customWeek = new DailyHours[7];

        customWeek = standardWeek.clone();

        boolean[] daysEnabled = Constants.getDayStandard();
        for(int i = 0; i <  7; i++){
            customWeek[i].setEnable(daysEnabled[i]);
        }
    }


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

            standardWeek[i] = new DailyHours();
            standardWeek[i].setAMTurn(bAMstandard, eAMstandard);
            standardWeek[i].setPMTurn(bPMstandard, ePMstandard);
            standardWeek[i].setEnable(true);
        }
    }

    //value:
    //0 - manual
    //1 - 24
    //2 - solar
    private static void setStandards(int value){
        if(value == 1) {
            for(int i = 0; i <  7; i++){
                standardWeek[i] = new DailyHours();
                standardWeek[i].set24hTurn();
                standardWeek[i].setEnable(true);
            }
        }else {

            for (int i = 0; i < 7; i++) {
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

                standardWeek[i] = new DailyHours();
                standardWeek[i].setAMTurn(bAMstandard, eAMstandard);
                standardWeek[i].setPMTurn(bPMstandard, ePMstandard);
                standardWeek[i].setEnable(true);
            }
        }
    }

    public static DailyHours getCustomDayStandard(String dayName){
        switch (dayName){
            case "Lunedì":
                return customWeek[0];
            case "Martedì":
                return customWeek[1];
            case "Mercoledì":
                return customWeek[2];
            case "Giovedì":
                return customWeek[3];
            case "Venerdì":
                return customWeek[4];
            case "Sabato":
                return customWeek[5];
            case "Domenica":
                return customWeek[6];
            default:
                return null;
        }
    }

    private static int[] calculateCustomHours(DailyHours[] array){
        int[] count = new int[7];
        int countToRemove = 0;
        daysToRemove = 0;

        for(int i = 0 ; i < array.length; i++){
            count[i] = array[i].getHoursNumber();
            if(array[i].getHoursNumber() == 0){
                daysToRemove++;
            }
        }

        return count;
    }

    public static int[] getHoursNumber(Boolean custom){
        if(SolarYearHours.isSolarYear()){
            int[] returner = new int[1];
            returner[0] = SolarYearHours.getHourInYear();
            return returner;
        }else if(custom){
            return calculateCustomHours(customWeek);
        }else {
            return calculateCustomHours(standardWeek);
        }

    }

    public static void setCustomDailyHours(String dayName, DailyHours item){
        int i = standardWeek.length;
        switch (dayName){
            case "Monday":
                customWeek[0] = item;
                break;
            case "Tuesday":
                customWeek[1] = item;
                break;
            case "Thursday":
                customWeek[2] = item;
                break;
            case "Wednesday":
                customWeek[3] = item;
                break;
            case "Friday":
                customWeek[4] = item;
                break;
            case "Saturday":
                customWeek[5] = item;
                break;
            case "Sunday":
                customWeek[6] = item;
                break;
        }
    }

    public static void setDailyHours(String dayName, DailyHours item){
        switch (dayName){
            case "Monday":
                standardWeek[0] = item;
                break;
            case "Tuesday":
                standardWeek[1] = item;
                break;
            case "Thursday":
                standardWeek[2] = item;
                break;
            case "Wednesday":
                standardWeek[3] = item;
                break;
            case "Friday":
                standardWeek[4] = item;
                break;
            case "Saturday":
                standardWeek[5] = item;
                break;
            case "Sunday":
                standardWeek[6] = item;
                break;
        }
    }

    public static void setEnableStandardDay(String dayName, boolean value){

        boolean returner;

        if(value){
            returner = true;
        }else {
            returner = false;
        }

        switch (dayName){
            case "Monday":
                standardWeek[0].setEnable(returner);
                break;
            case "Tuesday":
                standardWeek[1].setEnable(returner);
                break;
            case "Thursday":
                standardWeek[2].setEnable(returner);
                break;
            case "Wednesday":
                standardWeek[3].setEnable(returner);
                break;
            case "Friday":
                standardWeek[4].setEnable(returner);
                break;
            case "Saturday":
                standardWeek[5].setEnable(returner);
                break;
            case "Sunday":
                standardWeek[6].setEnable(returner);
                break;
        }
    }

    public static void setEnableCustomDay(String dayName, boolean value){

        boolean returner;

        if(value){
            returner = true;
        }else {
            returner = false;
        }

        switch (dayName){
            case "Monday":
                customWeek[0].setEnable(returner);
                break;
            case "Tuesday":
                customWeek[1].setEnable(returner);
                break;
            case "Thursday":
                customWeek[2].setEnable(returner);
                break;
            case "Wednesday":
                customWeek[3].setEnable(returner);
                break;
            case "Friday":
                customWeek[4].setEnable(returner);
                break;
            case "Saturday":
                customWeek[5].setEnable(returner);
                break;
            case "Sunday":
                customWeek[6].setEnable(returner);
                break;
        }
    }

    public static DailyHours getDayStandard(String dayName){
        switch (dayName){
            case "Monday":
                return standardWeek[0];
            case "Tuesday":
                return standardWeek[1];
            case "Thursday":
                return standardWeek[2];
            case "Wednesday":
                return standardWeek[3];
            case "Friday":
                return standardWeek[4];
            case "Saturday":
                return standardWeek[5];
            case "Sunday":
                return standardWeek[6];
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

    //cod: 1: è festivo, 2: è lavorativo 3:già compreso tra feste
    public static void addStaticHolyday(int christmas, int easter, int firstOfYear){
        if (christmas == 1){
            singleDays.add(CalendarDates.getChristmas().getTime());
        }
        if(easter == 1){
            singleDays.add(CalendarDates.getCurrentYearEaster().getTime());
        }

        if(firstOfYear == 1){
            singleDays.add(CalendarDates.getFirstOfYear().getTime());
        }
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
