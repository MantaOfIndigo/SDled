package com.nololed.andreamantani.nololed.Model;


import android.net.Uri;

import com.nololed.andreamantani.nololed.Utils.DatabaseDataManager;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

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
    private double maintenanceCost;
    private int usageDaysInYear;
    private int[] usageHourInWeek;
    private double tecnologyCost;

    private int accurateHourOfYearUsage = 0;

    public Tecnology(){
        this.qta = 0;
        this.photo = null;
        this.infos = null;
        this.location = "";
        this.index = -1;
        this.maintenanceCost = -1;
    }
    public Tecnology(int qta, Uri photo, Information infos){
        this.qta = qta;
        this.photo = photo;
        this.infos = infos;
        this.location = "";
        this.index = -1;
        this.usageDaysInYear = -1;
        this.usageHourInWeek = null;
        this.maintenanceCost = DatabaseDataManager.getMaintenanceCost(this.infos.getModel());
    }
    public Tecnology(int qta, Uri photo, Information infos, String location){
        this.qta = qta;
        this.photo = Uri.parse(photo.getPath().replace(" ", ""));
        this.infos = infos;
        this.location = replaceSpecialChars(location);
        this.index = -1;
        this.usageDaysInYear = -1;
        this.usageHourInWeek = null;
        this.maintenanceCost = DatabaseDataManager.getMaintenanceCost(this.infos.getModel());
    }



    public Tecnology(int qta, Uri photo, Information infos, String location, int daysInYear, int[] hourInYear){
        this.qta = qta;
        this.photo = Uri.parse(photo.getPath().replace(" ", ""));
        this.infos = infos;
        this.location = replaceSpecialChars(location);
        this.index = -1;
        this.usageDaysInYear = daysInYear;
        this.usageHourInWeek = hourInYear;
        this.maintenanceCost = DatabaseDataManager.getMaintenanceCost(this.infos.getModel());
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
    public int[] getUsageHourInWeek(){
        return this.usageHourInWeek;
    }

    public int getUsageHourForPrice(){

        if(this.accurateHourOfYearUsage == 0){
            this.accurateHourOfYearUsage = StandardWorkHours.calculateAccurateHoursInYear(this.usageHourInWeek);
        }

        return this.accurateHourOfYearUsage;
    }

    public void setLocation(String value){
        this.location = value;
    }
    public void setUsageDaysInYear(int value) {this.usageDaysInYear = value;}
    public void setUsageHourInWeek(int[] value) { this.usageHourInWeek = value;}
    public void setIndex(int i){ this.index = i;}
    private void setAccurateHourOfYearUsage(int value){
        this.accurateHourOfYearUsage = value;
    }

    public double getMaintenanceCost(){
        return this.maintenanceCost;
    }

    public int getUsageHourInWeekSum(){
        int count = 0;
        for(int i = 0; i < this.usageHourInWeek.length; i++){
            count += this.usageHourInWeek[i];
        }

        return count;
    }
    public void setMaintenanceCost(double value){
        this.maintenanceCost = value;
    }

    public String toString(){
        String returner = "[ url : " + this.photo.toString() +
                " , qta : " + String.valueOf(this.qta) +
                " , model : " + this.infos.getModel() +
                " , power : " + this.infos.getPowerString() +
                " , tonality : " + this.infos.getTonalityString() +
                " , location : " + this.location +
                " , hoursInWeek :";

        for(int i = 0; i < this.usageHourInWeek.length;i++){
            returner += " " + i + " = " + this.usageHourInWeek[i] + " # ";
        }


        returner +=  " , daysInYear : " + this.usageDaysInYear +
                " , index : " + this.index +
                " , maintenance : " + this.maintenanceCost +
                ", hourInYear : " + this.accurateHourOfYearUsage + "]";

        return returner;
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

        tecString = tecString.replace("[", "");
        tecString = tecString.replace("]", "");


        String[] splitter2 = tecString.split(Pattern.quote(","));
        /*String[] results = new String[splitter2.length];
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
        }*/

        Uri desURL = Uri.parse(splitter2[0].split(Pattern.quote(":"))[1].replace(" ", ""));
        int desQta = Integer.parseInt(splitter2[1].split(Pattern.quote(":"))[1].replace(" ", ""));

        String desModel = splitter2[2].split(Pattern.quote(":"))[1];
        desModel = desModel.substring(1,desModel.length() - 1);

        String desPower = splitter2[3].split(Pattern.quote(":"))[1].replace(" ", "");
        String desTonality = splitter2[4].split(Pattern.quote(":"))[1].replace(" ", "");

        String desLocation = splitter2[5].split(Pattern.quote(":"))[1];
        desLocation = desLocation.substring(1, desLocation.length() - 1);

        String desHourInYearArray = splitter2[6].split(Pattern.quote(":"))[1];
        String[] daysPerDaySplitter =  desHourInYearArray.split(Pattern.quote("#"));
        int[] desDaysPerDaysHours = new int[daysPerDaySplitter.length];
        // lunghezza meno uno dato che l'ultima locazione è sempre vuota
        for(int i = 0; i < daysPerDaySplitter.length - 1; i++){
            desDaysPerDaysHours[i] = Integer.parseInt(daysPerDaySplitter[i].split(Pattern.quote("="))[1].replace(" ", ""));
        }


        int desDaysInYear = Integer.parseInt(splitter2[7].split(Pattern.quote(":"))[1].replace(" ", ""));
        int desIndex = Integer.parseInt(splitter2[8].split(Pattern.quote(":"))[1].replace(" ", ""));
        double desMaintenanceCost = Double.parseDouble(splitter2[9].split(Pattern.quote(":"))[1].replace(" ", ""));
        int desAccurateHourInYear = Integer.parseInt(splitter2[10].split(Pattern.quote(":"))[1].replace(" ", ""));


        Tecnology newItem = new Tecnology(desQta, desURL, new Information(desModel,desPower,desTonality), desLocation, desDaysInYear, desDaysPerDaysHours);

        newItem.setIndex(desIndex);
        newItem.setAccurateHourOfYearUsage(desAccurateHourInYear);
        newItem.setMaintenanceCost(desMaintenanceCost);
        return newItem;
    }

    public double calculateMaintenanceCost(){
        TecnologyModel item = DatabaseDataManager.getObjectFromName(this.getInfos().getModel());
        if(this.maintenanceCost == 0){
            this.maintenanceCost = item.getTecnologyCost();
        }

        double priceOfTecnology = this.maintenanceCost;
        int hourLasting = item.getTecnologyLasting();

        int hourUsed = this.getUsageHourForPrice();

        double yearsOfLasting = (double) hourLasting / (double) hourUsed;
        double yearlyMaintenanceCost = priceOfTecnology / yearsOfLasting;

        return yearlyMaintenanceCost * this.getQta();
    }
}

