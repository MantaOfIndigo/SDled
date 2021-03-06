package com.nololed.andreamantani.nololed.Utils;

import com.nololed.andreamantani.nololed.Model.DailyHours;
import com.nololed.andreamantani.nololed.Model.SystemTec;

/**
 * Created by andreamantani on 08/05/16.
 */
public class Constants {

    private static double priceForEnergy = 0.22;
    private static double customPriceForEnergy = 0.00;
    private static long byteMailLimit = (17 * 1024);

    private static boolean[] dayStandard = new boolean[7];
    public static int daysInYearStandard;
    public static int hourInWeek;

    private static SystemTec estimate;

    public static void setDayStandard(Boolean value, int index){
        dayStandard[index] = value;
    }

    public static boolean[] getDayStandard(){
        return dayStandard.clone();
    }

    public static double getPriceForEnergy(){
        if(customPriceForEnergy == 0.00) {
            return priceForEnergy;
        }

        return customPriceForEnergy;
    }



    public static long getByteMailLimit(){
        return byteMailLimit;
    }
    public static void setPriceForEnergy(double value){
        customPriceForEnergy = value;
    }

    public static void setEstimate(SystemTec value){
        estimate = value;
    }

    public static SystemTec getEstimate(){
        return estimate;
    }
}
