package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyData;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyTable;
import com.nololed.andreamantani.nololed.Model.ProfilePhotoItem;
import com.nololed.andreamantani.nololed.Model.Tecnology;
import com.nololed.andreamantani.nololed.Model.TecnologyModel;
import com.nololed.andreamantani.nololed.Utils.DatabaseDataManager;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 10/06/16.
 */
public class SelectModelActivity extends AppCompatActivity {


    EditText customPower;
    int newOrNot = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_model);


        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            if(extra.getString("family") != null) {

                final ListView listview = (ListView) findViewById(R.id.select_model_list);
                String[] values = DatabaseDataManager.getModelOfFamily(extra.getString("family"));

                final ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < values.length; ++i) {
                    list.add(values[i]);
                }

                final StableArrayAdapter adapter = new StableArrayAdapter(this,
                        R.layout.family_record, list);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        String item = (String) parent.getItemAtPosition(position);

                        setCustomModelPower(item);

                        /*Intent intnt = new Intent(SelectModelActivity.this, ProfileTecnologyActivity.class);
                        intnt.putExtra("selected_model", item);
                        intnt.putExtra("new_or_not", newOrNot);
                        startActivity(intnt);*/

                    }

                });
            }

            newOrNot = extra.getInt("new_item");

        }else{
                //Avverti che ci sono problemi
        }



    }

    private void setCustomModelPower(String modelName){

        TextView selectedName = (TextView) findViewById(R.id.select_model_selected);
        customPower = (EditText) findViewById(R.id.select_model_power);

        TecnologyModel item = DatabaseDataManager.getObjectFromName(modelName);

        String prova = item.serializeModel();

        TecnologyModel prova2 = item.deserializeModel(prova);

        String[] splitter;

        if(item.getFamilyName().equals(OldTecFamilyTable.getItemFromId("PLAST").getName()) ||
                item.getFamilyName().equals(OldTecFamilyTable.getItemFromId("PLASTC").getName()) ||
                item.getFamilyName().equals(OldTecFamilyTable.getItemFromId("INCCOM").getName())){
            splitter = modelName.split(Pattern.quote("x"));

            selectedName.setText(splitter[0] + "x");
        }else{
            selectedName.setText(item.getModelName());
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:

        }

        return true;
    }


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}


