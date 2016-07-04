package com.nololed.andreamantani.nololed.Model.Dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nololed.andreamantani.nololed.FontClasses.CustomButtonFont;
import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;
import com.nololed.andreamantani.nololed.Utils.DailyTimePickerDialog;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

import java.util.Calendar;

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

    CheckBox oneTurnchk;
    CheckBox allDaychk;

    DailyHours myStandard;

    boolean hourConfirmed = false;
    boolean oneTurn = false;
    boolean allDay = false;

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


        myStandard = new DailyHours(StandardWorkHours.getDayStandard(dayName));

        CustomButtonFont okButton = (CustomButtonFont) findViewById(R.id.dialog_date_ok);
        CustomButtonFont cancelButton = (CustomButtonFont) findViewById(R.id.dialog_date_null);

        oneTurnchk = (CheckBox) findViewById(R.id.daily_hour_one_turn);
        allDaychk = (CheckBox) findViewById(R.id.daily_hour_24);


        allDaychk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    allDay = true;
                    oneTurn = false;

                    oneTurnchk.setChecked(false);

                    setHourSelector(0,0,0);
                    setHourSelector(12,0,1);
                    setHourSelector(12,0,2);
                    setHourSelector(0,0,3);


                    beginAM.setEnabled(false);
                    endAM.setEnabled(false);
                    beginPM.setEnabled(false);
                    endAM.setEnabled(false);

                    checkDates();


                }else {


                    beginAM.setEnabled(true);
                    endAM.setEnabled(true);
                    beginPM.setEnabled(true);
                    endAM.setEnabled(true);

                    allDay = false;

                    setHourSelector(myStandard.getBeginAMHour(), myStandard.getBeginAMMinute(),0);
                    setHourSelector(myStandard.geteEndAMHour(), myStandard.getEndAMMinute(),1);
                    setHourSelector(myStandard.getBeginPMHour(), myStandard.getBeginPMMinute(),2);
                    setHourSelector(myStandard.getEndPMHour(), myStandard.getEndPMMinute(),3);


                    checkDates();

                }
            }
        });



        oneTurnchk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    oneTurn = true;
                    allDay = false;

                    allDaychk.setChecked(false);

                    endAM.setBackgroundResource(R.color.custom_disable_grey);
                    endAM.setEnabled(false);
                    beginPM.setBackgroundResource(R.color.custom_disable_grey);
                    beginPM.setEnabled(false);


                    checkDates();
                }else{
                    oneTurn = false;

                    setHourSelector(myStandard.getBeginAMHour(), myStandard.getBeginAMMinute(),0);
                    setHourSelector(myStandard.geteEndAMHour(), myStandard.getEndAMMinute(),1);
                    setHourSelector(myStandard.getBeginPMHour(), myStandard.getBeginPMMinute(),2);
                    setHourSelector(myStandard.getEndPMHour(), myStandard.getEndPMMinute(),3);

                    endAM.setBackgroundResource(R.color.custom_bright_blue);
                    endAM.setEnabled(false);
                    beginPM.setBackgroundResource(R.color.custom_bright_blue);
                    beginPM.setEnabled(false);


                    checkDates();
                }
            }
        });



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

        initializeHoursSelector();




    }

    private void setHourSelector(int hour, int minute, int index){
        final TextView beginAMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginam);
        final TextView endAMtxt = (TextView) findViewById(R.id.daily_hour_txt_endam);
        final TextView beginPMtxt = (TextView) findViewById(R.id.daily_hour_txt_beginpm);
        final TextView endPMtxt = (TextView) findViewById(R.id.daily_hour_txt_endpm);

        if(index == 0){
            bAMCalendar.set(Calendar.HOUR_OF_DAY, hour);
            bAMCalendar.set(Calendar.MINUTE, minute);
            beginAMtxt.setText(CalendarUtils.getTimeFormatted(hour,minute));
        }

        if(index == 1){
            eAMCalendar.set(Calendar.HOUR_OF_DAY, hour);
            eAMCalendar.set(Calendar.MINUTE, minute);
            endAMtxt.setText(CalendarUtils.getTimeFormatted(hour,minute));
        }

        if(index == 2){
            bPMCalendar.set(Calendar.HOUR_OF_DAY, hour);
            bPMCalendar.set(Calendar.MINUTE, minute);
            beginPMtxt.setText(CalendarUtils.getTimeFormatted(hour,minute));
        }

        if(index == 3){
            ePMCalendar.set(Calendar.HOUR_OF_DAY, hour);
            ePMCalendar.set(Calendar.MINUTE, minute);
            endPMtxt.setText(CalendarUtils.getTimeFormatted(hour,minute));
        }



    }


    private void initializeHoursSelectorFor(){}
    private void initializeHoursSelector(){

        //workHours = new DailyHours();

        bAMCalendar = (Calendar) myStandard.getBeginAMCalendar().clone();
        eAMCalendar = (Calendar) myStandard.getEndAMCalendar().clone();
        bPMCalendar = (Calendar) myStandard.getBeginPMCalendar().clone();
        ePMCalendar = (Calendar) myStandard.getEndPMCalendar().clone();

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
                DailyTimePickerDialog time = new DailyTimePickerDialog(contextDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        beginAMtxt.setText(CalendarUtils.getTimeFormatted(hourOfDay, minute));
                        bAMCalendar.set(Calendar.HOUR, hourOfDay);
                        bAMCalendar.set(Calendar.MINUTE, minute);
                        checkDates();
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
                        eAMCalendar.set(Calendar.HOUR, hourOfDay);
                        eAMCalendar.set(Calendar.MINUTE, minute);
                        checkDates();
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
                        bPMCalendar.set(Calendar.HOUR, hourOfDay);
                        bPMCalendar.set(Calendar.MINUTE, minute);
                        checkDates();
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
                        ePMCalendar.set(Calendar.HOUR, hourOfDay);
                        ePMCalendar.set(Calendar.MINUTE, minute);
                        checkDates();
                    }
                }, myStandard.getEndPMHour(), myStandard.getEndPMMinute(), true, 13, 23);
                time.show();
            }
        });

    }

    private boolean checkAll4Hours(){
        boolean flag = true;

        int checker = 2;//valore che metodo non ritorna mai

        checker = CalendarUtils.checkHours(bAMCalendar, eAMCalendar);
        // 1 - il primo viene prima
        // 0 - sono uguali
        //-1 - il secondo viene prima

        if (checker == 1) {
            setUpRecordLayout(endAM, false);
        } else {
            setUpRecordLayout(endAM, true);
            flag = false;
        }

        checker = CalendarUtils.checkHours(bPMCalendar, ePMCalendar);

        if (checker == 1) {
            setUpRecordLayout(endPM, false);
        } else if(checker == -1){
            if(ePMCalendar.get(Calendar.HOUR) == 0){
                setUpRecordLayout(endPM, false);
            }else {
                setUpRecordLayout(endPM, true);
                flag = false;
            }
        }


        checker = CalendarUtils.checkHours(bAMCalendar, bPMCalendar);

        if (checker == 1) {
            setUpRecordLayout(beginPM, false);
        } else {
            setUpRecordLayout(beginPM, true);
            flag = false;
        }


        checker = CalendarUtils.checkHours(eAMCalendar, bPMCalendar);

        if (checker == 1) {
            setUpRecordLayout(beginPM, false);
        } else {
            setUpRecordLayout(beginPM, true);
            flag = false;
        }

        return flag;
    }

    private boolean checkDates() {
        boolean flag = true;

        if(oneTurn){

            int checker = CalendarUtils.checkHours(bAMCalendar, ePMCalendar);

            if (checker == 1) {
                setUpRecordLayout(endPM, false);
            } else if(checker == -1){
                if(ePMCalendar.get(Calendar.HOUR_OF_DAY) == 0 && ePMCalendar.get(Calendar.MINUTE) == 0){
                    setUpRecordLayout(endPM, false);
                }else {
                    setUpRecordLayout(endPM, true);
                    flag = false;
                }
            }

            if(checker == 0){
                setUpRecordLayout(endPM, true);
                flag = false;
            }

        }else if(allDay){
            setUpRecordLayout(beginAM, false);
            setUpRecordLayout(beginPM, false);
            setUpRecordLayout(endAM, false);
            setUpRecordLayout(endPM, false);
        } else {
            flag = checkAll4Hours();
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
            endPM.setBackgroundResource(R.color.custom_bright_blue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePM);
            tgBtn.setChecked(true);
            endPMtxt.setTextColor(Color.parseColor("#677a82"));
        }

        if(record == endAM && !error){
            endAM.setBackgroundResource(R.color.custom_bright_blue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_toggleAM);
            tgBtn.setChecked(true);
            endAMtxt.setTextColor(Color.parseColor("#677a82"));
        }

        if (record == beginPM && !error){
            beginPM.setBackgroundResource(R.color.custom_bright_blue);
            ToggleButton tgBtn = (ToggleButton) findViewById(R.id.daily_hour_togglePMB);
            tgBtn.setChecked(true);
            begPMtxt.setTextColor(Color.parseColor("#677a82"));
        }






    }

    public void saveHour() {
        checkDates();


        if (hourConfirmed || allDay) {


            DailyHours workHours = new DailyHours();

            if(allDay){
                workHours.set24hTurn();
            }else if(oneTurn){
                workHours.setOneTurn(bAMCalendar, ePMCalendar);
            }else {
                workHours.setAMTurn(bAMCalendar, eAMCalendar);
                workHours.setPMTurn(bPMCalendar, ePMCalendar);
            }


            StandardWorkHours.setCustomDailyHours(this.dayName, workHours);

            this.updateTable.onClick(this);
            this.currDialog.dismiss();
        }

    }
    public void back(){
        this.currDialog.dismiss();
    }
}
