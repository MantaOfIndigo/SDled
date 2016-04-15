package com.nololed.andreamantani.nololed.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Manta on 12/04/2016.
 */
public class Algorithm {

    public static List<Date> addOrdinateDateToList(Date date, List<Date> list){
        List<Date> newList = new ArrayList<>();

        if(date.equals(CalendarDates.getChristmas().getTime())){
            return list;
        }

        if(date.equals(CalendarDates.getFirstOfYear().getTime())){
            return list;
        }

        if(date.equals(CalendarDates.getCurrentYearEaster().getTime())){
            return list;
        }

        if(list.size() == 0){
            list.add(date);
            return list;
        }

        if(list.get(list.size()-1).before(date)){
            list.add(date);
            return list;
        }

        for(int i = 0; i < list.size(); i++){
            if(date != null) {
                if(list.get(i).equals(date)){
                    return list;
                }
                if (list.get(i).after(date)) {
                    newList.add(date);
                    date = null;
                }
            }
            newList.add(list.get(i));
        }
        return newList;
    }
}
