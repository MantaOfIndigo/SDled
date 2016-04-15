package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nololed.andreamantani.nololed.Model.Dialogs.SingleDateDialog;
import com.nololed.andreamantani.nololed.Model.Records.SingleDayRecord;
import com.nololed.andreamantani.nololed.Utils.Algorithm;
import com.nololed.andreamantani.nololed.Utils.CalendarDates;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andreamantani on 10/04/16.
 */
public class AddSingleHolidays extends AppCompatActivity {

    LinearLayout scrollContent;
    int christmasEnabled;
    int easterEnabled;
    int firstOfYearEnabled;

    List<Date> holyDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_holidays);

        CalendarDates.settingUpHolidays();
        setListeners();

        scrollContent = (LinearLayout) findViewById(R.id.single_holiday_scroll_content);

        this.holyDates = new ArrayList<>();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                for(int i = 0; i < scrollContent.getChildCount(); i++){
                    if(((SingleDayRecord) scrollContent.getChildAt(i)).getEnabled()){
                        StandardWorkHours.addSingleDay(holyDates.get(i));
                    }
                }

                startActivity(new Intent(AddSingleHolidays.this, WeekSetUpActivity.class));
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.check_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openCalendarDialog(View v){
        Dialog singleDateDialog = new Dialog(AddSingleHolidays.this);
        singleDateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        singleDateDialog.setContentView(new SingleDateDialog(AddSingleHolidays.this, null, singleDateDialog));

        singleDateDialog.show();
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
            v.setClickable(false);
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

    public void setNewDateRecord(Date dateSelected){
        int oldSize = this.holyDates.size();
        this.holyDates = Algorithm.addOrdinateDateToList(dateSelected, this.holyDates);

        if(this.holyDates.size() == oldSize){
            Toast.makeText(this, "Giorno già inserito", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Aggiunto", Toast.LENGTH_LONG).show();
        }
    }

    private void populateList(){
        scrollContent.removeAllViews();
        for(int i = 0; i < holyDates.size(); i++){
            scrollContent.addView(new SingleDayRecord(AddSingleHolidays.this, null, holyDates.get(i), toggleEnable));
        }
    }

    public void resumeActivity(){
        this.populateList();
    }

}
