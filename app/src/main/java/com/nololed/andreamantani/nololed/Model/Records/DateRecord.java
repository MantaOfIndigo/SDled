package com.nololed.andreamantani.nololed.Model.Records;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.Model.HolidayPeriod;
import com.nololed.andreamantani.nololed.R;
import com.nololed.andreamantani.nololed.Utils.Counter;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by andreamantani on 31/03/16.
 */
public class DateRecord extends LinearLayout {

    Date begin;
    Date end;
    HolidayPeriod period;
    FrameLayout delete;

    int index;

    public DateRecord(Context context, AttributeSet attrs, Date begin, Date end, OnClickListener delete, int index){
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.holi_record, this);

        period = new HolidayPeriod(begin, end, Counter.getHolidaysCounter());
        period.getDeserializeHolidayPeriod(period.toString());

        this.index = index;

        this.begin = begin;
        this.end = end;


        TextView beginTxt = (TextView) findViewById(R.id.record_holi_begin);
        TextView endTxt = (TextView) findViewById(R.id.record_holi_end);

        beginTxt.setText(period.getBeginString());
        endTxt.setText(period.getEndString());

        this.delete = (FrameLayout) findViewById(R.id.record_holi_delete);
        this.delete.setOnClickListener(delete);

    }

    public FrameLayout getDeleteLayout(){
        return this.delete;
    }
    public Date getBegin(){
        return this.begin;
    }
    public Date getEnd(){
        return this.end;
    }
    public int getIndex(){ return  this.index; }
}


