package com.nololed.andreamantani.nololed.Model.Records;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.FontClasses.CustomExtraLightFont;
import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

/**
 * Created by andreamantani on 07/04/16.
 */
public class DayRecord extends LinearLayout {

    FrameLayout setHourLayout;
    String dayName;

    public DayRecord(Context context, AttributeSet attrs, String dayName, int index, OnClickListener setHoursListener){
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.day_record, this);

        CustomExtraLightFont dayTitleSetter = (CustomExtraLightFont) findViewById(R.id.day_record_day_string);
        dayTitleSetter.setText(dayName);
        Log.v("week", dayName);
        this.dayName = dayName;
        this.setStandards();
        this.setHourLayout = (FrameLayout) findViewById(R.id.day_record_layout_listener);
        this.setHourLayout.setOnClickListener(setHoursListener);
    }

    private void setStandards(){
        DailyHours myStandard = StandardWorkHours.getDayStandard(this.getDayName());
        TextView dayRecordHour = (TextView) findViewById(R.id.day_record_hours);
        dayRecordHour.setText(myStandard.toString());
    }
    public FrameLayout getSetHourLayout(){
        return this.setHourLayout;
    }

    public String getDayName(){
        switch (this.dayName){
            case "Lunedì":
                return "Monday";
            case "Martedì":
                return "Tuesday";
            case "Mercoledì":
                return "Thursday";
            case "Giovedì":
                return "Wednesday";
            case "Venerdì":
                return "Friday";
            case "Sabato":
                return "Saturday";
            case "Domenica":
                return "Sunday";
            default:
                return "";
        }
    }
}
