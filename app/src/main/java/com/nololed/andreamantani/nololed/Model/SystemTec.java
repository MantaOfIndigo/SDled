package com.nololed.andreamantani.nololed.Model;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 15/03/16.
 */
public class SystemTec implements Serializable{
    private List<Tecnology> tecList;
    //private List<HolidayPeriod> periodList;
    private String name;
    public SystemTec(){
        this.name = "";
        this.tecList = new ArrayList<Tecnology>();
    }

    public SystemTec(String name){
        this.tecList = new ArrayList<Tecnology>();

        String returner = name.replace(",", "COMMA5");
        returner = returner.replace("{", "GRAPHO");
        returner = returner.replace("}", "GRAPHC");
        returner = returner.replace("[","SQUAREO");
        returner = returner.replace("]", "SQUAREC");

        this.name = returner;

    }

    public void addList(Tecnology newItem){
        if (newItem == null){
            return;
        }
        newItem.setIndex(this.tecList.size());
        this.tecList.add(newItem);
    }

    public void sendMail(AppCompatActivity from){
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"mantaniandrea@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preventivo " + replaceSpecialChars().toUpperCase());

        String mailText = "";
        for(Tecnology item : this.tecList){
            String[] splitter = item.getPhoto().getPath().split(Pattern.quote(File.separator));
            mailText += "Foto tecnologia: " + splitter[splitter.length-1] + "\n" + "Potenza: " + item.getInfos().getPowerString() + "\n" + "Ore al giorno: " + item.getInfos().getTonalityString() + "\n" + "Giorni all'anno: " + item.getInfos().getDaysForYearString() + "\n" + "Quantità: " + item.getQta() + "\n" + "Descrizione : " + item.getLocation() + "\n";
            mailText += "-------------------------------\n\n";
        }

        emailIntent.putExtra(Intent.EXTRA_TEXT, mailText);




        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (Tecnology item : this.tecList)
        {
            File fileIn = new File(item.getPhoto().getPath());
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        from.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private String replaceSpecialChars(){
        String ret = this.name.replace("DOT8", ".");
        ret = ret.replace("DOT7", "?");
        ret = ret.replace("_", " ");
        ret = ret.replace("DOT8", ".");
        ret = ret.replace("COMMA5", ",");
        ret = ret.replace("GRAPHO", "{");
        ret = ret.replace("GRAPHC", "}");
        ret = ret.replace("SQUAREO", "[");
        ret = ret.replace("SQUAREC", "]");
        return ret;
    }
    public String getName(){
        return replaceSpecialChars();
    }
    public String toString(){
        String returner = this.name + " { ";

        for(int i = 0; i < this.tecList.size();i++){
            if (i != 0){
                returner += " , ";
            }
            returner += tecList.get(i).toString() + " ";
        }

        return returner + " }";
    }

    public List<Tecnology> getList(){
        return this.tecList;
    }

    public SystemTec DeserializeSystem(String sysString){
        String[] splitter1 = sysString.split(Pattern.quote("{"));
        Log.v("cont", "spli1" + splitter1[1]);

        this.name = splitter1[0].replace(" ", "");

        String divide1 = splitter1[1].replace("}", "");
        String[] results = divide1.split(Pattern.quote("["));
        Log.v("cont", "splitterrr: " + divide1);

        for(int i = 1; i < results.length; i++){
            Log.v("cont", "splitterrr" + i + " : " + results[i]);
            this.addList(new Tecnology().DeserializeTecnology(results[i]));
        }


        //this.addList(new Tecnology().DeserializeTecnology(divide1));
        return this;
    }

    public boolean doesModelInSamePositionExists(String model, String location){
        for(int i = 0; i < this.tecList.size(); i++){
            if(location.equals(tecList.get(i).getLocation()) || location.replace(" ", "").equals(tecList.get(i).getLocation().replace(" ", ""))){
                if(model.equals(tecList.get(i).getInfos().getModel())){
                    return true;
                }
            }
        }
        return false;
    }
}
