package com.nololed.andreamantani.nololed.Model.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.AddHolidaysPeriodsActivity;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by andreamantani on 01/04/16.
 */
public class DateDialog extends LinearLayout {

    TextView daystxt;
    TextView monthtxt;
    DatePicker datePicker;
    boolean begin;
    Calendar c;
    Context contextParent;
    Dialog dialog;


    public DateDialog(Context context, AttributeSet attrs, Calendar calendar, boolean begin, Dialog dialog){
        super(context,attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_date, this);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        this.dialog = dialog;
        this.contextParent = context;
        this.c = calendar;

        daystxt = (TextView) findViewById(R.id.dialog_string_date_day);
        monthtxt = (TextView) findViewById(R.id.dialog_string_date_month);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.dialog_date_frame);

        this.begin = begin;
        Button btnConfirm = (Button) findViewById(R.id.dialog_date_ok);
        Button btnCancel = (Button) findViewById(R.id.dialog_date_null);

        btnConfirm.setOnClickListener(confirmDialogClickListener);
        btnCancel.setOnClickListener(cancelDialogClickListener);

        datePicker = (DatePicker) findViewById(R.id.dialog_datepicker_control);


        datePicker.init(mYear,mMonth,mDay,dataChangeListener);
        updateTitle(mMonth, mDay);

        if(!hideDateYear(frameLayout)){
            return;
        }
    }




        //frDatePicker.addView(obj.getDatePicker());

    private boolean hideDateYear(ViewGroup group) {
        for(int i = 0; i < group.getChildCount(); i++){
            DatePicker v = (DatePicker) group.getChildAt(i);
            ViewGroup v2 = v;
            if(v.getChildCount() > 0){
                for(int j = 0; j < v2.getChildCount();j++) {

                    ViewGroup v3 = (ViewGroup) v2.getChildAt(j);
                    ViewGroup pickers = (ViewGroup) v3.getChildAt(0);
                    CalendarView calendarView = (CalendarView) v3.getChildAt(1);
                    v3.removeAllViews();

                    FrameLayout frameCalendarLayout = (FrameLayout) findViewById(R.id.dialog_calendar_frame);

                    Calendar maxSetter = CalendarUtils.newInitializedCalendar();
                    Calendar minSetter = CalendarUtils.newInitializedCalendar();

                    if(this.begin){
                        minSetter.set(Calendar.MONTH, minSetter.getMinimum(Calendar.MONTH));
                        minSetter.set(Calendar.DAY_OF_MONTH, minSetter.getMinimum(Calendar.DAY_OF_MONTH));

                        maxSetter.set(Calendar.MONTH, c.get(Calendar.MONTH));
                        maxSetter.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);

                        if(CalendarUtils.daysBetween(minSetter.getTime(),maxSetter.getTime()) < 20){
                            minSetter.set(Calendar.MONTH, minSetter.getMaximum(Calendar.MONTH));
                            minSetter.set(Calendar.YEAR, minSetter.get(Calendar.YEAR) - 1);
                            calendarView.setMinDate(minSetter.getTimeInMillis());
                            calendarView.setMaxDate(maxSetter.getTimeInMillis());
                        }else{
                            calendarView.setMinDate(minSetter.getTimeInMillis());
                            calendarView.setMaxDate(maxSetter.getTimeInMillis());
                        }

                        if(controlCurrentDate(minSetter, maxSetter)){
                            calendarView.setDate(CalendarUtils.newInitializedCalendar().getTime().getTime());
                        }else{
                            maxSetter.add(Calendar.DAY_OF_YEAR, -1);
                            calendarView.setDate(maxSetter.getTime().getTime());
                        }


                    } else {
                        minSetter.set(Calendar.MONTH, c.get(Calendar.MONTH));
                        minSetter.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
                        minSetter.add(Calendar.DAY_OF_MONTH, 1);

                        maxSetter.set(Calendar.MONTH, maxSetter.getMaximum(Calendar.MONTH));
                        maxSetter.set(Calendar.DAY_OF_MONTH, maxSetter.getMaximum(Calendar.DAY_OF_MONTH));

                        if(CalendarUtils.daysBetween(minSetter.getTime(), maxSetter.getTime()) < 20){
                            maxSetter.set(Calendar.MONTH, maxSetter.getMinimum(Calendar.MONTH));
                            maxSetter.set(Calendar.YEAR, maxSetter.get(Calendar.YEAR) + 1);
                            calendarView.setMinDate(minSetter.getTimeInMillis());
                            calendarView.setMaxDate(maxSetter.getTimeInMillis());
                        } else {
                            calendarView.setMaxDate(maxSetter.getTimeInMillis());
                            calendarView.setMinDate(minSetter.getTimeInMillis());
                        }

                        if(controlCurrentDate(minSetter, maxSetter)){
                            calendarView.setDate(CalendarUtils.newInitializedCalendar().getTime().getTime());
                        }else{
                            minSetter.add(Calendar.DAY_OF_YEAR, 1);
                            calendarView.setDate(minSetter.getTime().getTime());
                        }
                    }

                    frameCalendarLayout.addView(calendarView);

                    return true;
                }
            }
        }

        return false;
    }

    private void updateTitle(int monthOfYear, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//       mCalendar.set(Calendar.DAY_OF_MONTH, day);


        daystxt.setText(String.valueOf(dayOfMonth));

        String monthFormat = CalendarUtils.getFormatMonts().format(mCalendar.getTime()).toLowerCase();
        monthtxt.setText(monthFormat.substring(0,1).toUpperCase() + monthFormat.substring(1).toLowerCase());

    }



    private View.OnClickListener confirmDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(begin){
                c = getDate();
                TextView d = (TextView) ((AddHolidaysPeriodsActivity) contextParent).findViewById(R.id.add_holiday_begin);
                d.setText(CalendarUtils.getDateFormatted(c.getTime()));
            }else {
                c = getDate();
                TextView d = (TextView) ((AddHolidaysPeriodsActivity) contextParent).findViewById(R.id.add_holiday_end);
                d.setText(CalendarUtils.getDateFormatted(c.getTime()));
            }

            dialog.dismiss();
        }
    };

    private View.OnClickListener cancelDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    private boolean controlCurrentDate(Calendar min, Calendar max){
        Calendar current = CalendarUtils.newInitializedCalendar();

        if(current.getTime().before(min.getTime())){
            return false;
        }

        if(current.getTime().after(max.getTime())){
            return false;
        }

        return true;

    }


    private Calendar getDate(){
        Calendar returner = Calendar.getInstance();
        returner.set(Calendar.YEAR, datePicker.getYear());
        returner.set(Calendar.MONTH, datePicker.getMonth());
        returner.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

        if(returner.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)){
            returner.set(Calendar.MONTH, returner.getMinimum(Calendar.MONTH));
            returner.set(Calendar.DAY_OF_MONTH, returner.getMinimum(Calendar.DAY_OF_MONTH));
            returner.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        }

        if(returner.get(Calendar.YEAR) > Calendar.getInstance().get(Calendar.YEAR)){
            returner.set(Calendar.MONTH, returner.getMaximum(Calendar.MONTH));
            returner.set(Calendar.DAY_OF_MONTH, returner.getMaximum(Calendar.DAY_OF_MONTH));
            returner.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        }
        return returner;
    }
    private DatePicker.OnDateChangedListener dataChangeListener = new DatePicker.OnDateChangedListener(){

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            updateTitle(monthOfYear,dayOfMonth);
        }
    };


    @Override
    public String toString(){
        return "ciao";
    }
}
