package com.nololed.andreamantani.nololed.Model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nololed.andreamantani.nololed.PreviewEstimateActivity;
import com.nololed.andreamantani.nololed.Utils.Constants;
import com.nololed.andreamantani.nololed.Utils.DatabaseDataManager;
import com.nololed.andreamantani.nololed.Utils.GalleryItems;
import com.nololed.andreamantani.nololed.Utils.SolarYearHours;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import java.io.File;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
        returner = returner.replace("-", "COMMALINE9");
        returner = returner.replace("?", "DOT7");

        this.name = returner;

    }

    public void addList(Tecnology newItem){
        if (newItem == null){
            return;
        }
        newItem.setIndex(this.tecList.size());
        this.tecList.add(newItem);
    }


    public double calculatePrice(){
        double price = 0;

        for(int i = 0; i < this.tecList.size();i++){
            price += this.getPricePartial(this.tecList.get(i));
        }

        return price;


    }

    public List<Double> calculateLedPrices(){
        List<Double> prices = new ArrayList<>();

        for(int i = 0; i < this.tecList.size(); i++){
            TecnologyModel item = DatabaseDataManager.getObjectFromName(tecList.get(i).getInfos().getModel());
            int hour = 0;

            if(SolarYearHours.isSolarYear()){
                hour = SolarYearHours.getHourInYear();

            }else{
                hour = tecList.get(i).getUsageHourForPrice();
            }

            prices.add(getPartialPrice(item.ledPower, hour,tecList.get(i).getQta()/*, item.getPrice()*/));
        }

        return prices;
    }

    public double getTotalLedPrice(List<Double> prices){
        double total = 0;
        for(int i = 0; i < prices.size(); i++){
            total += prices.get(i);
        }

        return total;

    }

    private void setTecnologySolarDaysAndHours(){
        for(int i = 0; i < this.tecList.size() ; i++){
            int[] hoursInWeek = new int[1];
            hoursInWeek[0] = SolarYearHours.getHourInYear();
            this.tecList.get(i).setUsageHourInWeek(hoursInWeek);
            this.tecList.get(i).setUsageDaysInYear(SolarYearHours.getDayInYear());
        }
    }

    public void sendMail(AppCompatActivity from){


        DecimalFormat formatter = new DecimalFormat("#0.00");
        formatter.setRoundingMode(RoundingMode.FLOOR);

        if(SolarYearHours.isSolarYear()){
            setTecnologySolarDaysAndHours();
        }
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"tecnico@laluceanoleggio.com", "stefano.d@sdled.it"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preventivo " + replaceSpecialChars().toUpperCase());

        String mailText = "COSTO TOTALE ENERGIA IMPIANTO CORRENTE:     " + formatter.format(this.calculatePrice()) + "\n";
        mailText += "COSTO TOTALE ENERGIA IMPIANTO LED:     " + formatter.format(this.getTotalLedPrice(this.calculateLedPrices())) + "\n";
        mailText += "COSTO MANUTENZIONE:    " + formatter.format(this.calculateMaintenanceCost()) + "\n";
        mailText += "COSTO NOLEGGIO MENSILE:     " + formatter.format(this.getMonthlyRateForNolo()) + "\n";
        mailText += "COSTO NOLEGGIO ANNUALE:     " + formatter.format(this.getMonthlyRateForNolo() * 12)  + "\n\n";

        for(Tecnology item : this.tecList){
            String[] splitter = item.getPhoto().getPath().split(Pattern.quote(File.separator));
            mailText += "Foto tecnologia: " + splitter[splitter.length-1] + "\n";
            mailText += "Modello: " + item.getInfos().getModel() + "\n";
            mailText += "Quantità: "+ item.getQta() + "\n";
            mailText += "Potenza: " + DatabaseDataManager.getObjectFromName(item.getInfos().getModel()).getModelPower()+ "\n";
            mailText += "Tonalità luce: " + item.getInfos().getTonalityString() + "\n";
            mailText += "Ore annuali : " + item.getUsageHourForPrice() + "\n";
            mailText += "Giorni all'anno : " + item.getUsageDaysInYear() + "\n";
            mailText += "Locazione : " + item.getLocation() + "\n";
            mailText += "\nCosto parziale : " + formatter.format(getPricePartial(item)) + "\n";
            mailText += "Costo manutenzione annuale : " + formatter.format(item.calculateMaintenanceCost());
            mailText += "\n-------------------------------\n\n";
        }


        List<String> paths = GalleryItems.getImages();

        if(paths.size() > 0){

            mailText += "\n\n" + "Immagini aggiuntive del sito:" + "\n";

            for(int i = 0; i < paths.size();i++){
                String[] splitterUrl = paths.get(i).split(Pattern.quote(File.separator));
                mailText += "immagine " + i + " : " + splitterUrl[splitterUrl.length - 1] + "\n";
            }
        }

        emailIntent.putExtra(Intent.EXTRA_TEXT, mailText);





        ArrayList<Uri> uris = new ArrayList<Uri>();

        List<List<Uri>> imagesForMail = new ArrayList<>();

        long byteCounter = 0;

        for (Tecnology item : this.tecList)
        {
            File directory = new File(item.getPhoto().getPath());
            if(directory.isDirectory()) {
                for (File child : directory.listFiles()) {
                    byteCounter += child.length();

                    Uri u = Uri.fromFile(child);
                    if((byteCounter - (byteCounter % 1024)) / 1024 > Constants.getByteMailLimit()){
                        imagesForMail.add((List<Uri>) uris.clone());
                        uris.clear();
                        byteCounter = 0;
                    }

                    uris.add(u);
                }
            }

        }

        if(paths.size() > 0){
            for(String url : paths){
                File fileIn = new File(url);
                Uri u = Uri.fromFile(fileIn);
                byteCounter += fileIn.length();


                if((byteCounter - (byteCounter % 1024)) / 1024 > Constants.getByteMailLimit()){
                    imagesForMail.add((List<Uri>) uris.clone());
                    uris.clear();
                    byteCounter = 0;
                }

                uris.add(u);
            }
        }


        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        from.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        if(imagesForMail.size() > 0){
            //Toast.makeText(from, "Verranno inviate più mail per tutti gli allegati", Toast.LENGTH_SHORT);
        }

        for(int i = 0; i < imagesForMail.size(); i++){
            Intent emailContentIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            emailContentIntent.setType("text/plain");
            emailContentIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                    new String[]{"mantaniandrea@gmail.com"});

            emailContentIntent.putExtra(Intent.EXTRA_SUBJECT, "Preventivo " + replaceSpecialChars().toUpperCase() + " - Allegati");

            ArrayList<Uri> currentList = (ArrayList<Uri>) imagesForMail.get(i);

            emailContentIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, currentList);
            from.startActivity(Intent.createChooser(emailContentIntent, "Send mail..."));

        }







    }

    public void deleteFolder(){
        String folderPath = Utilities.getSystem().getName();

        String folderName = folderPath.replace(" ", "_");
        folderName = folderName.replace("?", "DOT7");
        folderName = folderName.replace(".", "DOT8");

        folderName = folderName.replace(".", "DOT8");

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Nololed" + File.separator + folderName);

        deleteRecursive(folder);

    }

    private void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();

    }




    private String replaceSpecialChars(){
        String ret = this.name.replace("DOT8", ".");
        ret = ret.replace("DOT7", "?");
        ret = ret.replace("_", " ");
        ret = ret.replace("COMMALINE9", "-");
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

    public double getPricePartial(Tecnology item){
        double partialPrice = 0;

        int hourOfUsage = 0;

        TecnologyModel model = DatabaseDataManager.getObjectFromName(item.getInfos().getModel());

        int power = item.getInfos().getPower();

        if(SolarYearHours.isSolarYear()){
            hourOfUsage = SolarYearHours.getHourInYear();
            partialPrice = hourOfUsage * power * item.getQta();
        }else{

            partialPrice = item.getUsageHourForPrice() * power * item.getQta();
        }
        partialPrice = partialPrice / 1000;

        return partialPrice * Constants.getPriceForEnergy();
    }


    public double getPartialPrice(int power, int hours, int qta/*,double price*/){
        double partialPrice = 0;

        partialPrice = hours * power * qta;
        partialPrice = partialPrice / 1000;
        //partialPrice += (qta * price);

        return partialPrice * Constants.getPriceForEnergy();
    }

    public List<TecnologyModel> getLedSystem(){
        List<TecnologyModel> returner = new ArrayList<>();

        for(int i = 0; i < this.tecList.size(); i++ ){
            returner.add(DatabaseDataManager.getObjectFromName(tecList.get(i).getInfos().getModel()));
        }

        return returner;
    }

    public double getMonthlyRateForNolo(){
        double monthlyRate = 0;
        for(int i = 0; i < this.tecList.size(); i++) {
            TecnologyModel item = DatabaseDataManager.getObjectFromName(tecList.get(i).getInfos().getModel());

            monthlyRate += item.price  * tecList.get(i).getQta();

        }
            return monthlyRate;
    }

    public double calculateMaintenanceCost(){

        double totalCost = 0;
        double ol;

        for(int i = 0; i < this.tecList.size(); i++){
            totalCost += this.tecList.get(i).calculateMaintenanceCost();
        }

        return totalCost;

    }


}
