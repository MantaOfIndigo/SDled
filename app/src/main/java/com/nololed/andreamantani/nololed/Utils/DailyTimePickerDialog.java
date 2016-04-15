package com.nololed.andreamantani.nololed.Utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

/**
 * Created by andreamantani on 08/04/16.
 */
public class DailyTimePickerDialog extends TimePickerDialog {

    int minHour;
    int maxHour;
    int currentHour;
    int currentMinute;

    public DailyTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView, int minHour, int maxHour) {
        super(context, listener, hourOfDay, minute, is24HourView);
        this.minHour = minHour;
        this.maxHour = maxHour;
        this.currentHour = hourOfDay;
        this.currentMinute = minute;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        if (minute > 0 && minute < 30){
            currentMinute = 30;
        }


        if (minute > 30 && minute < 59){
            currentMinute = 0;
        }



        boolean validTime = true;
        if (hourOfDay < minHour){
            validTime = false;

        }

        if (hourOfDay  > maxHour || hourOfDay == maxHour){
            validTime = false;
            currentHour = maxHour;
            currentMinute = 0;
        }

        if (validTime) {
            currentHour = hourOfDay;
        }

        updateTime(currentHour, currentMinute);
    }
}
