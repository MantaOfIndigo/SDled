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
import android.widget.Toast;

import com.nololed.andreamantani.nololed.AddHolidaysPeriodsActivity;
import com.nololed.andreamantani.nololed.AddSingleHolidays;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.util.Calendar;

/**
 * Created by Manta on 12/04/2016.
 */
public class SingleDateDialog extends LinearLayout {

    DatePicker datePicker;
    Context contextParent;
    Dialog dialogReference;

    public SingleDateDialog(Context context, AttributeSet attrs, Dialog dialogReference){
        super(context, attrs);

        this.contextParent = context;
        this.dialogReference = dialogReference;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_date, this);

        Button confirmBtn = (Button) findViewById(R.id.dialog_date_ok);
        Button cancelBtn = (Button) findViewById(R.id.dialog_date_null);

        confirmBtn.setOnClickListener(confirmDialogClickListener);
        cancelBtn.setOnClickListener(cancelDialogClickListener);

        confirmBtn.setText(("aggiungi").toUpperCase());
        cancelBtn.setText(("termina").toUpperCase());

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.dialog_date_frame);
        hideDateYear(frameLayout);

        Calendar setter = CalendarUtils.newInitializedCalendar();

        updateTitle(setter.get(Calendar.MONTH), setter.get(Calendar.DAY_OF_MONTH));

        this.datePicker = (DatePicker) findViewById(R.id.dialog_datepicker_control);
        this.datePicker.init(setter.get(Calendar.YEAR), setter.get(Calendar.MONTH), setter.get(Calendar.DAY_OF_MONTH), dataChangeListener);
    }

    private Calendar getDate(){
        Calendar returner = Calendar.getInstance();
        returner.set(Calendar.YEAR, datePicker.getYear());
        returner.set(Calendar.MONTH, datePicker.getMonth());
        returner.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());


        returner.set(Calendar.HOUR, 0);
        returner.set(Calendar.MINUTE, 0);
        returner.set(Calendar.SECOND, 0);
        returner.set(Calendar.MILLISECOND, 0);

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

                    Calendar minDate = CalendarUtils.newInitializedCalendar();
                    Calendar maxDate = CalendarUtils.newInitializedCalendar();

                    minDate.set(Calendar.MONTH, minDate.getMinimum(Calendar.MONTH));
                    minDate.set(Calendar.DAY_OF_MONTH, minDate.getMinimum(Calendar.DAY_OF_MONTH));

                    maxDate.set(Calendar.MONTH, maxDate.getMaximum(Calendar.MONTH));
                    maxDate.set(Calendar.DAY_OF_MONTH, maxDate.getMaximum(Calendar.DAY_OF_MONTH));

                    calendarView.setMinDate(minDate.getTimeInMillis());
                    calendarView.setMaxDate(maxDate.getTimeInMillis());

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

        TextView daystxt = (TextView) findViewById(R.id.dialog_string_date_day);
        TextView monthtxt = (TextView) findViewById(R.id.dialog_string_date_month);

        daystxt.setText(String.valueOf(dayOfMonth));

        String monthFormat = CalendarUtils.getFormatMonts().format(mCalendar.getTime()).toLowerCase();
        monthtxt.setText(monthFormat.substring(0,1).toUpperCase() + monthFormat.substring(1).toLowerCase());

    }

    private DatePicker.OnDateChangedListener dataChangeListener = new DatePicker.OnDateChangedListener(){

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            updateTitle(monthOfYear,dayOfMonth);
        }
    };

    private View.OnClickListener confirmDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar dateSelected = CalendarUtils.newInitializedCalendar();

            dateSelected = getDate();
            ((AddSingleHolidays) contextParent).setNewDateRecord(dateSelected.getTime());
        }
    };

    private View.OnClickListener cancelDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((AddSingleHolidays) contextParent).resumeActivity();
            dialogReference.dismiss();
        }
    };


}
