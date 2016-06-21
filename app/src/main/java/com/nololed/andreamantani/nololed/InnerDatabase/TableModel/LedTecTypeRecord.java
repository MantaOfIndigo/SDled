package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

/**
 * Created by andreamantani on 09/06/16.
 */
public class LedTecTypeRecord {

    private String codId;
    private String name;

    public LedTecTypeRecord(String codId, String name){
        super();
        this.name = name;
        this.codId = codId;
    }

    public String getCodId(){
        return this.codId;
    }

    public String getName(){
        return this.name;
    }
}
