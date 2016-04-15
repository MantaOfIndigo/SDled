package com.nololed.andreamantani.nololed.Model.Records;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import org.w3c.dom.Attr;

import java.util.Date;

/**
 * Created by andreamantani on 11/04/16.
 */
public class SingleDayRecord extends LinearLayout {

    Date day;
    boolean enabled;

    public SingleDayRecord(Context context, AttributeSet attrs, Date date, OnClickListener toggleEnabled){
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.single_day_record, this);

        this.enabled = true;
        this.setOnClickListener(toggleEnabled);

        TextView txtdate = (TextView) findViewById(R.id.single_day_date);
        txtdate.setText(CalendarUtils.getDateFormatted(date));
    }

    public boolean getEnabled(){
        return this.enabled;
    }

    public void toggleEnable(){
        if(enabled){
            this.enabled = false;
            toggleOff();
        }else {
            this.enabled = true;
            toggleOn();
        }
    }

    private void toggleOff(){
        LinearLayout recordLayout = (LinearLayout) findViewById(R.id.single_day_layout);
        recordLayout.setAlpha((float) 0.3);
    }
    private void toggleOn(){
        LinearLayout recordLayout = (LinearLayout) findViewById(R.id.single_day_layout);
        recordLayout.setAlpha((float) 1);
    }
}
