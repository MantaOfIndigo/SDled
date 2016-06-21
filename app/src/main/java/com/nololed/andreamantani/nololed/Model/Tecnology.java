package com.nololed.andreamantani.nololed.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 15/03/16.
 */
public class Tecnology implements Serializable {

    private int qta;
    private Uri photo;
    private Information infos;
    private String location;
    private int index;
    private int usageDaysInYear;
    private int usageHourInYear;

    public Tecnology(){
        this.qta = 0;
        this.photo = null;
        this.infos = null;
        this.location = "";
        this.index = -1;
    }
    public Tecnology(int qta, Uri photo, Information infos){
        this.qta = qta;
        this.photo = photo;
        this.infos = infos;
        this.location = "";
        this.index = -1;
        this.usageDaysInYear = -1;
        this.usageHourInYear = -1;
    }
    public Tecnology(int qta, Uri photo, Information infos, String location){
        this.qta = qta;
        this.photo = Uri.parse(photo.getPath().replace(" ", ""));
        this.infos = infos;
        this.location = replaceSpecialChars(location);
        this.index = -1;
        this.usageDaysInYear = -1;
        this.usageHourInYear = -1;
    }

    public Tecnology(int qta, Uri photo, Information infos, String location, int daysInYear, int hourInYear){
        this.qta = qta;
        this.photo = Uri.parse(photo.getPath().replace(" ", ""));
        this.infos = infos;
        this.location = replaceSpecialChars(location);
        this.index = -1;
        this.usageDaysInYear = daysInYear;
        this.usageHourInYear = hourInYear;
    }

    public String getPreviewPhotoPath(){
        File folder = new File(this.photo.getPath());

        if (folder.isDirectory()) {
            for (File child : folder.listFiles()) {
                return child.getAbsolutePath();
            }
        }

        return "";

    }

    private String replaceSpecialChars(String value){
        String returner = value.replace(",", "COMMA5");
        returner = returner.replace("{", "GRAPHO");
        returner = returner.replace("}", "GRAPHC");
        returner = returner.replace("[","SQUAREO");
        returner = returner.replace("]", "SQUAREC");
        return returner;
    }

    private String backToSpecial(String value){
        String returner = value.replace("COMMA5", ",");
        returner = returner.replace("GRAPHO", "{");
        returner = returner.replace("GRAPHC", "}");
        returner = returner.replace("SQUAREO", "[");
        returner = returner.replace("SQUAREC", "]");
        return returner;
    }

    public int getQta(){
        return this.qta;
    }
    public Uri getPhoto(){
        return this.photo;
    }
    public Information getInfos(){
        return this.infos;
    }
    public String getLocation(){ return backToSpecial(this.location);}
    public int getIndex(){ return this.index; }
    public int getUsageDaysInYear(){
        return this.usageDaysInYear;
    }
    public int getUsageHourInYear(){
        return this.usageHourInYear;
    }

    public int getUsageHourForPrice(){
        int result = 0;

        if(this.usageDaysInYear > 0){
            double daysWithoutRest = usageDaysInYear - (usageDaysInYear % 7);
            double daysNoRest = daysWithoutRest / 7;

            result = (int) (usageHourInYear * daysNoRest);

        }else{
            result = -1;
        }

        return result;
    }

    public void setLocation(String value){
        this.location = value;
    }
    public void setUsageDaysInYear(int value) {this.usageDaysInYear = value;}
    public void setUsageHourInYear(int value) { this.usageHourInYear = value;}
    public void setIndex(int i){ this.index = i;}

    public String toString(){
        String returner = "[";
        return returner += "url : " + this.photo.toString() + " , " + "qta : " + String.valueOf(this.qta) + " , " + "model : " + this.infos.getModel() + " , " + "power : " + this.infos.getPowerString() + " , " + "tonality : " + this.infos.getTonalityString() + " , " + "location : " + this.location +  " , " + "hourInYear : " + this.usageHourInYear +  " , " + "daysInYear : " + this.usageDaysInYear + " , " + "index : " + this.index + "]";
    }

    /*
      * 0 - url photo string
      * 1 - qta int
      * 2 - model string
      * 3 - power string
      * 4 - tonality string
      * 5 - locazione string
      * 6 - ore all'anno int
      * 7 - giorni all'anno int
      * 8 - indice
      * */
    public Tecnology DeserializeTecnology(String tecString){
        if (tecString.replace(",", "").replace(" ", "") == ""){
            return null;
        }


        String[] splitter2 = tecString.split(Pattern.quote(","));
        String[] results = new String[splitter2.length];
        for(int i = 0; i < splitter2.length; i++){
                String[] splitter3 = splitter2[i].split(Pattern.quote(":"));
                if (splitter3.length == 2){
                    results[i] = splitter3[1];

                    if(i == 5 || i == 2){
                        results[i] = results[i].substring(1,results[i].length()-1);
                    }

                    if (i == 1 || i == 6 || i == 7){
                        results[i] = results[i].replace(" ", "");
                    }
                }
        }

      
        Log.v("tecnology", "res: " + results[0] + " " + results[1] + " " + results[2] + " " + results[3] + " " + results[4] + " " + results[5] + " " + results[6] + " " + results[7] + " " + results[8]);
        String[] splitter4 = results[8].split(Pattern.quote("]"));

        Tecnology newItem = new Tecnology(Integer.parseInt(results[1]), Uri.parse(results[0]), new Information(results[2], results[3], results[4]), results[5], Integer.parseInt(results[7]), Integer.parseInt(results[6]));

        newItem.setIndex(Integer.parseInt(splitter4[0].replace(" ", "")));
        return newItem;
    }
}

