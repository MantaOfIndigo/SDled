package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

import android.app.Application;

/**
 * Created by andreamantani on 09/06/16.
 */
public class LedTecTypeData extends Application{

    public static String[] ledNames = {
            "Lampada led 5 watt",
            "Lampada led 7 watt",
            "Lampada led 9 watt",
            "Lampada led 12 watt",
            "Tubo led T8 13 watt",
            "Tubo led T8 15 watt",
            "Tubo led T8 18 watt",
            "Tubo led T8 24 watt",
            "Reglette T5 led 4 watt",
            "Reglette T5 led 8 watt",
            "Reglette T5 led 10 watt",
            "Reglette T5 led 14 watt",
            "Reglette T5 led 18 watt",
            "Pannello led da incasso 600x600mm 38W",
            "Pannello led da parete 600x600mm 38W",
            "Kit accessori sospensione/incasso",
            "Downlight incasso/parete 10 watt",
            "Downlight incasso/parete 15 watt",
            "Downlight incasso/parete 30 watt",
            "Downlight incasso/parete 25 watt",
            "Downlight orientabile 22 watt",
            "Downlight orientabile 40 watt",
            "Downlight orientabile 60 watt",
            "Portafaretto bianco orientabile",
            "Lampada GU10 led 5 watt",
            "Lampada GU10 led 7 watt",
            "AR111 led 13 watt+alim.",
            "AR111 led 15 watt+alim.",
            "AR111 led 22 watt+alim.",
            "AR111 led 40 watt+alim. 24/42°",
            "Plafoniera stagna per tubi led 2x9",
            "Plafoniera stagna per tubi led 15 watt",
            "Plafoniera stagna per tubi led 12x5 watt",
            "Plafoniera stagna per tubi led 23 watt",
            "Plafoniera stagna per tubi led 2x23 watt",
            "Proiettore IP65 orientabile 80 watt",
            "Proiettore IP65 orientabile 160 watt",
            "Proiettore IP65 orientabile 200 watt",
            "Plafone ind IP65 160 watt",
            "Plafone ind IP65 200 watt"
    };

    public static String[] ledIds = {
            "LL5",
            "LL7",
            "LL9",
            "LL12",
            "TBL13",
            "TBL15",
            "TBL18",
            "TBL24",
            "RE4",
            "RE8",
            "RE10",
            "RE14",
            "RE18",
            "PAI38",
            "PAP38",
            "KITA",
            "DWLIGHT10",
            "DWLIGHT15",
            "DWLIGHT30",
            "DWLIGHT25",
            "DWLIGHTOR22",
            "DWLIGHTOR40",
            "DWLIGHTOR60",
            "PFARBIANOR",
            "GU5",
            "GU7",
            "AR13",
            "AR15",
            "AR22",
            "AR40",
            "PLAF29",
            "PLAF15",
            "PLAF215",
            "PLAF23",
            "PLAF223",
            "PROIP65OR80",
            "PROIP65OR160",
            "PROIP65OR200",
            "PLAFINDIP65160",
            "PLAFINDIP65200"
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
