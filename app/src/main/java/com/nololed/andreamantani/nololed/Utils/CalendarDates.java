package com.nololed.andreamantani.nololed.Utils;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by andreamantani on 10/04/16.
 */
public class CalendarDates {
    private static Calendar[] eastersInYears;
    private static Calendar christmas;
    private static Calendar firstOfYear;

    private static Calendar currentDate;

    public static void settingUpHolidays(){
        currentDate = Calendar.getInstance();

        christmas = Calendar.getInstance();
        christmas.set(Calendar.MONTH, 11);
        christmas.set(Calendar.DAY_OF_MONTH, 25);
        christmas.set(Calendar.MINUTE, 0);
        christmas.set(Calendar.SECOND, 0);

        firstOfYear = Calendar.getInstance();
        firstOfYear.set(Calendar.MONTH, 0);
        firstOfYear.set(Calendar.DAY_OF_MONTH, 1);
        firstOfYear.set(Calendar.MINUTE, 0);
        firstOfYear.set(Calendar.SECOND, 0);

        settingEastersInYears();
    }

    private static void settingEastersInYears(){

        eastersInYears = new Calendar[7];

        Calendar easter2016 = Calendar.getInstance();
        easter2016.set(Calendar.YEAR, 2016);
        easter2016.set(Calendar.MONTH, 2);
        easter2016.set(Calendar.DAY_OF_MONTH, 27);
        easter2016.set(Calendar.MINUTE, 0);
        easter2016.set(Calendar.SECOND, 0);

        eastersInYears[0] = easter2016;

        Calendar easter2017 = Calendar.getInstance();
        easter2017.set(Calendar.YEAR, 2017);
        easter2017.set(Calendar.MONTH, 3);
        easter2017.set(Calendar.DAY_OF_MONTH, 16);
        easter2017.set(Calendar.MINUTE, 0);
        easter2017.set(Calendar.SECOND, 0);

        eastersInYears[1] = easter2017;

        Calendar easter2018 = Calendar.getInstance();
        easter2018.set(Calendar.YEAR, 2018);
        easter2018.set(Calendar.MONTH, 3);
        easter2018.set(Calendar.DAY_OF_MONTH, 1);
        easter2018.set(Calendar.MINUTE, 0);
        easter2018.set(Calendar.SECOND, 0);

        eastersInYears[2] = easter2018;

        Calendar easter2019 = Calendar.getInstance();
        easter2019.set(Calendar.YEAR, 2019);
        easter2019.set(Calendar.MONTH, 3);
        easter2019.set(Calendar.DAY_OF_MONTH, 21);
        easter2019.set(Calendar.MINUTE, 0);
        easter2019.set(Calendar.SECOND, 0);

        eastersInYears[3] = easter2019;

        Calendar easter2020 = Calendar.getInstance();
        easter2020.set(Calendar.YEAR, 2020);
        easter2020.set(Calendar.MONTH, 3);
        easter2020.set(Calendar.DAY_OF_MONTH, 12);
        easter2020.set(Calendar.MINUTE, 0);
        easter2020.set(Calendar.SECOND, 0);

        eastersInYears[4] = easter2020;

        Calendar easter2021 = Calendar.getInstance();
        easter2021.set(Calendar.YEAR, 2021);
        easter2021.set(Calendar.MONTH, 3);
        easter2021.set(Calendar.DAY_OF_MONTH, 4);
        easter2021.set(Calendar.MINUTE, 0);
        easter2021.set(Calendar.SECOND, 0);

        eastersInYears[5] = easter2021;

        Calendar easter2022 = Calendar.getInstance();
        easter2022.set(Calendar.YEAR, 2022);
        easter2022.set(Calendar.MONTH, 3);
        easter2022.set(Calendar.DAY_OF_MONTH, 17);
        easter2022.set(Calendar.MINUTE, 0);
        easter2022.set(Calendar.SECOND, 0);

        eastersInYears[6] = easter2022;

        Log.v("clock", "east " + eastersInYears[0].getTime().toString());
        Log.v("clock", "east " + eastersInYears[1].getTime().toString());
        Log.v("clock", "east " + eastersInYears[2].getTime().toString());
        Log.v("clock", "east " + eastersInYears[3].getTime().toString());
        Log.v("clock", "east " + eastersInYears[4].getTime().toString());
        Log.v("clock", "east " + eastersInYears[5].getTime().toString());
        Log.v("clock", "east " + eastersInYears[6].getTime().toString());



    }

    public static Calendar getChristmas(){
        return christmas;
    }

    public static Calendar getFirstOfYear(){
        return firstOfYear;
    }

    public static Calendar getCurrentYearEaster(){
        switch (currentDate.get(Calendar.YEAR)){
            case 2016:
                return eastersInYears[0];
            case 2017:
                return eastersInYears[1];
            case 2018:
                return eastersInYears[2];
            case 2019:
                return eastersInYears[3];
            case 2020:
                return eastersInYears[4];
            case 2021:
                return eastersInYears[5];
            case 2022:
                return eastersInYears[6];
            default:
                return null;
        }
    }
}
