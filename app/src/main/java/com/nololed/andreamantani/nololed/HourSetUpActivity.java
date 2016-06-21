package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;
import com.nololed.andreamantani.nololed.Utils.DailyTimePickerDialog;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

import java.util.Calendar;

/**
 * Created by andreamantani on 03/05/16.
 */
public class HourSetUpActivity extends AppCompatActivity{

    //DailyHours workHours;
    Calendar bAMCalendar;
    Calendar eAMCalendar;
    Calendar bPMCalendar;
    Calendar ePMCalendar;

    LinearLayout beginAM;
    LinearLayout endAM;
    LinearLayout beginPM;
    LinearLayout endPM;

    DailyHours myStandard;

    boolean hourConfirmed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_setup);

        StandardWorkHours.setStandards();
        myStandard = StandardWorkHours.getDayStandard("Monday");

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
        endPMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.getEndPMHour(), myStandard.getEndPMMinute()));

        beginAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(HourSetUpActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                DailyTimePickerDialog time = new DailyTimePickerDialog(HourSetUpActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                DailyTimePickerDialog time = new DailyTimePickerDialog(HourSetUpActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                DailyTimePickerDialog time = new DailyTimePickerDialog(HourSetUpActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        endPMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        ePMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        ePMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(ePMCalendar, 3);

                    }
                }, myStandard.getEndPMHour(), myStandard.getEndPMMinute(), true, 13, 23);
                time.show();
            }
        });

    }


    private boolean checkDates(Calendar item, int index){
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

    private void setUpRecordLayout(LinearLayout record, boolean error){

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
            endPMtxt.setTextColor(Color.parseColor("#257b9f"));
        }

        if(record == endAM && !error){
            endAM.setBackgroundResource(R.color.custom_greenblue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_toggleAM);
            tgBtn.setChecked(true);
            endAMtxt.setTextColor(Color.parseColor("#257b9f"));
        }

        if (record == beginPM && !error){
            beginPM.setBackgroundResource(R.color.custom_greenblue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePMB);
            tgBtn.setChecked(true);
            begPMtxt.setTextColor(Color.parseColor("#257b9f"));
        }

    }

    public void saveHourStandard(View v){
        DailyHours workHours = new DailyHours();
        workHours.setAMTurn(bAMCalendar, eAMCalendar);
        workHours.setPMTurn(bPMCalendar, ePMCalendar);

        StandardWorkHours.setStandards(workHours);

        startActivity(new Intent(HourSetUpActivity.this, WeekSetUpActivity.class));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HourSetUpActivity.this.finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
