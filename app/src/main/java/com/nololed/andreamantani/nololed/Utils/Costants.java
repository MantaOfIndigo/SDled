package com.nololed.andreamantani.nololed.Utils;

import com.nololed.andreamantani.nololed.Model.SystemTec;

/**
 * Created by andreamantani on 08/05/16.
 */
public class Costants {

    private static double priceForEnergy = 0.22;
    private static double customPriceForEnergy = 0.00;

    private static SystemTec estimate;

    public static double getPriceForEnergy(){
        if(customPriceForEnergy == 0.00) {
            return priceForEnergy;
        }

        return customPriceForEnergy;
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
