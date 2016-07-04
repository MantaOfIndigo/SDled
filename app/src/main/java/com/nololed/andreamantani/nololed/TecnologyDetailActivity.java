package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyData;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyTable;
import com.nololed.andreamantani.nololed.Model.Tecnology;
import com.nololed.andreamantani.nololed.Model.TecnologyModel;
import com.nololed.andreamantani.nololed.Utils.DatabaseDataManager;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by andreamantani on 23/06/16.
 */
public class TecnologyDetailActivity extends AppCompatActivity {


    int powerOrigin = 0;
    double maintenance = 0;
    String modelName;
    int newOrNot = -1;
    Tecnology tecnologyDetailed;

    EditText modelPowerTxt;
    EditText maintenancePriceTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnology_detail);

        hideKeyboard();

        Bundle extras = getIntent().getExtras();



        TextView modelNameTxt = (TextView) findViewById(R.id.detail_model_name);
        modelPowerTxt = (EditText) findViewById(R.id.detail_power_tec);
        TextView familyNameTxt = (TextView) findViewById(R.id.detail_family_name);
        TextView ledSobstTxt = (TextView) findViewById(R.id.detail_sobstitutive_name);
        TextView ledPowerTxt = (TextView) findViewById(R.id.detail_power_led);
        final TextView maintenanceYearPriceTxt = (TextView) findViewById(R.id.detail_model_price);
        ImageView familyImage = (ImageView) findViewById(R.id.detail_image);
        maintenancePriceTxt = (EditText) findViewById(R.id.detail_model_maintenance);

        if(extras != null){
            TecnologyModel item = new TecnologyModel().deserializeModel(extras.getString("model"));
            modelNameTxt.setText(item.getModelName());

            modelName = item.getModelName();

            powerOrigin = extras.getInt("power");
            modelPowerTxt.setText(String.valueOf(powerOrigin));

            tecnologyDetailed = new Tecnology();
            tecnologyDetailed = tecnologyDetailed.DeserializeTecnology(extras.getString("tecnology"));


            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
            otherSymbols.setDecimalSeparator('.');

            DecimalFormat formatter = new DecimalFormat("#0.00", otherSymbols);
            formatter.setRoundingMode(RoundingMode.FLOOR);


            maintenance = tecnologyDetailed.getMaintenanceCost();
            maintenancePriceTxt.setText(formatter.format(maintenance));

            maintenanceYearPriceTxt.setText(formatter.format(tecnologyDetailed.calculateMaintenanceCost()));

            maintenancePriceTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Tecnology editable = tecnologyDetailed;
                    double newPrice = Double.parseDouble(v.getText().toString());

                    if(newPrice < 0){
                        newPrice *= -1;
                    }
                    editable.setMaintenanceCost(newPrice);

                    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
                    otherSymbols.setDecimalSeparator('.');

                    DecimalFormat formatter = new DecimalFormat("#0.00", otherSymbols);
                    formatter.setRoundingMode(RoundingMode.FLOOR);

                    maintenanceYearPriceTxt.setText(formatter.format(editable.calculateMaintenanceCost()));

                    return false;
                }
            });

                    familyNameTxt.setText(item.getFamilyName());
            ledSobstTxt.setText(item.getLedName());
            ledPowerTxt.setText(String.valueOf(item.getLedPower()));


            familyImage.setImageResource(OldTecFamilyTable.getItemFromName(item.getFamilyName()).getImageResources());

            newOrNot = extras.getInt("new_or_not");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        hideKeyboard();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:

                int powerCurrent = Integer.parseInt(modelPowerTxt.getText().toString());
                double maintenanceCurrent;
                try {
                    maintenanceCurrent = Double.parseDouble(maintenancePriceTxt.getText().toString());
                }catch (NumberFormatException e){
                    maintenanceCurrent = maintenance;
                }

                if(powerCurrent < 0){
                    powerCurrent *= -1;
                }
                if(maintenanceCurrent < 0){
                    maintenanceCurrent *= -1;
                }

                final int powerReturner = powerCurrent;
                final double maintenanceReturner = maintenanceCurrent;

                if(powerCurrent != powerOrigin) {

                    new AlertDialog.Builder(TecnologyDetailActivity.this)
                            .setTitle("Attenzione")
                            .setMessage("Il valore della potenza è stato modificato, salvare?")
                            .setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intnt = new Intent(TecnologyDetailActivity.this, ProfileTecnologyActivity.class);
                                    intnt.putExtra("new_or_not", newOrNot);
                                    intnt.putExtra("power_detail", powerReturner);
                                    intnt.putExtra("maintenance", maintenanceReturner);
                                    startActivity(intnt);
                                }
                            }).setNegativeButton(R.string.dialog_back, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intnt = new Intent(TecnologyDetailActivity.this, ProfileTecnologyActivity.class);
                                    intnt.putExtra("new_or_not", newOrNot);
                                    intnt.putExtra("power_detail", -1);
                                    intnt.putExtra("maintenance", maintenanceReturner);
                                    startActivity(intnt);
                                    return;
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();
                }else {

                    Intent intnt = new Intent(TecnologyDetailActivity.this, ProfileTecnologyActivity.class);
                    intnt.putExtra("new_or_not", newOrNot);
                    intnt.putExtra("power_detail", -1);
                    intnt.putExtra("maintenance", maintenanceReturner);
                    startActivity(intnt);
                }


                break;
        }


        return false;
    }

    public void resetPowerValue(View v){
        int powerFromList = DatabaseDataManager.getObjectFromName(modelName).getModelPowerWithTransformer();
        EditText powerTxt = (EditText) findViewById(R.id.detail_power_tec);
        powerTxt.setText(String.valueOf(powerFromList));
    }

    public void resetMaintenanceValue(View v){
        tecnologyDetailed.setMaintenanceCost(DatabaseDataManager.getMaintenanceCost(tecnologyDetailed.getInfos().getModel()));
        EditText powerTxt = (EditText) findViewById(R.id.detail_model_maintenance);
        powerTxt.setText(String.valueOf(tecnologyDetailed.getMaintenanceCost()));
    }


    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
