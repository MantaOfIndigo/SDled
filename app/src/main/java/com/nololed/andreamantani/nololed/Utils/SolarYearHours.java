package com.nololed.andreamantani.nololed.Utils;

import android.util.Log;

import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.Model.HolidayPeriod;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by andreamantani on 11/05/16.
 */
public class SolarYearHours {

    private static boolean isSolarCalendarSelected = false;

    private static List<Integer> january;
    private static List<Integer> february;
    private static List<Integer> march;
    private static List<Integer> april;
    private static List<Integer> may;
    private static List<Integer> june;
    private static List<Integer> july;
    private static List<Integer> august;
    private static List<Integer> september;
    private static List<Integer> october;
    private static List<Integer> november;
    private static List<Integer> december;

    private static boolean[] week = new boolean[7];

    private static int[] hoursDawn = {7, 7, 7, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 6, 6, 7, 7, 7};
    private static int[] minutesDawn = {47, 32, 31, 52, 51, 57, 56, 7, 6, 35, 35, 34, 35, 0, 1, 35, 36, 9, 10, 47, 48, 25, 26, 47};
    private static int[] hourDusk = {16, 17, 17, 18, 18, 19, 19, 20, 20, 20, 20, 20, 20, 20, 20, 19, 19, 18, 18, 17, 17, 16, 16, 16};
    private static int[] minutesDusk = {48, 21, 22, 0, 1, 38, 39, 14, 15, 47, 47, 59, 59, 38, 37, 51, 49, 57, 55, 6, 4, 38, 37, 45};

    final static int[] monthsSizes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static int dayInYear = 0;
    private static int hourInYear;


    public static boolean isSolarYear(){

        return isSolarCalendarSelected;
    }
    public static void selectSolarCalendar(){
        isSolarCalendarSelected = true;
    }
    public static void deselectSolarCalendar() { isSolarCalendarSelected = false; }


    public static void enableDayOfWeek(String dayName, boolean value){
        switch (dayName){
            case "Monday":
                    week[0] = value;
                break;// METTI NOMI INGLESI
            case "Tuesday":
                    week[1] = value;
                break;
            case "Thursday":
                    week[2] = value;
                break;
            case "Wednesday":
                    week[3] = value;
                break;
            case "Friday":
                    week[4] = value;
                break;
            case "Saturday":
                    week[5] = value;
                break;
            case "Sunday":
                    week[6] = value;
                break;
            default:
                break;
        }
    }

    public static void toggleDayOfWeek(String dayName){
        switch (dayName){
            case "Monday":
                if(week[0]){
                    week[0] = false;
                }else{
                    week[0] = true;
                }
                break;// METTI NOMI INGLESI
            case "Tuesday":
                if(week[1]){
                    week[1] = false;
                }else{
                    week[1] = true;
                }
                break;
            case "Thursday":
                if(week[2]){
                    week[2] = false;
                }else{
                    week[2] = true;
                }
                break;
            case "Wednesday":
                if(week[3]){
                    week[3] = false;
                }else{
                    week[3] = true;
                }
                break;
            case "Friday":
                if(week[4]){
                    week[4] = false;
                }else{
                    week[4] = true;
                }
                break;
            case "Saturday":
                if(week[5]){
                    week[5] = false;
                }else{
                    week[5] = true;
                }
                break;
            case "Sunday":
                if(week[6]){
                    week[6] = false;
                }else{
                    week[6] = true;
                }
                break;
            default:
                break;
        }
    }


    public static int getDayInYear(){
        if(dayInYear == 0){
            calculateSolarHours();
        }

        return dayInYear;
    }

    public static int calculateSolarHours(){
        int counterHours = 0;
        dayInYear = 0;
        hourInYear = 0;
        List<Integer> month;
        for(int j = 0; j < 12; j++) {
            month = getListMonthFromId(j);
            for (int i = 0; i < month.size(); i++) {
                if (month.get(i) != 0) {
                    counterHours += month.get(i);
                    dayInYear++;
                }
            }
        }


        hourInYear = (int) TimeUnit.MINUTES.toHours(counterHours);
        int hourInMonth = (hourInYear - (hourInYear % 12)) / 12;
        int hourInWeek = (hourInMonth - (hourInMonth % 7)) / 7;
        return hourInWeek;

    }

    public static int getHourInYear(){
        return hourInYear;
    }

