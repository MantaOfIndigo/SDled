package com.nololed.andreamantani.nololed;

import android.app.Activity;
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
import com.nololed.andreamantani.nololed.Utils.SolarYearHours;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

import java.util.Calendar;

/**
 * Created by andreamantani on 13/05/16.
 */
public class SetupSolarActivity extends AppCompatActivity{

    //DailyHours workHours;
    Calendar bAMCalendar;
    Calendar eAMCalendar;

    LinearLayout beginAM;
    LinearLayout endAM;

    DailyHours myStandard;

    boolean hourConfirmed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_solar);

        Calendar beginTmp = CalendarUtils.newInitializedCalendar();
        Calendar endTmp = CalendarUtils.newInitializedCalendar();

        beginTmp.set(Calendar.HOUR_OF_DAY, 9);
        beginTmp.set(Calendar.MINUTE, 0);
        endTmp.set(Calendar.HOUR_OF_DAY, 19);
        endTmp.set(Calendar.MINUTE, 0);

        myStandard = new DailyHours();
        myStandard.setAMTurn(beginTmp, endTmp);

        bAMCalendar = myStandard.getBeginAMCalendar();
        eAMCalendar = myStandard.getEndAMCalendar();

        beginAM = (LinearLayout) findViewById(R.id.daily_hour_begin_am);
        endAM = (LinearLayout) findViewById(R.id.daily_hour_end_am);

        final TextView beginAMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginam);
        final TextView endAMtxt = (TextView) findViewById(R.id.daily_hour_txt_endam);

        beginAMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.getBeginAMHour(), myStandard.getBeginAMMinute()));
        endAMtxt.setText(CalendarUtils.getTimeFormatted(myStandard.geteEndAMHour(), myStandard.getEndAMMinute()));

        beginAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(SetupSolarActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                DailyTimePickerDialog time = new DailyTimePickerDialog(SetupSolarActivity.this, new TimePickerDialog.OnTimeSetListener() {
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


    }

    public void saveClosingHour(View v){
        DailyHours workHours = new DailyHours();
        workHours.setAMTurn(bAMCalendar, eAMCalendar);

        SolarYearHours.setSolarYear(workHours);

        startActivity(new Intent(SetupSolarActivity.this, WeekSetUpActivity.class));
    }







    private boolean checkDates(Calendar item, int index){
        boolean flag = true;
        if(CalendarUtils.checkHours(bAMCalendar, eAMCalendar)){
            setUpRecordLayout(endAM, false);
        }else{
            setUpRecordLayout(endAM, true);
            flag = false;
        }

        hourConfirmed = flag;
        return flag;
    }

    private void setUpRecordLayout(LinearLayout record, boolean error){

        TextView endAMtxt = (TextView) findViewById(R.id.daily_hour_txt_endam);
        TextView endPMtxt = (TextView) findViewById(R.id.daily_hour_txt_endpm);
        TextView begPMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginpm);

        if(record == endAM && error){
            endAM.setBackgroundResource(R.color.custom_red_error);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_toggleAM);
            tgBtn.setChecked(false);
            endAMtxt.setTextColor(Color.WHITE);
        }


        if(record == endAM && !error){
            endAM.setBackgroundResource(R.color.custom_bright_blue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_toggleAM);
            tgBtn.setChecked(true);
            endAMtxt.setTextColor(Color.parseColor("#FFFFFF"));
        }


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SetupSolarActivity.this.finish();
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

