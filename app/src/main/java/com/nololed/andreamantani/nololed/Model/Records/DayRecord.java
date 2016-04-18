package com.nololed.andreamantani.nololed.Model.Records;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

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

    public DayRecord(Context context, AttributeSet attrs, final String dayName, int index, OnClickListener setHoursListener){
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

        ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.day_records_toggle);
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StandardWorkHours.getDayStandard(getDayName()).setEnable(isChecked);
            }
        });

        setToggleButtonState(StandardWorkHours.getDayStandard(this.getDayName()).getEnable());

    }

    public DayRecord(Context context, AttributeSet attrs, final String dayName, int index, OnClickListener setHoursListener, Boolean custom){
        super(context, attrs);


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.day_record, this);

        CustomExtraLightFont dayTitleSetter = (CustomExtraLightFont) findViewById(R.id.day_record_day_string);
        dayTitleSetter.setText(dayName);
        Log.v("week", dayName);

        TextView dayRecordHour = (TextView) findViewById(R.id.day_record_hours);
        dayRecordHour.setText(StandardWorkHours.getCustomDayStandard(dayName).toString());

        this.dayName = dayName;
        this.setHourLayout = (FrameLayout) findViewById(R.id.day_record_layout_listener);
        this.setHourLayout.setOnClickListener(setHoursListener);

        ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.day_records_toggle);
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StandardWorkHours.getCustomDayStandard(dayName).setEnable(isChecked);
            }
        });

        setToggleButtonState(StandardWorkHours.getCustomDayStandard(dayName).getEnable());

    }

    private void setStandards(){
        DailyHours myStandard = StandardWorkHours.getDayStandard(this.getDayName());
        TextView dayRecordHour = (TextView) findViewById(R.id.day_record_hours);
        dayRecordHour.setText(myStandard.toString());
    }
    public FrameLayout getSetHourLayout(){
        return this.setHourLayout;
    }

    public boolean getToggleButtonState(){
        ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.day_records_toggle);
        if(toggleBtn.isChecked()){
            return true;
        }

        return false;
    }

    public void setToggleButtonState(boolean value){
        ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.day_records_toggle);
        if(value){
            toggleBtn.setChecked(value);
        }

        toggleBtn.setChecked(value);
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
