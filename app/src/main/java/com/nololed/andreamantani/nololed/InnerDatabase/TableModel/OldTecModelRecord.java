package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

/**
 * Created by andreamantani on 09/06/16.
 */
public class OldTecModelRecord {

    String codId;
    String modelName;
    int modelPower;
    OldTecFamilyRecord family;

    public OldTecModelRecord(String modelName, int modelPower, OldTecFamilyRecord family){
        this.modelName = modelName;
        this.modelPower = modelPower;
        this.family = family;
    }

    public String getCodId(){
        return this.codId;
    }

    public String getModelName(){
        return this.modelName;
    }

    public int getModelPower(){
        return this.modelPower;
    }

    public OldTecFamilyRecord getFamily(){
        return this.family;
    }
}
