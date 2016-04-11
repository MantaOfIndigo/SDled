package com.nololed.andreamantani.nololed.Model;


import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 15/03/16.
 */
public class Tecnology implements Serializable {

    private int qta;
    private Uri photo;
    private Information infos;
    private String desc;
    private int index;

    public Tecnology(){
        this.qta = 0;
        this.photo = null;
        this.infos = null;
        this.desc = "";
        this.index = -1;
    }
    public Tecnology(int qta, Uri photo, Information infos){
        this.qta = qta;
        this.photo = photo;
        this.infos = infos;
        this.desc = "";
        this.index = -1;
    }
    public Tecnology(int qta, Uri photo, Information infos, String desc){
        this.qta = qta;
        this.photo = Uri.parse(photo.getPath().replace(" ", ""));
        this.infos = infos;
        this.desc = replaceSpecialChars(desc);
        this.index = -1;
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
    public String getDesc(){ return backToSpecial(this.desc);}
    public int getIndex(){ return this.index; }

    public void setDesc(String value){
        this.desc = value;
    }
    public void setIndex(int i){ this.index = i;}

    public String toString(){
        String returner = "[";
        return returner += "url : " + this.photo.toString() + " , " + "qta : " + String.valueOf(this.qta) + " , " + "power : " + this.infos.getPowerString() + " , " + "hour : " + this.infos.getHourString() + " , " + "days : " + infos.getDaysForYearString() + " , " + "desc : " + this.desc + " , " + "index : " + this.index + "]";
    }

    public Tecnology DeserializeTecnology(String tecString){
        if (tecString.replace(",", "").replace(" ", "") == ""){
            return null;
        }


        String[] splitter2 = tecString.split(Pattern.quote(","));
        String[] results = new String[splitter2.length];
        for(int i = 0; i < splitter2.length; i++){
            if(i == 7){

            }else{
                String[] splitter3 = splitter2[i].split(Pattern.quote(":"));
                results[i] = splitter3[1];

                if(i == 5){
                    results[i] = results[i].substring(1,results[i].length()-1);
                }

                if (i == 1){
                    results[i] = results[i].replace(" ", "");
                }
            }

        }
        Log.v("tecnology", "res: " + results[0] + " " + results[1]+ " "+ results[2]+ " "+ results[3]+ " " + results[4]+ " "+ results[5] + " " + results[6]);
        Tecnology newItem = new Tecnology(Integer.parseInt(results[1]), Uri.parse(results[0]), new Information(results[2], results[3], results[4]), results[5]);
        String[] splitter4 = results[6].split(Pattern.quote("]"));
        newItem.setIndex(Integer.parseInt(splitter4[0].replace(" ", "")));
        return newItem;
    }
}

