package com.nololed.andreamantani.nololed.Model;


public class Information {

    private int power;
    private int hour;
    private int daysForYear;

    public Information(){
        power = 0;
        hour = 0;
        daysForYear = 0;
    }
    public Information(String power, String hour, String daysForYear){
        try {
            this.power = Integer.parseInt(power.replace(" ",""));
        }catch (Exception e){
            this.power = -1;
        }
        try {
            this.hour = Integer.parseInt(hour.replace(" ", ""));
        }catch (Exception e){
            this.hour = -1;
        }
        try {
            this.daysForYear = Integer.parseInt(daysForYear.replace(" ", ""));
        }catch (Exception e){
            this.daysForYear = -1;
        }
    }

    public int getPower(){
        return this.power;
    }
    public int getHour(){
        return this.hour;
    }
    public int getDaysForYear(){
        return this.daysForYear;
    }

    public String getPowerString(){
        if (this.power == -1){
            return "";
        }
        return String.valueOf(this.power);
    }
    public String getHourString(){

        if(this.hour == -1){
            return "";
        }
        return String.valueOf(this.hour);
    }
    public String getDaysForYearString(){
        if(this.daysForYear == -1){
            return "";
        }
        return String.valueOf(this.daysForYear);
    }
}
