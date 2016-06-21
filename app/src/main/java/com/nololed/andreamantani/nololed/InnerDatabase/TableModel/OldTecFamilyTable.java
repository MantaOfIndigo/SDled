package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

import android.app.Application;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 09/06/16.
 */
public class OldTecFamilyTable extends Application {

    private static List<OldTecFamilyRecord> records;



    public static void createTable(){
        records = new ArrayList<>();

        for(int i = 0; i < OldTecFamilyData.familyNames.length ; i++){
            String name = OldTecFamilyData.familyNames[i];
            String cod = OldTecFamilyData.familyCods[i];
            int path = OldTecFamilyData.urls[i];
            //urls

            OldTecFamilyRecord newRecord = new OldTecFamilyRecord(cod, name, path);
            records.add(newRecord);
        }

    }

    public static OldTecFamilyRecord getItemFromId(String id){
        for (int i = 0 ; i < records.size(); i++){
            if(id.equals(records.get(i).getCodId())){
                return records.get(i);
            }
        }

        return null;
    }

    public static OldTecFamilyRecord getItemFromName(String name){
        for (int i = 0 ; i < records.size(); i++){
            if(name.equals(records.get(i).getName())){
                return records.get(i);
            }
        }

        return null;
    }

}
