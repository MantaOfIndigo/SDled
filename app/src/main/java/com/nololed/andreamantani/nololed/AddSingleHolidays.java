package com.nololed.andreamantani.nololed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.Model.HolidayPeriod;
import com.nololed.andreamantani.nololed.Model.SingleDayRecord;
import com.nololed.andreamantani.nololed.Utils.CalendarDates;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

import java.util.List;

/**
 * Created by andreamantani on 10/04/16.
 */
public class AddSingleHolidays extends AppCompatActivity {

    LinearLayout scrollContent;
    int christmasEnabled;
    int easterEnabled;
    int firstOfYearEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_holidays);



        CalendarDates.settingUpHolidays();
        setListeners();

        scrollContent = (LinearLayout) findViewById(R.id.single_holiday_scroll_content);

        for(int i = 0; i < 3; i++){
            scrollContent.addView(new SingleDayRecord(AddSingleHolidays.this, null, CalendarDates.getCurrentYearEaster().getTime(), toggleEnable));
        }
    }

    private void setListeners(){
        LinearLayout christmasLayout = (LinearLayout) findViewById(R.id.single_holiday_christmas);
        LinearLayout easterLayout = (LinearLayout) findViewById(R.id.single_holiday_easter);
        LinearLayout firstOfYearLayout = (LinearLayout) findViewById(R.id.single_holiday_firstOfYear);

        TextView easterDate = (TextView) findViewById(R.id.single_day_easter_textdate);
        easterDate.setText(CalendarUtils.getDateFormatted(CalendarDates.getCurrentYearEaster().getTime()));

        if(!StandardWorkHours.isChristmasEnabled()){
            christmasEnabled = 3;
            setAlpha(christmasEnabled, christmasLayout);
        }else{
            christmasEnabled = 1;
            christmasLayout.setOnClickListener(toggleChristmas);
        }
        if(!StandardWorkHours.isEasterEnabled()){
            easterEnabled = 3;
            setAlpha(easterEnabled, easterLayout);
        }else{
            easterEnabled = 1;
            easterLayout.setOnClickListener(toggleEaster);
        }
        if(!StandardWorkHours.isFirstOfYearEnabled()){
            firstOfYearEnabled = 3;
            setAlpha(firstOfYearEnabled, firstOfYearLayout);
        }else{
            firstOfYearEnabled = 1;
            firstOfYearLayout.setOnClickListener(toggleFirstOfYear);
        }

    }

    private void setAlpha(int enabledCode, View v){
        if(enabledCode == 1){
            v.setAlpha((float) 0.3);
        }else if(enabledCode == 2){
            v.setAlpha((float) 1);
        } else{
            v.setAlpha((float) 0.3);
        }
    }
    private View.OnClickListener toggleChristmas = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            setAlpha(christmasEnabled, v);

            if(christmasEnabled == 1){
                christmasEnabled = 2;
            }else if(christmasEnabled == 2){
                christmasEnabled = 1;
            }
        }
    };

    private View.OnClickListener toggleEaster = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            setAlpha(easterEnabled, v);

            if(easterEnabled == 1){
                easterEnabled = 2;
            }else if(easterEnabled == 2){
                easterEnabled = 1;
            }
        }
    };

    private View.OnClickListener toggleFirstOfYear = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            setAlpha(firstOfYearEnabled, v);

            if(firstOfYearEnabled == 1){
                firstOfYearEnabled = 2;
            }else if(firstOfYearEnabled == 2){
                firstOfYearEnabled = 1;
            }
        }
    };

    private View.OnClickListener toggleEnable = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0; i < scrollContent.getChildCount(); i++){
                if( v == scrollContent.getChildAt(i)){
                    ((SingleDayRecord) v).toggleEnable();
                }
            }
        }
    };

}
