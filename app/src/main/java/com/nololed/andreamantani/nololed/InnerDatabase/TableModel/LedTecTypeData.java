package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

import android.app.Application;

/**
 * Created by andreamantani on 09/06/16.
 */
public class LedTecTypeData extends Application{

    public static String[] ledNames = {
            "Tuboled 13-watt",
            "Tuboled 15-watt",
            "Tuboled 18-watt",
            "Tuboled 24-watt",
            "Downlight",
            "Downlight orientabile",
            "Portafaretto bianco orientabile",
            "AR111",
            "Plafoniera + 1x9",
            "Plafoniera + 2x9",
            "Plafoniera + 1x15",
            "Plafoniera + 2x15",
            "Plafoniera + 1x24",
            "Plafoniera + 2x24",
            "Tipo disano plastica",
            "Proiettore IP65 orientabile",
            "Plafone ind IP65"
    };

    public static String[] ledIds = {
            "TBL13",
            "TBL15",
            "TBL18",
            "TBL24",
            "DWLIGHT",
            "DWLIGHTOR",
            "PFARBIANOR",
            "AR111",
            "PLAF19",
            "PLAF29",
            "PLAF115",
            "PLAF215",
            "PLAF124",
            "PLAF224",
            "DISANPLA",
            "PROIP65OR",
            "PLAFINDIP65"
    };

    public static String getLedNameFromID(String id){
        for(int i = 0; i < ledIds.length; i++){
            if(ledIds[i].equals(id)){
                return ledNames[i];
            }
        }

        return "";
    }
}
