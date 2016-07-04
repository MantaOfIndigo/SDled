package com.nololed.andreamantani.nololed.Model.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.nololed.andreamantani.nololed.DailyWorkhoursActivity;
import com.nololed.andreamantani.nololed.FontClasses.CustomButtonFont;
import com.nololed.andreamantani.nololed.Model.Records.DayRecord;
import com.nololed.andreamantani.nololed.ProfileTecnologyActivity;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.Constants;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;
import com.nololed.andreamantani.nololed.WeekSetUpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 17/04/16.
 */
public class WeekDialog extends LinearLayout {

    LinearLayout scroll;
    Context contextDialog;
    Dialog currDialog;
    Context backContext;



    List<DayRecord> recordController;

    public WeekDialog(Context context, AttributeSet attrs, Dialog dialog){
        super(context, attrs);

        this.backContext = context;
        this.contextDialog = dialog.getContext();
        this.currDialog = dialog;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_week, this);

        CustomButtonFont okBtn = (CustomButtonFont) findViewById(R.id.dialog_date_ok);
        CustomButtonFont cancelBtn = (CustomButtonFont) findViewById(R.id.dialog_date_null);

        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHours();
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        StandardWorkHours.setCustomStandards();
        recordController = new ArrayList<>();
        populateScroll();





    }

    private void populateScroll(){
        scroll = (LinearLayout) findViewById(R.id.weekly_scroll_layout);
        String[] weekName = new String[]{"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};

        boolean[] daysEnabled = Constants.getDayStandard();

        for(int i = 0; i < weekName.length; i++){
            DayRecord newItem = new DayRecord(contextDialog, null, weekName[i], i, setHoursListener, true);

            if (!StandardWorkHours.getCustomDayStandard(weekName[i]).getEnable()) {
                newItem.setToggleButtonState(false);
            }

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
                    Dialog hour = new Dialog(contextDialog);
                    hour.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    hour.setContentView(new HoursDialog(contextDialog, null, hour, ((DayRecord) row).getDayName(), populateListener));

                    hour.show();
                }
            }
        }
    };

    private View.OnClickListener populateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scroll.removeAllViews();
            populateScroll();
        }
    };

    private void setEnableDays(){
        for(int i = 0; i < recordController.size(); i++){
            if(recordController.get(i).getToggleButtonState()){
                StandardWorkHours.setEnableCustomDay(recordController.get(i).getDayName(), true);
            }else{
                StandardWorkHours.setEnableCustomDay(recordController.get(i).getDayName(), false);
            }
        }
    }

    public void saveHours(){
        setEnableDays();

        ((ProfileTecnologyActivity) backContext).setHoursNumber(StandardWorkHours.getHoursNumber(true));
        ((ProfileTecnologyActivity) backContext).setDaysInYear();
        currDialog.dismiss();
    }
    public void back(){
        currDialog.dismiss();
    }
}
