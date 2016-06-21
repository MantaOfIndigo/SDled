package com.nololed.andreamantani.nololed.Utils;

import android.app.Application;

import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.LedTecTypeData;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.LedTecTypeRecord;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.LedTecTypeTable;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyRecord;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyTable;
import com.nololed.andreamantani.nololed.Model.Tecnology;
import com.nololed.andreamantani.nololed.Model.TecnologyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 09/06/16.
 */
public class DatabaseDataManager extends Application{

    private static List<TecnologyModel> models;

    static class dataModel
    {
        public String name;
        public int power;

        public dataModel(String name, int power){
            this.name = name;
            this.power = power;
        }
    };

    private static dataModel[] neonModelNames = {
            new dataModel("Neon 18 watt", 18),
            new dataModel("Neon 30 watt", 30),
            new dataModel("Neon 36 watt", 36),
            new dataModel("Neon 50 watt", 50)
    };

    private static dataModel[] incComModelNames = {
            new dataModel("Incasso Compatte 1x18", 18),
            new dataModel("Incasso Compatte 2x18", 2*18 ),
            new dataModel("Incasso Compatte 1x26", 26 ),
            new dataModel("Incasso Compatte 2x26", 2*26 ),
            new dataModel("Incasso Compatte 1x35", 35 ),
    };

    private static dataModel[] incIodModelNames = {
            new dataModel("Incasso ioduri 35 watt", 35),
            new dataModel("Incasso ioduri 70 watt", 70),
            new dataModel("Incasso ioduri 150 watt", 150)
    };

    private static dataModel[] mr16ModelNames = {
            new dataModel("Supporto MR16", 16),
            new dataModel("GU10 4 watt", 4),
            new dataModel("GU10 7 watt", 7)
    };

    private static dataModel[] ar111ModelNames = {
            new dataModel("AR111 50 watt", 50),
            new dataModel("AR111 75 watt", 75),
            new dataModel("AR111 30 watt ioduri", 30),
            new dataModel("AR111 70 watt ioduri", 70)
    };

    private static dataModel[] plafStaModelNames = {
            new dataModel("Plafoniere Stagne 1x18", 18),
            new dataModel("Plafoniere Stagne 2x18", 2*18),
            new dataModel("Plafoniere Stagne 1x36", 36),
            new dataModel("Plafoniere Stagne 2x36", 2*36),
            new dataModel("Plafoniere Stagne 1x58", 58),
            new dataModel("Plafoniere Stagne 2x58", 2*58)
    };

    private static dataModel[] plafStaCusModelNames = {
            new dataModel("Plafoniere Stagne Custom 1x18", 18),
            new dataModel("Plafoniere Stagne Custom 2x18", 2*18),
            new dataModel("Plafoniere Stagne Custom 1x36", 36),
            new dataModel("Plafoniere Stagne Custom 2x36", 2*36),
            new dataModel("Plafoniere Stagne Custom 1x58", 58),
            new dataModel("Plafoniere Stagne Custom 2x58", 2*58)
    };

    private static dataModel[] proiModelName = {
            new dataModel("Proiettori ioduri 150 watt", 150),
            new dataModel("Proiettori ioduri 250 watt", 250),
            new dataModel("Proiettori ioduri 400 watt", 400)
    };

    private static dataModel[] plafIndModelName = {
            new dataModel("Plafoni Ind ioduri 250 watt", 250),
            new dataModel("Plafoni Ind ioduri 400 watt", 400)

    };

