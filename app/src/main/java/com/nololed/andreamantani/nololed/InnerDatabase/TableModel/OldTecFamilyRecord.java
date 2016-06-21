package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

import android.app.Application;
import android.net.Uri;

/**
 * Created by andreamantani on 09/06/16.
 */
public class OldTecFamilyRecord {

    private String codId;
    private String name;
    private int imageUrl;

    public OldTecFamilyRecord(String codId, String name, int imageUrl){
        super();
        this.name = name;
        this.imageUrl = imageUrl;
        this.codId = codId;
    }

    public String getCodId(){
        return this.codId;
    }

    public String getName(){
        return this.name;
    }

    public int getImageResources(){
        return imageUrl;
    }


}