    public static void setSolarYear(DailyHours value){


        Calendar controlBisestile = CalendarUtils.newInitializedCalendar();
        int currentYear = controlBisestile.get(Calendar.YEAR);
        if(currentYear % 4 == 0){
            monthsSizes[1] = 29;
        }

        for(int months = 0; months < 24; months+= 2){
            Calendar dawnFirst = CalendarUtils.newInitializedCalendar();

            dawnFirst.set(Calendar.HOUR_OF_DAY, hoursDawn[months]);
            dawnFirst.set(Calendar.MINUTE, minutesDawn[months]);

            Calendar dawnLast = CalendarUtils.newInitializedCalendar();

            dawnLast.set(Calendar.HOUR_OF_DAY, hoursDawn[months + 1]);
            dawnLast.set(Calendar.MINUTE, minutesDawn[months + 1]);

            Calendar duskFirst = CalendarUtils.newInitializedCalendar();

            duskFirst.set(Calendar.HOUR_OF_DAY, hourDusk[months]);
            duskFirst.set(Calendar.MINUTE, minutesDusk[months]);

            Calendar duskLast = CalendarUtils.newInitializedCalendar();

            duskLast.set(Calendar.HOUR_OF_DAY, hourDusk[months + 1]);
            duskLast.set(Calendar.MINUTE, minutesDusk[months + 1]);


            setSingleMonth((months / 2), dawnFirst, duskFirst, dawnLast, duskLast, value);

        }

        Log.v("MyApp", "done");



    }

    private static void setSingleMonth(int calendarMonth, Calendar dawnFirst, Calendar duskFirst, Calendar dawnLast, Calendar duskLast, DailyHours workhours){
        List<Integer> month = new ArrayList<>();



        if(month == null){
            return;
        }

        long dawnFirstHalf = 0;
        dawnFirst.add(Calendar.HOUR_OF_DAY, 1);

        if(workhours.getBeginAMCalendar().before(dawnFirst)){
            dawnFirstHalf = CalendarUtils.secondsBetween(workhours.getBeginAMCalendar(), dawnFirst);
        }else{
            dawnFirstHalf = 0;
        }

        long dawnSecondHalf = 0;

        dawnLast.add(Calendar.HOUR_OF_DAY, 1);

        if(workhours.getBeginAMCalendar().before(dawnLast)){
            dawnSecondHalf = CalendarUtils.secondsBetween(workhours.getBeginAMCalendar(), dawnLast);
        }else{
            dawnSecondHalf = 0;
        }

        long duskFirstHalf = 0;
        duskFirst.add(Calendar.HOUR_OF_DAY, -1);

        if(workhours.getEndAMCalendar().after(duskFirst)){
            duskFirstHalf = CalendarUtils.secondsBetween(duskFirst, workhours.getEndAMCalendar());
        }else{
            duskFirstHalf = 0;
        }

        long duskSecondHalf = 0;
        duskLast.add(Calendar.HOUR_OF_DAY, -1);

        if(workhours.getEndAMCalendar().after(duskLast)){
            duskSecondHalf = CalendarUtils.secondsBetween(duskLast, workhours.getEndAMCalendar());
        }else{
            duskSecondHalf = 0;
        }

        long minutesFirstHalf = TimeUnit.SECONDS.toMinutes(dawnFirstHalf + duskFirstHalf);
        long minutesSecondHalf = TimeUnit.SECONDS.toMinutes(dawnSecondHalf + duskSecondHalf);

        int MONTH_LENGHT = monthsSizes[calendarMonth];

        for(int i = 0; i < MONTH_LENGHT; i++){
            if(i < MONTH_LENGHT / 2) {
                month.add((int) minutesFirstHalf);
            }else{
                month.add((int) minutesSecondHalf);
            }
        }

        switch (calendarMonth){
            case 0:
                january = new ArrayList<>();
                january = month;
                break;
            case 1:
                february = new ArrayList<>();
                february = month;
                break;
            case 2:
                march = new ArrayList<>();
                march = month;
                break;
            case 3:
                april = new ArrayList<>();
                april = month;
                break;
            case 4:
                may = new ArrayList<>();
                may = month;
                break;
            case 5:
                june = new ArrayList<>();
                june = month;
                break;
            case 6:
                july = new ArrayList<>();
                july = month;
                break;
            case 7:
                august = new ArrayList<>();
                august = month;
                break;
            case 8:
                september = new ArrayList<>();
                september = month;
                break;
            case 9:
                october = new ArrayList<>();
                october = month;
                break;
            case 10:
                november = new ArrayList<>();
                november = month;
                break;
            case 11:
                december = new ArrayList<>();
                december = month;
                break;
            default:
                month = null;
                break;
        }
/*
        // minuti di luce nelle due date
        long differenceFirth = CalendarUtils.secondsBetween(dawnFirst, duskFirst);
        long differenceLast = CalendarUtils.secondsBetween(dawnLast, duskLast);

        long nightSecondsFirst = CalendarUtils.minutesOfNight(differenceFirth);
        long nightSecondsLast = CalendarUtils.minutesOfNight(differenceLast);

        int MONTH_LENGHT = monthsSizes[calendarMonth];

        int deltaJanuary = (int) ((double) nightSecondsLast - (double) nightSecondsFirst) / MONTH_LENGHT;

        month = new ArrayList<>();
        month.add((int) TimeUnit.SECONDS.toMinutes(nightSecondsFirst));

        long seconds = nightSecondsFirst;

        for(int januaryCount = 0; januaryCount < MONTH_LENGHT - 2; januaryCount++){
            seconds += deltaJanuary;
            month.add((int) TimeUnit.SECONDS.toMinutes(seconds));
        }

        month.add((int) TimeUnit.SECONDS.toMinutes(nightSecondsLast));

        switch (calendarMonth){
            case 0:
                january = new ArrayList<>();
                january = month;
                break;
            case 1:
                february = new ArrayList<>();
                february = month;
                break;
            case 2:
                march = new ArrayList<>();
                march = month;
                break;
            case 3:
                april = new ArrayList<>();
                april = month;
                break;
            case 4:
                may = new ArrayList<>();
                may = month;
                break;
            case 5:
                june = new ArrayList<>();
                june = month;
                break;
            case 6:
                july = new ArrayList<>();
                july = month;
                break;
            case 7:
                august = new ArrayList<>();
                august = month;
                break;
            case 8:
                september = new ArrayList<>();
                september = month;
                break;
            case 9:
                october = new ArrayList<>();
                october = month;
                break;
            case 10:
                november = new ArrayList<>();
                november = month;
                break;
            case 11:
                december = new ArrayList<>();
                december = month;
                break;
            default:
                month = null;
                break;
        }
        */
    }

