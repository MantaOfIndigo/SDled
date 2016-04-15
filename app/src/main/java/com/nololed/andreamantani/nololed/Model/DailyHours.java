package com.nololed.andreamantani.nololed.Model;

import com.nololed.andreamantani.nololed.Utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andreamantani on 05/04/16.
 */
public class DailyHours {

    private Turn[] turns;

    public DailyHours(){
        this.turns = new Turn[2];
    }


    public void setAMTurn(Calendar beginTurn, Calendar endTurn){
        this.turns[0] = new Turn(beginTurn, endTurn);
    }

    public void setPMTurn(Calendar beginTurn, Calendar endTurn){
        this.turns[1] = new Turn(beginTurn, endTurn);
    }

    public Turn[] getTurnsList(){
        return this.turns;
    }

    public String toString(){
        return turns[0].toString() + " / " + turns[1].toString();
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

    public int geteEndPMHour(){
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
