package com.nololed.andreamantani.nololed.InnerDatabase.TableModel;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 09/06/16.
 */
public class LedTecTypeTable extends Application{

    private static List<LedTecTypeRecord> records;

    public static void createTable() {

        records = new ArrayList<>();

        for(int i = 0; i < LedTecTypeData.ledNames.length; i++){

            String name = LedTecTypeData.ledNames[i];
            String codId = LedTecTypeData.ledIds[i];

            LedTecTypeRecord newRecord = new LedTecTypeRecord(codId, name);

            records.add(newRecord);

        }
    }

    public static LedTecTypeRecord getItemFromId(String id){
        for (int i = 0 ; i < records.size(); i++){
            if(id.equals(records.get(i).getCodId())){
                return records.get(i);
            }
        }

        return null;
    }
}