    public static void setSolarYear(){
        // dividi le differenze per ogni mese
        //Gennaio ha 31 giorni
        // alba 1 Gennaio : 7:47
        // alba 31 Gennaio : 7:32
        // diff:

        //Gennaio

        Calendar controlBisestile = CalendarUtils.newInitializedCalendar();
        int currentYear = controlBisestile.get(Calendar.YEAR);
        if(currentYear % 4 == 0){
            monthsSizes[1] = 29;
        }

        for(int months = 0; months < 24; months+= 2){
            Calendar dawnFirst = CalendarUtils.newInitializedCalendar();

            dawnFirst.set(Calendar.HOUR_OF_DAY, hoursDawn[months]);
            dawnFirst.set(Calendar.MINUTE, minutesDawn[months]);

            Calendar dawnLast = CalendarUtils.newInitializedCalendar();

            dawnLast.set(Calendar.HOUR_OF_DAY, hoursDawn[months + 1]);
            dawnLast.set(Calendar.MINUTE, minutesDawn[months + 1]);

            Calendar duskFirst = CalendarUtils.newInitializedCalendar();

            duskFirst.set(Calendar.HOUR_OF_DAY, hourDusk[months]);
            duskFirst.set(Calendar.MINUTE, minutesDusk[months]);

            Calendar duskLast = CalendarUtils.newInitializedCalendar();

            duskLast.set(Calendar.HOUR_OF_DAY, hourDusk[months + 1]);
            duskLast.set(Calendar.MINUTE, minutesDusk[months + 1]);

            setSingleMonth((months / 2), dawnFirst, duskFirst, dawnLast, duskLast);

        }

        Log.v("MyApp", "done");

    }

