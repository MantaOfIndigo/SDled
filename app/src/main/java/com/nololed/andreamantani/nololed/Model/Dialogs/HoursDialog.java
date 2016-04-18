package com.nololed.andreamantani.nololed.Model.Dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.nololed.andreamantani.nololed.DailyWorkhoursActivity;
import com.nololed.andreamantani.nololed.FontClasses.CustomButtonFont;
import com.nololed.andreamantani.nololed.FontClasses.CustomItalicFont;
import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.ProfileTecnologyActivity;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;
import com.nololed.andreamantani.nololed.Utils.DailyTimePickerDialog;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;
import com.nololed.andreamantani.nololed.WeekSetUpActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andreamantani on 17/04/16.
 */
public class HoursDialog extends LinearLayout {

    Calendar bAMCalendar;
    Calendar eAMCalendar;
    Calendar bPMCalendar;
    Calendar ePMCalendar;

    LinearLayout beginAM;
    LinearLayout endAM;
    LinearLayout beginPM;
    LinearLayout endPM;

    DailyHours myStandard;

    boolean hourConfirmed = false;

    String dayName;

    Context contextDialog;
    Dialog currDialog;

    OnClickListener updateTable;


    public HoursDialog(final Context context, AttributeSet attrs, Dialog dialog, String dayName, OnClickListener populate) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_hours, this);


        this.updateTable = populate;
        this.contextDialog = dialog.getContext();
        this.currDialog = dialog;
        this.dayName = dayName;

        CustomButtonFont okButton = (CustomButtonFont) findViewById(R.id.dialog_date_ok);
        CustomButtonFont cancelButton = (CustomButtonFont) findViewById(R.id.dialog_date_null);

        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHour();
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        myStandard = StandardWorkHours.getDayStandard(dayName);
        //workHours = new DailyHours();

        bAMCalendar = myStandard.getBeginAMCalendar();
        eAMCalendar = myStandard.getEndAMCalendar();
        bPMCalendar = myStandard.getBeginPMCalendar();
        ePMCalendar = myStandard.getEndPMCalendar();

        beginAM = (LinearLayout) findViewById(R.id.daily_hour_begin_am);
        endAM = (LinearLayout) findViewById(R.id.daily_hour_end_am);
        beginPM = (LinearLayout) findViewById(R.id.daily_hour_begin_pm);
        endPM = (LinearLayout) findViewById(R.id.daily_hour_end_pm);

        final TextView beginAMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginam);
        final TextView endAMtxt = (TextView) findViewById(R.id.daily_hour_txt_endam);
        final TextView beginPMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginpm);
        final TextView endPMtxt = (TextView) findViewById(R.id.daily_hour_txt_endpm);

        beginAMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.getBeginAMHour(), myStandard.getBeginAMMinute()));
        endAMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.geteEndAMHour(), myStandard.getEndAMMinute()));
        beginPMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.getBeginPMHour(), myStandard.getBeginPMMinute()));
        endPMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.geteEndPMHour(), myStandard.getEndPMMinute()));

        beginAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(contextDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginAMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        bAMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        bAMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(bAMCalendar, 0);
                    }
                }, myStandard.getBeginAMHour(), myStandard.getBeginAMMinute(), true, 0, 13);
                time.show();
            }
        });

        endAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(contextDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endAMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        eAMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        eAMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(eAMCalendar, 1);
                    }
                }, myStandard.geteEndAMHour(), myStandard.getEndAMMinute(), true, 0, 23);
                time.show();
            }
        });

        beginPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(contextDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginPMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        bPMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        bPMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(bPMCalendar, 2);
                    }
                }, myStandard.getBeginPMHour(), myStandard.getBeginPMMinute(), true, 0, 23);

                time.show();
            }
        });

        endPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(contextDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        endPMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        ePMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        ePMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(ePMCalendar, 3);

                    }
                }, myStandard.geteEndPMHour(), myStandard.getEndPMMinute(), true, 13, 23);
                time.show();
            }
        });
    }

    private boolean checkDates(Calendar item, int index) {
        boolean flag = true;
        if(CalendarUtils.checkHours(bAMCalendar, eAMCalendar)){
            setUpRecordLayout(endAM, false);
        }else{
            setUpRecordLayout(endAM, true);
            flag = false;
        }

        if(CalendarUtils.checkHours(bPMCalendar, ePMCalendar)){
            setUpRecordLayout(endPM, false);
        }else{
            setUpRecordLayout(endPM, true);
            flag = false;
        }

        if(CalendarUtils.checkHours(bAMCalendar,bPMCalendar)){
            setUpRecordLayout(beginPM, false);
        }else {
            setUpRecordLayout(beginPM, true);
            flag = false;
        }

        if(CalendarUtils.checkHours(eAMCalendar, bPMCalendar)){
            setUpRecordLayout(beginPM, false);
        }else {
            setUpRecordLayout(beginPM, true);
            flag = false;
        }

        hourConfirmed = flag;
        return flag;
    }

    private void setUpRecordLayout(LinearLayout record, boolean error) {
        TextView endAMtxt = (TextView) findViewById(R.id.daily_hour_txt_endam);
        TextView endPMtxt = (TextView) findViewById(R.id.daily_hour_txt_endpm);
        TextView begPMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginpm);

        if (record == endPM && error){
            endPM.setBackgroundResource(R.color.custom_red_error);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePM);
            tgBtn.setChecked(false);
            endPMtxt.setTextColor(Color.WHITE);
        }

        if(record == endAM && error){
            endAM.setBackgroundResource(R.color.custom_red_error);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_toggleAM);
            tgBtn.setChecked(false);
            endAMtxt.setTextColor(Color.WHITE);
        }

        if(record == beginPM && error){
            beginPM.setBackgroundResource(R.color.custom_red_error);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePMB);
            tgBtn.setChecked(false);
            begPMtxt.setTextColor(Color.WHITE);
        }

        if (record == endPM && !error){
            endPM.setBackgroundResource(R.color.custom_greenblue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePM);
            tgBtn.setChecked(true);
            endPMtxt.setTextColor(Color.parseColor("#677a82"));
        }

        if(record == endAM && !error){
            endAM.setBackgroundResource(R.color.custom_greenblue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_toggleAM);
            tgBtn.setChecked(true);
            endAMtxt.setTextColor(Color.parseColor("#677a82"));
        }

        if (record == beginPM && !error){
            beginPM.setBackgroundResource(R.color.custom_greenblue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePMB);
            tgBtn.setChecked(true);
            begPMtxt.setTextColor(Color.parseColor("#677a82"));
        }




    }

    public void saveHour() {
        if (hourConfirmed) {
            DailyHours workHours = new DailyHours();
            workHours.setAMTurn(bAMCalendar, eAMCalendar);
            workHours.setPMTurn(bPMCalendar, ePMCalendar);
            StandardWorkHours.setCustomDailyHours(this.dayName, workHours);


            this.updateTable.onClick(this);
            this.currDialog.dismiss();
        }

    }
    public void back(){
        this.currDialog.dismiss();
    }
}
