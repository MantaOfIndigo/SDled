package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.nololed.andreamantani.nololed.Model.CustomDatePickerDialog;
import com.nololed.andreamantani.nololed.Model.Dialogs.DateDialog;
import com.nololed.andreamantani.nololed.Model.Records.DateRecord;
import com.nololed.andreamantani.nololed.Model.HolidayPeriod;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by andreamantani on 31/03/16.
 */
public class AddHolidaysPeriodsActivity extends AppCompatActivity {

    TableLayout tbLayout;
    Dialog dialogBegin;
    Calendar beginDate;

    List<HolidayPeriod> periods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holidays);

        StandardWorkHours.setStandards();


        if (StandardWorkHours.getPeriods().size() > 0){
            this.periods = StandardWorkHours.getPeriods();
        }else {
            this.periods = new ArrayList<>();
        }

        tbLayout = (TableLayout) findViewById(R.id.holiday_table);
        FrameLayout frameBegin = (FrameLayout) findViewById(R.id.holiday_frame_begin_date);
        FrameLayout frameEnd = (FrameLayout) findViewById(R.id.holiday_frame_end_date);

        frameBegin.setOnClickListener(clickBeginDate);
        frameEnd.setOnClickListener(clickEndDate);

        final TextView txtBeginDateControl = (TextView) findViewById(R.id.add_holiday_begin);
        txtBeginDateControl.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                TextView endDate = (TextView) findViewById(R.id.add_holiday_end);
                if(!endDate.getText().toString().isEmpty()){
                    Calendar endDateCalendar = CalendarUtils.getDateFromString(endDate.getText().toString());
                    if (endDateCalendar != null){
                        if (!CalendarUtils.inBetweenDate(CalendarUtils.getDateFromString(txtBeginDateControl.getText().toString()), endDateCalendar)) {
                            Toast.makeText(AddHolidaysPeriodsActivity.this, "Seleziona un periodo valido", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        populateTable();
        //holidaysList =


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                if(periods.size() == 0){
                    new AlertDialog.Builder(this)
                            .setTitle("Attenzione")
                            .setMessage("Inserire eventuali periodi feriali dell'attività. Continuare se non ne possiede.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(AddHolidaysPeriodsActivity.this, AddSingleHolidays.class));
                                }
                            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();
                }else{
                    StandardWorkHours.setPeriods(this.periods);
                    startActivity(new Intent(AddHolidaysPeriodsActivity.this, AddSingleHolidays.class));
                }
        }

        return true;
    }


    private boolean control(){
        TextView beginCheck1 = (TextView) findViewById(R.id.add_holiday_begin);
        TextView endCheck2 = (TextView) findViewById(R.id.add_holiday_end);
        Calendar beginChecker1 = CalendarUtils.getDateFromString(beginCheck1.getText().toString());
        Calendar endChecker2 = CalendarUtils.getDateFromString(endCheck2.getText().toString());

        if(beginChecker1 != null){
            if(endChecker2 != null){
                HolidayPeriod check3 = new HolidayPeriod(beginChecker1, endChecker2);

                for(int i = 0; i < periods.size(); i++){
                    if(periods.get(i).equals(check3)){
                        return false;
                    }
                }

            }
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        hideKeyboard();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void populateTable(){
        tbLayout.removeAllViews();
        for(int i = 0; i < this.periods.size(); i++){
            tbLayout.addView(new DateRecord(getBaseContext(), null, this.periods.get(i).getBegin(), this.periods.get(i).getEnd(), deleteIndexRow, i));
        }
    }
    private void addHolidayPeriod(Date begin, Date end){
        /* dSharedPreferences sharedPref = getSharedPreferences("holidays", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_holidays_list", sys.toString());
        editor.commit();*/
    }

    private View.OnClickListener deleteIndexRow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for(int i = 0; i < tbLayout.getChildCount(); i++){
                View row = tbLayout.getChildAt(i);
                if(((DateRecord) row).getDeleteLayout()  == v){
                    //sys.getList().remove(((TecnologyRecord) row).getTecnology().getIndex());

                    tbLayout.removeView(row);
                    periods.remove(((DateRecord) row).getIndex());
                    SharedPreferences sharedPref = getSharedPreferences("holidays", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("current_holidays_list", periods.toString());
                    editor.commit();

                    populateTable();
                }
            }
        }
    };

    private View.OnClickListener clickBeginDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            TextView checkEndDate = (TextView) findViewById(R.id.add_holiday_end);
            Calendar cal = Calendar.getInstance();

            if(!checkEndDate.getText().toString().isEmpty()){
                Calendar tmp = CalendarUtils.getDateFromString(checkEndDate.getText().toString());
                cal.set(Calendar.DAY_OF_MONTH, tmp.get(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.MONTH, tmp.get(Calendar.MONTH));
            }else{
                cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.MONTH, cal.getMaximum(Calendar.MONTH));
            }
            dialogBegin = new Dialog(AddHolidaysPeriodsActivity.this);
            //dpd.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBegin.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBegin.setContentView(new DateDialog(AddHolidaysPeriodsActivity.this, null, cal, true, dialogBegin));
            dialogBegin.show();
        }
    };

    private View.OnClickListener clickEndDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView checkBeginDate = (TextView) findViewById(R.id.add_holiday_begin);
            Calendar cal = CalendarUtils.newInitializedCalendar();

            if(!checkBeginDate.getText().toString().isEmpty()){
                Calendar tmp = CalendarUtils.getDateFromString(checkBeginDate.getText().toString());
                cal.set(Calendar.DAY_OF_MONTH, tmp.get(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.MONTH, tmp.get(Calendar.MONTH));
            }else{
                cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.MONTH,cal.getMinimum(Calendar.MONTH));
            }

            dialogBegin = new Dialog(AddHolidaysPeriodsActivity.this);
            //dpd.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBegin.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBegin.setContentView(new DateDialog(AddHolidaysPeriodsActivity.this, null, cal, false, dialogBegin));
            dialogBegin.show();
        }
    };

    public void setBeginDate(Calendar calendar){
        this.beginDate = calendar;

        TextView tt = (TextView) findViewById(R.id.add_holiday_begin);
        tt.setText(CalendarUtils.getDateFormatted(calendar.getTime()));

    }



    public void addDate(View v){
        TextView firstOne = (TextView) findViewById(R.id.add_holiday_begin);
        TextView lastOne = (TextView) findViewById(R.id.add_holiday_end);


        if(control()) {
            //Periods verrà incapsulato in un oggetto che gli darà l'indice
            periods.add(new HolidayPeriod(CalendarUtils.getDateFromString(firstOne.getText().toString()), CalendarUtils.getDateFromString(lastOne.getText().toString())));
            populateTable();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Periodo non valido")
                    .setMessage("Le date devono definire un periodo differente da quelli già inseriti.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        lastOne.setText("");
        firstOne.setText("");

    }



    private void updateBeginLabel(){
        TextView item = (TextView) findViewById(R.id.add_holiday_begin);
        item.setText(CalendarUtils.getDateFormatted(beginDate.getTime()));
    }



    @Override
    public void onBackPressed() {
        return;
    }
}
