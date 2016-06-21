package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.nololed.andreamantani.nololed.Model.Records.DayRecord;
import com.nololed.andreamantani.nololed.Utils.SolarYearHours;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setEnableDays(){
        for(int i = 0; i < recordController.size(); i++){
            if(recordController.get(i).getToggleButtonState()){
                if(SolarYearHours.isSolarYear()){
                    SolarYearHours.enableDayOfWeek(recordController.get(i).getDayName(), true);
                }else {
                    StandardWorkHours.setEnableStandardDay(recordController.get(i).getDayName(), true);
                }
            }else{
                if(SolarYearHours.isSolarYear()){
                    SolarYearHours.enableDayOfWeek(recordController.get(i).getDayName(), false);
                }else {
                    StandardWorkHours.setEnableStandardDay(recordController.get(i).getDayName(), false);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:

                setEnableDays();

                if(SolarYearHours.isSolarYear()){
                    SolarYearHours.removeDaysOfWeek();
                }

                Intent intent = new Intent(WeekSetUpActivity.this, AddHolidaysPeriodsActivity.class);
                intent.putExtra("new_photo", 0);

                startActivity(intent);
        }
        return true;
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

            if(!SolarYearHours.isSolarYear()) {
                for (int i = 0; i < scroll.getChildCount(); i++) {
                    View row = scroll.getChildAt(i);
                    if (((DayRecord) row).getSetHourLayout() == v) {
                        Intent weekIntent = new Intent(WeekSetUpActivity.this, DailyWorkhoursActivity.class);
                        weekIntent.putExtra("day_name", ((DayRecord) row).getDayName());

                        startActivity(weekIntent);
                    }
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WeekSetUpActivity.this.finish();
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
