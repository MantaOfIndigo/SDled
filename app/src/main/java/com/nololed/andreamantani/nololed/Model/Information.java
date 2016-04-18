package com.nololed.andreamantani.nololed.Model;


public class Information {

    private String model;
    private int power;
    private int tonality;
    private int daysForYear;

    public Information(){
        model = "";
        power = 0;
        tonality = 0;
        daysForYear = 0;
    }
    public Information(String model, String power, String tonality){

        this.model = model;

        try {
            this.power = Integer.parseInt(power.replace(" ",""));
        }catch (Exception e){
            this.power = -1;
        }
        try {
            this.tonality = Integer.parseInt(tonality.replace(" ", ""));
        }catch (Exception e){
            this.tonality = -1;
        }
        /*try {
            this.daysForYear = Integer.parseInt(daysForYear.replace(" ", ""));
        }catch (Exception e){
            this.daysForYear = -1;
        }*/
    }

    public String getModel(){ return this.model; }
    public int getPower(){
        return this.power;
    }
    public int getTonality(){
        return this.tonality;
    }
    public String getTonalityString(){
        return String.valueOf(this.tonality);
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
    public int getTonalityItem(){

        if(this.tonality == -1) {
            return this.tonality;
        }

        switch (this.tonality){
            case 3000:
                return 0;
            case 4000:
                return 1;
            case 6000:
                return 2;
            default:
                return -1;
        }

    }
    public String getDaysForYearString(){
        if(this.daysForYear == -1){
            return "";
        }
        return String.valueOf(this.daysForYear);
    }
}