    public static void createList(){
        OldTecFamilyTable.createTable();
        LedTecTypeTable.createTable();

        models = new ArrayList<>();

        //CREAZIONE DI UNO STATO DEL DATABASE STANDARD SEGUENDO LA TABELLA

        //NEON

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL13"), neonModelNames[0].name, neonModelNames[0].power, 13, 0.5));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL15"), neonModelNames[1].name, neonModelNames[1].power, 15, 1.4));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL18"), neonModelNames[2].name, neonModelNames[2].power, 17, 0.8));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL24"), neonModelNames[3].name, neonModelNames[3].power, 23, 1));


        // INCASSO COMPATTE

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT"), incComModelNames[0].name, incComModelNames[0].power, 10, 1.3));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT"), incComModelNames[1].name, incComModelNames[1].power, 15, 2.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT"), incComModelNames[2].name, incComModelNames[2].power, 15, 2.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT"), incComModelNames[3].name, incComModelNames[3].power, 30, 2.3));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT"), incComModelNames[4].name, incComModelNames[4].power, 25, 2.1));


        // INCASSO IODURI

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCIOD"), LedTecTypeTable.getItemFromId("DWLIGHTOR"), incIodModelNames[0].name, incIodModelNames[0].power, 22, 3.7));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCIOD"), LedTecTypeTable.getItemFromId("DWLIGHTOR"), incIodModelNames[1].name, incIodModelNames[1].power, 40, 6.2));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCIOD"), LedTecTypeTable.getItemFromId("DWLIGHTOR"), incIodModelNames[2].name, incIodModelNames[2].power, 60, 9.6));


        // MR16

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("MR16"), LedTecTypeTable.getItemFromId("PFARBIANOR"), mr16ModelNames[0].name, mr16ModelNames[0].power, 1, 1.3));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("MR16"), LedTecTypeTable.getItemFromId("PFARBIANOR"), mr16ModelNames[1].name, mr16ModelNames[1].power, 5, 0.4));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("MR16"), LedTecTypeTable.getItemFromId("PFARBIANOR"), mr16ModelNames[2].name, mr16ModelNames[2].power, 7, 0.5));


        //AR111

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("AR111"), ar111ModelNames[0].name, ar111ModelNames[0].power, 13, 1.5));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("AR111"), ar111ModelNames[1].name, ar111ModelNames[1].power, 15, 2.8));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("DWLIGHTOR"), ar111ModelNames[2].name, ar111ModelNames[2].power, 22, 3.7));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("DWLIGHTOR"), ar111ModelNames[3].name, ar111ModelNames[3].power, 40, 6.2));


        // PLFAONI STAGNO

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF19"), plafStaModelNames[0].name, plafStaModelNames[0].power, 0, 0.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF29"), plafStaModelNames[1].name, plafStaModelNames[1].power, 0, 0.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF115"), plafStaModelNames[2].name, plafStaModelNames[2].power, 15, 1.7));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF215"), plafStaModelNames[3].name, plafStaModelNames[3].power, 30, 2.8));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF124"), plafStaModelNames[4].name, plafStaModelNames[4].power, 23, 2.4));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF224"), plafStaModelNames[5].name, plafStaModelNames[5].power, 46, 4.0));


        // PLAFONI CUSTOM

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLASTC"), LedTecTypeTable.getItemFromId("DISANPLA"), plafStaCusModelNames[0].name, plafStaCusModelNames[0].power, 9, 0.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLASTC"), LedTecTypeTable.getItemFromId("DISANPLA"), plafStaCusModelNames[1].name, plafStaCusModelNames[1].power, 18, 0.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLASTC"), LedTecTypeTable.getItemFromId("DISANPLA"), plafStaCusModelNames[2].name, plafStaCusModelNames[2].power, 15, 6.4));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLASTC"), LedTecTypeTable.getItemFromId("DISANPLA"), plafStaCusModelNames[3].name, plafStaCusModelNames[3].power, 30, 7.3));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLASTC"), LedTecTypeTable.getItemFromId("DISANPLA"), plafStaCusModelNames[4].name, plafStaCusModelNames[4].power, 40, 8.1));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLASTC"), LedTecTypeTable.getItemFromId("DISANPLA"), plafStaCusModelNames[5].name, plafStaCusModelNames[5].power, 54, 6.1));


        //PROIETTORI

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PROIOD"), LedTecTypeTable.getItemFromId("PROIP65OR"), proiModelName[0].name, proiModelName[0].power, 80, 17.0));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PROIOD"), LedTecTypeTable.getItemFromId("PROIP65OR"), proiModelName[1].name, proiModelName[1].power, 160, 30.4));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PROIOD"), LedTecTypeTable.getItemFromId("PROIP65OR"), proiModelName[2].name, proiModelName[2].power, 200, 36.5));

        //Plafoni Ind Ioduri

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAIOD"), LedTecTypeTable.getItemFromId("PLAFINDIP65"), plafIndModelName[0].name, plafIndModelName[0].power, 160, 30.6));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAIOD"), LedTecTypeTable.getItemFromId("PLAFINDIP65"), plafIndModelName[1].name, plafIndModelName[1].power, 200, 36.6));
    }

    public static void addList(TecnologyModel item){
        models.add(item);
    }
    public static String[] getModelOfFamily(String family){

        List<String> returner = new ArrayList<>();

        for(int i = 0; i < models.size(); i++){
            if(family.equals(models.get(i).getFamilyName())){
                returner.add(models.get(i).getModelName());
            }
        }

        String[] array = new String[returner.size()];

        return returner.toArray(array);
    }
    public static TecnologyModel getObjectFromName(String name){
        for(int i = 0; i < models.size();i++){
            if(name.equals(models.get(i).getModelName())){
                return models.get(i);
            }
        }

        return null;
    }

}
