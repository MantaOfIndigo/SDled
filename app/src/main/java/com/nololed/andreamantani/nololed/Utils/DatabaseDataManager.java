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
        public double cost;
        private int endurance;

        public dataModel(String name, int power, double maintenanceCost, int hourEndurance){
            this.name = name;
            this.power = power;
            this.cost = maintenanceCost;
            this.endurance = hourEndurance;
        }
    };


    private static dataModel[] lampsModelNames = {
            new dataModel("Lampade E27/14 30 watt", 30, 2.50, 2000),
            new dataModel("Lampade E27/14 46 watt", 46, 2.50, 2000),
            new dataModel("Lampade E27/14 57 watt", 57, 2.50, 2000),
            new dataModel("Lampade E27/14 116 watt", 116, 2.50, 2000)
    };

    private static dataModel[] neonModelNames = {
            new dataModel("Neon 18 watt", 18, 4.0, 6000),
            new dataModel("Neon 30 watt", 30, 4.0, 6000),
            new dataModel("Neon 36 watt", 36, 5.0, 6000),
            new dataModel("Neon 50 watt", 50, 5.0, 6000)
    };

    private static dataModel[] regletteModelNames = {
            new dataModel("Reglette T5 30cm 8 watt", 8, 18.0, 5000),
            new dataModel("Reglette T5 60cm 14 watt", 14, 18.0, 5000 ),
            new dataModel("Reglette T5 90cm 21 watt", 21, 26.0, 5000 ),
            new dataModel("Reglette T5 120cm 28 watt", 28,30.0, 5000 ),
            new dataModel("Reglette T5 150cm 35 watt", 35, 36.0, 5000 )
    };

    private static dataModel[] panelModelNames = {
            new dataModel("Pannello 4x18 watt Incasso", 4*18, 16.0, 6000),
            new dataModel("Pannello 4x18 watt Parete", 4*18, 16.0, 6000),
            new dataModel("Accessori Pannelli", 1 , -1, -1)
    };

    private static dataModel[] incComModelNames = {
            new dataModel("Incasso Compatte 1x18", 18, 7.0, 5000),
            new dataModel("Incasso Compatte 2x18", 2*18 , 2.0*7.0, 5000),
            new dataModel("Incasso Compatte 1x26", 26 , 7.0, 5000),
            new dataModel("Incasso Compatte 2x26", 2*26 , 2.0*7.0, 5000),
            new dataModel("Incasso Compatte 1x35", 35, 7.0, 5000)
    };

    private static dataModel[] incIodModelNames = {
            new dataModel("Incasso ioduri 35 watt", 35, 20.0, 9000),
            new dataModel("Incasso ioduri 70 watt", 70, 25.0, 9000),
            new dataModel("Incasso ioduri 150 watt", 150, 30.0, 9000)
    };

    private static dataModel[] mr16ModelNames = {
            new dataModel("Supporto MR16", 16, 5.0, 1000),
            new dataModel("GU10 4 watt", 4, 5.0, 1000),
            new dataModel("GU10 7 watt", 7, 5.0, 1000)
    };

    private static dataModel[] ar111ModelNames = {
            new dataModel("AR111 50 watt", 50, 12.0, 2000),
            new dataModel("AR111 75 watt", 75, 12.0, 2000),
            new dataModel("AR111 30 watt ioduri", 30, 20.0, 2000),
            new dataModel("AR111 70 watt ioduri", 70, 25.0, 2000)
    };

    private static dataModel[] plafStaModelNames = {
            new dataModel("Plafoniere Stagne 2x18", 2*18, 2.0*4.0, 6000),
            new dataModel("Plafoniere Stagne 1x36", 36, 5.0, 6000),
            new dataModel("Plafoniere Stagne 2x36", 2*36, 2.0*5.0, 6000),
            new dataModel("Plafoniere Stagne 1x58", 58, 5.0, 6000),
            new dataModel("Plafoniere Stagne 2x58", 2*58, 2.0*5.0, 6000)
    };


    private static dataModel[] proiModelName = {
            new dataModel("Proiettori ioduri 150 watt", 150, 30.0, 9000),
            new dataModel("Proiettori ioduri 250 watt", 250, 38.0, 9000),
            new dataModel("Proiettori ioduri 400 watt", 400, 48.0, 9000)
    };

    private static dataModel[] plafIndModelName = {
            new dataModel("Plafoni Ind ioduri 250 watt", 250, 38.0, 9000),
            new dataModel("Plafoni Ind ioduri 400 watt", 400, 48.0, 9000)

    };

    public static void createList(){
        OldTecFamilyTable.createTable();
        LedTecTypeTable.createTable();

        models = new ArrayList<>();

        //CREAZIONE DI UNO STATO DEL DATABASE STANDARD SEGUENDO LA TABELLA

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("LAMP"), LedTecTypeTable.getItemFromId("LL5"), lampsModelNames[0].name, lampsModelNames[0].power, 5, 0.4, false, lampsModelNames[0].cost, lampsModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("LAMP"), LedTecTypeTable.getItemFromId("LL7"), lampsModelNames[1].name, lampsModelNames[1].power, 7, 0.5, false, lampsModelNames[1].cost, lampsModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("LAMP"), LedTecTypeTable.getItemFromId("LL9"), lampsModelNames[2].name, lampsModelNames[2].power, 9, 0.7, false, lampsModelNames[2].cost, lampsModelNames[2].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("LAMP"), LedTecTypeTable.getItemFromId("LL12"), lampsModelNames[3].name, lampsModelNames[3].power, 12, 1.30, false, lampsModelNames[3].cost, lampsModelNames[3].endurance));


        //NEON

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL13"), neonModelNames[0].name, neonModelNames[0].power, 13, 0.5, true, neonModelNames[0].cost, neonModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL15"), neonModelNames[1].name, neonModelNames[1].power, 15, 1.4, true, neonModelNames[1].cost, neonModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL18"), neonModelNames[2].name, neonModelNames[2].power, 17, 0.8, true, neonModelNames[2].cost, neonModelNames[2].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("NEON"), LedTecTypeTable.getItemFromId("TBL24"), neonModelNames[3].name, neonModelNames[3].power, 23, 1, true, neonModelNames[3].cost, neonModelNames[3].endurance));


        //REGLETTE

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("REGLE"), LedTecTypeTable.getItemFromId("RE4"), regletteModelNames[0].name, regletteModelNames[0].power, 8, 1, true, regletteModelNames[0].cost, regletteModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("REGLE"), LedTecTypeTable.getItemFromId("RE8"), regletteModelNames[1].name, regletteModelNames[1].power, 13, 1.2, true, regletteModelNames[1].cost, regletteModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("REGLE"), LedTecTypeTable.getItemFromId("RE10"), regletteModelNames[2].name, regletteModelNames[2].power, 21, 1.3, true, regletteModelNames[2].cost, regletteModelNames[2].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("REGLE"), LedTecTypeTable.getItemFromId("RE14"), regletteModelNames[3].name, regletteModelNames[3].power, 14, 1.5, true, regletteModelNames[3].cost, regletteModelNames[3].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("REGLE"), LedTecTypeTable.getItemFromId("RE18"), regletteModelNames[4].name, regletteModelNames[4].power, 18, 2.1, true, regletteModelNames[4].cost, regletteModelNames[4].endurance));


        // PANEL

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PANEL"), LedTecTypeTable.getItemFromId("PAI38"), panelModelNames[0].name, panelModelNames[0].power, 38, 4, true, panelModelNames[0].cost, panelModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PANEL"), LedTecTypeTable.getItemFromId("PAP38"), panelModelNames[1].name, panelModelNames[1].power, 38, 4.4, true, panelModelNames[1].cost, panelModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PANEL"), LedTecTypeTable.getItemFromId("KITA"), panelModelNames[2].name, panelModelNames[2].power, 1, 2.7, true, panelModelNames[2].cost, panelModelNames[2].endurance));


        // INCASSO COMPATTE

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT10"), incComModelNames[0].name, incComModelNames[0].power, 10, 1.3, true, incComModelNames[0].cost, incComModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT15"), incComModelNames[1].name, incComModelNames[1].power, 15, 2.0, true, incComModelNames[1].cost, incComModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT15"), incComModelNames[2].name, incComModelNames[2].power, 15, 2.0, true, incComModelNames[2].cost, incComModelNames[2].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT30"), incComModelNames[3].name, incComModelNames[3].power, 30, 2.3, true, incComModelNames[3].cost, incComModelNames[3].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCCOM"), LedTecTypeTable.getItemFromId("DWLIGHT25"), incComModelNames[4].name, incComModelNames[4].power, 25, 2.1, true, incComModelNames[4].cost, incComModelNames[4].endurance));


        // INCASSO IODURI

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCIOD"), LedTecTypeTable.getItemFromId("DWLIGHTOR22"), incIodModelNames[0].name, incIodModelNames[0].power, 22, 3.7, true, incIodModelNames[0].cost, incIodModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCIOD"), LedTecTypeTable.getItemFromId("DWLIGHTOR40"), incIodModelNames[1].name, incIodModelNames[1].power, 40, 6.2, true, incIodModelNames[1].cost, incIodModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("INCIOD"), LedTecTypeTable.getItemFromId("DWLIGHTOR60"), incIodModelNames[2].name, incIodModelNames[2].power, 60, 9.6, true, incIodModelNames[2].cost, incIodModelNames[2].endurance));


        // MR16

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("MR16"), LedTecTypeTable.getItemFromId("PFARBIANOR"), mr16ModelNames[0].name, mr16ModelNames[0].power, 1, 1.3, true, mr16ModelNames[0].cost, mr16ModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("MR16"), LedTecTypeTable.getItemFromId("GU5"), mr16ModelNames[1].name, mr16ModelNames[1].power, 5, 0.4, false, mr16ModelNames[1].cost, mr16ModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("MR16"), LedTecTypeTable.getItemFromId("GU7"), mr16ModelNames[2].name, mr16ModelNames[2].power, 7, 0.5, false, mr16ModelNames[2].cost, mr16ModelNames[2].endurance));


        //AR111

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("AR13"), ar111ModelNames[0].name, ar111ModelNames[0].power, 13, 1.3, false, ar111ModelNames[0].cost, ar111ModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("AR15"), ar111ModelNames[1].name, ar111ModelNames[1].power, 15, 2.8, false, ar111ModelNames[1].cost, ar111ModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("AR22"), ar111ModelNames[2].name, ar111ModelNames[2].power, 22, 3.7, true, ar111ModelNames[2].cost, ar111ModelNames[2].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("AR111"), LedTecTypeTable.getItemFromId("AR40"), ar111ModelNames[3].name, ar111ModelNames[3].power, 40, 6.2, true, ar111ModelNames[3].cost, ar111ModelNames[3].endurance));


        // PLFAONI STAGNO

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF29"), plafStaModelNames[0].name, plafStaModelNames[0].power, 18, 1.5, true, plafStaModelNames[0].cost, plafStaModelNames[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF15"), plafStaModelNames[1].name, plafStaModelNames[1].power, 15, 1.7, true, plafStaModelNames[1].cost, plafStaModelNames[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF215"), plafStaModelNames[2].name, plafStaModelNames[2].power, 30, 2.8, true, plafStaModelNames[2].cost, plafStaModelNames[2].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF23"), plafStaModelNames[3].name, plafStaModelNames[3].power, 23, 2.4, true, plafStaModelNames[3].cost, plafStaModelNames[3].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAST"), LedTecTypeTable.getItemFromId("PLAF223"), plafStaModelNames[4].name, plafStaModelNames[4].power, 46, 4, true, plafStaModelNames[4].cost, plafStaModelNames[4].endurance));


        //PROIETTORI

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PROIOD"), LedTecTypeTable.getItemFromId("PROIP65OR80"), proiModelName[0].name, proiModelName[0].power, 80, 17.0, true, proiModelName[0].cost, proiModelName[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PROIOD"), LedTecTypeTable.getItemFromId("PROIP65OR160"), proiModelName[1].name, proiModelName[1].power, 160, 30.4, true, proiModelName[1].cost, proiModelName[1].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PROIOD"), LedTecTypeTable.getItemFromId("PROIP65OR200"), proiModelName[2].name, proiModelName[2].power, 200, 36.5, true, proiModelName[2].cost, proiModelName[2].endurance));

        //Plafoni Ind Ioduri

        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAIOD"), LedTecTypeTable.getItemFromId("PLAFINDIP65160"), plafIndModelName[0].name, plafIndModelName[0].power, 160, 30.6, true, plafIndModelName[0].cost, plafIndModelName[0].endurance));
        addList(new TecnologyModel(OldTecFamilyTable.getItemFromId("PLAIOD"), LedTecTypeTable.getItemFromId("PLAFINDIP65200"), plafIndModelName[1].name, plafIndModelName[1].power, 200, 36.6, true, plafIndModelName[1].cost, plafIndModelName[1].endurance));
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
        TecnologyModel returner = null;
        for(int i = 0; i < models.size();i++){
            if(name.equals(models.get(i).getModelName())){
                returner = models.get(i);
            }
        }

        if(returner == null){
            return new TecnologyModel();
        }

        return returner;
    }
    public static double getMaintenanceCost(String name){
        TecnologyModel searcher = getObjectFromName(name);

        if(searcher.getTecnologyCost() < 1){
            // caso che accade solamente quando il modello non è ancora stato selezionato
            return -1;
        }

        return searcher.getTecnologyCost();
    }

}
