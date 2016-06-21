package com.nololed.andreamantani.nololed.Model;

import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.LedTecTypeData;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.LedTecTypeRecord;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyRecord;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 09/06/16.
 */
public class TecnologyModel implements Serializable {

    String codId;
    String familyName;
    String modelName;
    int modelPower;
    String ledSobstitutiveCod;
    int ledPower;
    double price;

    public TecnologyModel(String codId, String familyName, String modelName, int modelPower, String ledSobstitutiveCod, int ledPower, double price){
        this.familyName = familyName;
        this.modelName = modelName;
        this.modelPower = modelPower;
        this.ledSobstitutiveCod = ledSobstitutiveCod;
        this.ledPower = ledPower;
        this.price = price;
        this.codId = codId;
    }

    public TecnologyModel(OldTecFamilyRecord oldTecRecord, LedTecTypeRecord ledTecRecord, String modelName, int modelPower, int ledPower, double price){
        this.codId = oldTecRecord.getCodId() + ledTecRecord.getCodId() + String.valueOf(modelPower);
        this.modelPower = modelPower;
        this.modelName = modelName;
        this.familyName = oldTecRecord.getName();
        this.ledSobstitutiveCod = ledTecRecord.getCodId();
        this.ledPower = ledPower;
        this.price = price;
    }

    public String getCodId(){
        return this.codId;
    }
    public String getFamilyName(){
        return this.familyName;
    }
    public String getModelName(){
        return this.modelName;
    }
    public String getLedSobstitutiveCod(){
        return this.ledSobstitutiveCod;
    }
    public int getModelPower(){
        return this.modelPower;
    }
    public int getLedPower(){
        return this.ledPower;
    }
    public double getPrice(){
        return this.price;
    }

    public String getLedName(){
        return LedTecTypeData.getLedNameFromID(this.ledSobstitutiveCod);
    }

    public String serializeModel(){
        String returner = "{";

        returner += "cod : " + this.codId + " , family : " + this.familyName + " , model : " + this.modelName + " , modelPower : " + this.modelPower + " , ledCod : " + this.ledSobstitutiveCod + " , ledPower : " + this.ledPower + " , price : " + this.price;

        returner += "}";
        return returner;
    }

    public TecnologyModel deserializeModel(String modelSerializable){

        String desCod;
        String desFamily;
        String desModel;
        int desModPow;
        String desLedCod;
        int desLedPow;
        Double desPrice;

        modelSerializable = modelSerializable.replace("{", "");
        modelSerializable = modelSerializable.replace("}", "");

        String[] splitter = modelSerializable.split(Pattern.quote(","));


        desCod = splitter[0].split(Pattern.quote(":"))[1].replace(" ", "");
        desFamily = splitter[1].split(Pattern.quote(":"))[1];
        desFamily = desFamily.substring(1, desFamily.length() - 1);
        desModel = splitter[2].split(Pattern.quote(":"))[1];
        desModel = desModel.substring(1,desModel.length() - 1);
        desModPow = Integer.parseInt(splitter[3].split(Pattern.quote(":"))[1].replace(" ", ""));
        desLedCod = splitter[4].split(Pattern.quote(":"))[1].replace(" ", "");
        desLedPow = Integer.parseInt(splitter[5].split(Pattern.quote(":"))[1].replace(" ", ""));
        desPrice = Double.parseDouble(splitter[6].split(Pattern.quote(":"))[1].replace(" ", ""));


        return new TecnologyModel(desCod,desFamily,desModel,desModPow,desLedCod,desLedPow,desPrice);
    }



}