    public static void removeDaysOfWeek(){
        CalendarDates.settingUpHolidays();
        Calendar currentYear = CalendarUtils.newInitializedCalendar();

        int daysThisYear = 0;

        for(int i = 0; i < 12; i++){
            daysThisYear += getListMonthFromId(i).size();
        }


        Calendar cursor = CalendarDates.getFirstOfYear();
        for(int i = 0; i < daysThisYear; i++){
            Log.v("MyApp", "gior sett " + cursor.get(Calendar.DAY_OF_WEEK));

            if(verifyDay(cursor.get(Calendar.DAY_OF_WEEK))){
                removeSingleDayHoliday(cursor);
            }

            cursor.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private static boolean verifyDay(int id){
        int counter = 0;

        Log.v("MyApp", "did : "+ id);
        if(id == 1){
            counter = 6;
        }else{
            counter = id - 2;
        }


        Log.v("MyApp", "count : "+ counter);
        if(week[counter]){
            return false;
        }
        return true;
    }

    private static void setSingleMonth(int calendarMonth , Calendar dawnFirst, Calendar duskFirst, Calendar dawnLast, Calendar duskLast){

        List<Integer> month = new ArrayList<>();



        if(month == null){
            return;
        }

        // minuti di luce nelle due date
        long differenceFirth = CalendarUtils.secondsBetween(dawnFirst, duskFirst);
        long differenceLast = CalendarUtils.secondsBetween(dawnLast, duskLast);

        long nightSecondsFirst = CalendarUtils.minutesOfNight(differenceFirth);
        long nightSecondsLast = CalendarUtils.minutesOfNight(differenceLast);

        int MONTH_LENGHT = monthsSizes[calendarMonth];

        int deltaJanuary = (int) ((double) nightSecondsLast - (double) nightSecondsFirst) / MONTH_LENGHT;

        month = new ArrayList<>();
        month.add((int) TimeUnit.SECONDS.toMinutes(nightSecondsFirst));

        long seconds = nightSecondsFirst;

        for(int januaryCount = 0; januaryCount < MONTH_LENGHT - 2; januaryCount++){
            seconds += deltaJanuary;
            month.add((int) TimeUnit.SECONDS.toMinutes(seconds));
        }

        month.add((int) TimeUnit.SECONDS.toMinutes(nightSecondsLast));

        switch (calendarMonth){
            case 0:
                january = new ArrayList<>();
                january = month;
                break;
            case 1:
                february = new ArrayList<>();
                february = month;
                break;
            case 2:
                march = new ArrayList<>();
                march = month;
                break;
            case 3:
                april = new ArrayList<>();
                april = month;
                break;
            case 4:
                may = new ArrayList<>();
                may = month;
                break;
            case 5:
                june = new ArrayList<>();
                june = month;
                break;
            case 6:
                july = new ArrayList<>();
                july = month;
                break;
            case 7:
                august = new ArrayList<>();
                august = month;
                break;
            case 8:
                september = new ArrayList<>();
                september = month;
                break;
            case 9:
                october = new ArrayList<>();
                october = month;
                break;
            case 10:
                november = new ArrayList<>();
                november = month;
                break;
            case 11:
                december = new ArrayList<>();
                december = month;
                break;
            default:
                month = null;
                break;
        }
    }

    public static void setSolarCalendarWithoutHolidays(){
        List<HolidayPeriod> periods = StandardWorkHours.getPeriods();
        List<Date> days = StandardWorkHours.getDaysList();

        for(int i = 0; i < days.size(); i++){
            Calendar date = CalendarUtils.getDatePart(days.get(i));
            removeSingleDayHoliday(date);
        }

        for(int i = 0; i < periods.size(); i++){
            Calendar begin = CalendarUtils.getDatePart(periods.get(i).getBegin());
            Calendar end = CalendarUtils.getDatePart(periods.get(i).getEnd());

            removePeriodHoliday(begin, end);

        }
    }

    private static  void removePeriodHoliday(Calendar begin, Calendar end){
        int beginMonth = begin.get(Calendar.MONTH);
        int endMonth = end.get(Calendar.MONTH);

        if(beginMonth == endMonth){
            int beginDay = begin.get(Calendar.DAY_OF_MONTH);
            int endDay = end.get(Calendar.DAY_OF_MONTH);

            List<Integer> month = getListMonthFromId(beginMonth);
            for(int i = beginDay - 1; i < endDay; i++){
                month.set(i, 0);
            }
        }

        if(beginMonth < endMonth){
            int beginDay = begin.get(Calendar.DAY_OF_MONTH);

            List<Integer> month = getListMonthFromId(beginMonth);
            for(int i = beginDay - 1; i < month.size(); i++){
                month.set(i, 0);
            }

            int endDay = end.get(Calendar.DAY_OF_MONTH);

            month = getListMonthFromId(endMonth);
            for(int i = 0; i < endDay; i++){
                month.set(i, 0);
            }

            for(int i = beginMonth + 1; i < endMonth; i++){
                month = getListMonthFromId(i);
                for(int j = 0; j < month.size(); j++){
                    month.set(j, 0);
                }
            }
        }
    }

    private static List<Integer> getListMonthFromId(int id){
        switch(id){
            case 0:
                return january;
            case 1:
                return february;
            case 2:
                return march;
            case 3:
                return april;
            case 4:
                return may;
            case 5:
                return june;
            case 6:
                return july;
            case 7:
                return august;
            case 8:
                return september;
            case 9:
                return october;
            case 10:
                return november;
            case 11:
                return december;
            default:
                return null;
        }
    }

    private static void removeSingleDayHoliday(Calendar date){

        switch(date.get(Calendar.MONTH)){
            case 0:
                january.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 1:
                february.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 2:
                march.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 3:
                april.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 4:
                may.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 5:
                june.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 6:
                july.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 7:
                august.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 8:
                september.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 9:
                october.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 10:
                november.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            case 11:
                december.set(date.get(Calendar.DAY_OF_MONTH) - 1, 0);
                break;
            default:
                break;
        }

    }

}
