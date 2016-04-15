package com.nololed.andreamantani.nololed;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 * Created by andreamantani on 07/04/16.
 */
public class DailyWorkhoursActivity extends AppCompatActivity{

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

    String dayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_workhours);



        Bundle extras = getIntent().getExtras();
        dayName = extras.getString("day_name");

        setTitle(this.dayName);

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
                DailyTimePickerDialog time = new DailyTimePickerDialog(DailyWorkhoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginAMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        bAMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        bAMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(bAMCalendar, 0);
                    }
                }, myStandard.getBeginAMHour(), myStandard.getBeginAMMinute(), true, 6, 12);
                time.show();
            }
        });

        endAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(DailyWorkhoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endAMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        eAMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        eAMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(eAMCalendar, 1);
                    }
                }, myStandard.geteEndAMHour(), myStandard.getEndAMMinute(), true, 9, 13);
                time.show();
            }
        });

        beginPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(DailyWorkhoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginPMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        bPMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        bPMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(bPMCalendar, 2);
                    }
                }, myStandard.getBeginPMHour(), myStandard.getBeginPMMinute(), true, 13, 18);

                time.show();
            }
        });

        endPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTimePickerDialog time = new DailyTimePickerDialog(DailyWorkhoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        endPMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        ePMCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        ePMCalendar.set(Calendar.MINUTE, minute);
                        checkDates(ePMCalendar, 3);

                    }
                }, myStandard.geteEndPMHour(), myStandard.getEndPMMinute(), true, 14, 20);
                time.show();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DailyHours workHours = new DailyHours();
        switch (item.getItemId()) {
            case R.id.action_favorite:
                workHours.setAMTurn(bAMCalendar, eAMCalendar);
                workHours.setPMTurn(bPMCalendar, ePMCalendar);
                StandardWorkHours.setDailyHours(this.dayName, workHours);
                startActivity(new Intent(DailyWorkhoursActivity.this, WeekSetUpActivity.class));
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean checkDates(Calendar item, int index){
        if(CalendarUtils.checkHours(bAMCalendar, eAMCalendar)){
            setUpRecordLayout(endAM, false);
        }else{
            setUpRecordLayout(endAM, true);
        }

        if(CalendarUtils.checkHours(bPMCalendar, ePMCalendar)){
            setUpRecordLayout(endPM, false);
        }else{
            setUpRecordLayout(endPM, true);
        }

        return false;
    }

    private void setUpRecordLayout(LinearLayout record, boolean error){

        TextView endAMtxt = (TextView) findViewById(R.id.daily_hour_txt_endam);
        TextView endPMtxt = (TextView) findViewById(R.id.daily_hour_txt_endpm);

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




    }




}
