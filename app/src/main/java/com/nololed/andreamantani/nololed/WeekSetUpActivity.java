package com.nololed.andreamantani.nololed;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.nololed.andreamantani.nololed.Model.DateRecord;
import com.nololed.andreamantani.nololed.Model.DayRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 06/04/16.
 */
public class WeekSetUpActivity extends AppCompatActivity {


    List<DayRecord> recordController;
    LinearLayout scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_setting);
        recordController = new ArrayList<>();
        populateScroll();
    }

    private void populateScroll(){
        scroll = (LinearLayout) findViewById(R.id.weekly_scroll_layout);
        String[] weekName = new String[]{"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};

        for(int i = 0; i < weekName.length; i++){
            DayRecord newItem = new DayRecord(WeekSetUpActivity.this, null, weekName[i], i, setHoursListener);
            scroll.addView(newItem);
            recordController.add(newItem);
        }
    }

    private View.OnClickListener setHoursListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for(int i = 0; i < scroll.getChildCount(); i++){
                View row = scroll.getChildAt(i);
                if(((DayRecord) row).getSetHourLayout()  == v){
                    Intent weekIntent = new Intent(WeekSetUpActivity.this, DailyWorkhoursActivity.class);
                    weekIntent.putExtra("day_name", ((DayRecord) row).getDayName());

                    startActivity(weekIntent);
                }
            }
        }
    };


}
