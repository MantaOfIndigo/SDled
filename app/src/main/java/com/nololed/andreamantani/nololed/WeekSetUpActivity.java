package com.nololed.andreamantani.nololed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.nololed.andreamantani.nololed.Model.Records.DayRecord;

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                startActivity(new Intent(WeekSetUpActivity.this, ProfileTecnologyActivity.class));
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
