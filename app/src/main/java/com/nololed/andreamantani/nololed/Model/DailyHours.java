package com.nololed.andreamantani.nololed.Model;

import android.util.Log;

import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andreamantani on 05/04/16.
 */
public class DailyHours {

    private Turn[] turns;
    private boolean enable;
    private boolean oneTurn;

    public DailyHours(){
        this.turns = new Turn[2];
        this.enable = true;
        this.oneTurn = false;
    }

    public DailyHours(DailyHours item){
        this.turns = new Turn[2];
        this.enable = item.getEnable();
        this.oneTurn = item.getOneTurn();

        this.turns = item.getTurnsList();
    }


    public boolean getOneTurn(){
        if(this.oneTurn){
            return true;
        }else {
            return false;
        }
    }
    public int getHoursNumber(){
        if(enable) {
            return this.turns[0].getHoursCount() + this.turns[1].getHoursCount();
        }

        return 0;

    }

    private void toggleEnable(){
        if(this.enable){
            this.enable = false;
        }else{
            this.enable = true;
        }
    }

    public void setEnable(boolean value){
        this.enable = value;
    }

    public void setAMTurn(Calendar beginTurn, Calendar endTurn){
        this.turns[0] = new Turn(beginTurn, endTurn);
    }

    public void setPMTurn(Calendar beginTurn, Calendar endTurn){
        this.turns[1] = new Turn(beginTurn, endTurn);
    }

    public void setOneTurn(Calendar beginTurn, Calendar endTurn){
        this.turns[0] = new Turn(beginTurn, endTurn);
        this.turns[1] = new Turn(0);
        this.oneTurn = true;
    }

    public void set24hTurn(){
        Calendar begin1 = CalendarUtils.newInitializedCalendar();
        Calendar end1 = CalendarUtils.newInitializedCalendar();

        begin1.set(Calendar.HOUR_OF_DAY, 0);
        end1.set(Calendar.HOUR_OF_DAY, 12);

        this.turns[0] = new Turn(begin1, end1);

        Calendar begin2 = CalendarUtils.newInitializedCalendar();
        Calendar end2 = CalendarUtils.newInitializedCalendar();

        begin2.set(Calendar.HOUR_OF_DAY, 12);
        end2.set(Calendar.HOUR_OF_DAY, 0);

        this.turns[1] = new Turn(begin2, end2);
    }

    public Turn[] getTurnsList(){
        return this.turns.clone();
    }

    public String toString(){

        if(this.oneTurn){
            return  turns[0].toString();
        }else if(turns[1].getHoursCount() == 0) {
            return  turns[0].toString();
        }else {
            return turns[0].toString() + " / " + turns[1].toString();
        }
    }

    public boolean getEnable(){
        if(this.enable){
            return true;
        }else{
            return false;
        }
    }
    public int getBeginAMHour(){
        Calendar returner = this.turns[0].getBegin();
        return returner.get(Calendar.HOUR_OF_DAY);
    }
    public int getBeginAMMinute(){
        Calendar returner = this.turns[0].getBegin();
        return returner.get(Calendar.MINUTE);
    }
    public int geteEndAMHour(){
        Calendar returner = this.turns[0].getEnd();
        return returner.get(Calendar.HOUR_OF_DAY);
    }
    public int getEndAMMinute(){
        Calendar returner = this.turns[0].getEnd();
        return returner.get(Calendar.MINUTE);
    }

    public int getBeginPMHour(){
        Calendar returner = this.turns[1].getBegin();
        return returner.get(Calendar.HOUR_OF_DAY);
    }
    public int getBeginPMMinute(){
        Calendar returner = this.turns[1].getBegin();
        return returner.get(Calendar.MINUTE);
    }

    public int getEndPMHour(){
        Calendar returner = this.turns[1].getEnd();
        return returner.get(Calendar.HOUR_OF_DAY);
    }
    public int getEndPMMinute(){
        Calendar returner = this.turns[1].getEnd();
        return returner.get(Calendar.MINUTE);
    }

    public Calendar getBeginAMCalendar(){
        return this.turns[0].begin;
    }

    public Calendar getEndAMCalendar(){
        return this.turns[0].end;
    }

    public Calendar getBeginPMCalendar(){
        return this.turns[1].begin;
    }

    public Calendar getEndPMCalendar(){
        return this.turns[1].end;
    }


}
