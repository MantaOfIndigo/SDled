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
    double tecnologyCost;
    boolean transformer;
    int tecnologyLasting;


    public TecnologyModel(){
        this.codId = "";
        this.familyName = "";
        this.modelName = "";
        this.modelPower = 0;
        this.ledSobstitutiveCod = "";
        this.ledPower = 0;
        this.price = 0;
        this.transformer = true;
    }

    public TecnologyModel(String codId, String familyName, String modelName, int modelPower, String ledSobstitutiveCod, int ledPower, double price, boolean transformer){
        this.familyName = familyName;
        this.modelName = modelName;
        this.modelPower = modelPower;
        this.ledSobstitutiveCod = ledSobstitutiveCod;
        this.ledPower = ledPower;
        this.price = price;
        this.codId = codId;
        this.transformer = transformer;
    }

    public TecnologyModel(OldTecFamilyRecord oldTecRecord, LedTecTypeRecord ledTecRecord, String modelName, int modelPower, int ledPower, double price, boolean transformer){
        this.codId = oldTecRecord.getCodId() + ledTecRecord.getCodId() + String.valueOf(modelPower);
        this.modelPower = modelPower;
        this.modelName = modelName;
        this.familyName = oldTecRecord.getName();
        this.ledSobstitutiveCod = ledTecRecord.getCodId();
        this.ledPower = ledPower;
        this.price = price;
        this.transformer = transformer;
    }

    public TecnologyModel(OldTecFamilyRecord oldTecRecord, LedTecTypeRecord ledTecRecord, String modelName, int modelPower, int ledPower, double price, boolean transformer, double tecnologyCost, int hourLasting){
        this.codId = oldTecRecord.getCodId() + ledTecRecord.getCodId() + String.valueOf(modelPower);
        this.modelPower = modelPower;
        this.modelName = modelName;
        this.familyName = oldTecRecord.getName();
        this.ledSobstitutiveCod = ledTecRecord.getCodId();
        this.ledPower = ledPower;
        this.price = price;
        this.transformer = transformer;
        this.tecnologyCost = tecnologyCost;
        this.tecnologyLasting = hourLasting;
    }

    public TecnologyModel(String codId, String familyName, String modelName, int modelPower, String ledSobstitutiveCod, int ledPower, double price, boolean transformer, double tecnologyCost){
        this.familyName = familyName;
        this.modelName = modelName;
        this.modelPower = modelPower;
        this.ledSobstitutiveCod = ledSobstitutiveCod;
        this.ledPower = ledPower;
        this.price = price;
        this.codId = codId;
        this.transformer = transformer;
        this.tecnologyCost = tecnologyCost;
    }

    public TecnologyModel(String codId, String familyName, String modelName, int modelPower, String ledSobstitutiveCod, int ledPower, double price, boolean transformer, double tecnologyCost, int hourLasting){
        this.familyName = familyName;
        this.modelName = modelName;
        this.modelPower = modelPower;
        this.ledSobstitutiveCod = ledSobstitutiveCod;
        this.ledPower = ledPower;
        this.price = price;
        this.codId = codId;
        this.transformer = transformer;
        this.tecnologyCost = tecnologyCost;
        this.tecnologyLasting = hourLasting;
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
    public double getTecnologyCost(){
        return this.tecnologyCost;
    }
    public int getTecnologyLasting(){ return this.tecnologyLasting; }
    public int getModelPowerWithTransformer(){
        if(this.transformer){
            return this.modelPower + (this.modelPower * 15 / 100);
        }

        return this.modelPower;
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

        returner += "cod : " + this.codId +
                " , family : " + this.familyName +
                " , model : " + this.modelName +
                " , modelPower : " + this.modelPower +
                " , ledCod : " + this.ledSobstitutiveCod +
                " , ledPower : " + this.ledPower +
                " , price : " + this.price +
                " , transformer : " + this.transformer +
                " , maintenance : " + this.tecnologyCost +
                " , lasting : " + this.tecnologyLasting;

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
        boolean desTransformer;
        Double desMaintenance;
        int desLasting;

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
        desTransformer = Boolean.parseBoolean(splitter[7].split(Pattern.quote(":"))[1].replace(" ", ""));
        desMaintenance = Double.parseDouble(splitter[8].split(Pattern.quote(":"))[1].replace(" ", ""));
        desLasting = Integer.parseInt(splitter[9].split(Pattern.quote(":"))[1].replace(" ", ""));


        return new TecnologyModel(desCod,desFamily,desModel,desModPow,desLedCod,desLedPow,desPrice, desTransformer, desMaintenance, desLasting);
    }



}
