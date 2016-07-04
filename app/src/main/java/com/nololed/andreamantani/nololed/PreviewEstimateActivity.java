package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.Model.Records.LedPreviewRecord;
import com.nololed.andreamantani.nololed.Model.Records.PreviewRecord;
import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Model.Tecnology;
import com.nololed.andreamantani.nololed.Model.TecnologyModel;
import com.nololed.andreamantani.nololed.Utils.Constants;
import com.nololed.andreamantani.nololed.Utils.SolarYearHours;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import org.w3c.dom.Text;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 08/05/16.
 */
public class PreviewEstimateActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {


    String[] values = {"0.15","0.16","0.17","0.18","0.19","0.20","0.21","0.22","0.23","0.24","0.25","0.26","0.27"};
    Double energy = 0.0;
    boolean noReturn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        new AlertDialog.Builder(PreviewEstimateActivity.this)
                .setTitle("Costo Energia")
                .setMessage("Modificare il costo dell'energia? \n(Standard: " + Constants.getPriceForEnergy() + ")")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        show();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        populateList();
                        return;
                    }
                }
        ).setIcon(android.R.drawable.ic_dialog_alert).show();

        populateList();





    }

    private void populateList(){



        double totalLed = 0;
        double totalCurrent = 0;

        List<Tecnology> estimate =  Utilities.getSystem().getList();
        LinearLayout content = (LinearLayout) findViewById(R.id.preview_content);

        content.removeAllViews();

        for(int i = 0; i < estimate.size(); i++){
            int qta = estimate.get(i).getQta();
            double price = new SystemTec().getPricePartial(estimate.get(i));
            String model = estimate.get(i).getInfos().getModel();
            content.addView(new PreviewRecord(PreviewEstimateActivity.this, null, model, price, qta));
        }

        List<TecnologyModel> models = Utilities.getSystem().getLedSystem();


        LinearLayout ledContent = (LinearLayout) findViewById(R.id.led_content);
        ledContent.removeAllViews();

        List<Double> prices = Utilities.getSystem().calculateLedPrices();


        for(int i = 0; i < models.size(); i++){
            double price = prices.get(i);
            totalLed+=price;
            String model = models.get(i).getLedName();
            ledContent.addView(new LedPreviewRecord(PreviewEstimateActivity.this, null, model, price));

        }

        TextView totalLedTxt = (TextView) findViewById(R.id.preview_led_txt_price);
        TextView total = (TextView) findViewById(R.id.preview_txt_price);

        TextView monthlyRateTxt = (TextView) findViewById(R.id.preview_nolo_month);
        TextView yearlyRateTxt = (TextView) findViewById(R.id.preview_nolo_year);


        TextView differenceTxt = (TextView) findViewById(R.id.preview_txt_difference);
        TextView maintenanceTxt = (TextView) findViewById(R.id.preview_maintenance_year);
        TextView totalNoloCostTxt = (TextView) findViewById(R.id.preview_total_nolo_cost);
        TextView totalCurrCostTxt = (TextView) findViewById(R.id.preview_total_current_cost);




        DecimalFormat formatter = new DecimalFormat("#0.00");
        formatter.setRoundingMode(RoundingMode.FLOOR);

        totalCurrent = Utilities.getSystem().calculatePrice();

        double maintenanceCost = Utilities.getSystem().calculateMaintenanceCost();

        double differencePrice = totalCurrent + maintenanceCost - totalLed;
        double monthlyRatePrice = Utilities.getSystem().getMonthlyRateForNolo();

        double totalYearNoloCost =  totalLed + (monthlyRatePrice * 12);

        total.setText(formatter.format(totalCurrent));
        totalLedTxt.setText(formatter.format(totalLed));
        monthlyRateTxt.setText(formatter.format(monthlyRatePrice));
        yearlyRateTxt.setText(formatter.format(monthlyRatePrice * 12));
        maintenanceTxt.setText(formatter.format(maintenanceCost));
        totalNoloCostTxt.setText(formatter.format(totalYearNoloCost));
        totalCurrCostTxt.setText(formatter.format(maintenanceCost + totalCurrent));

        FrameLayout decoration = (FrameLayout) findViewById(R.id.preview_layout_decoration);

        if(differencePrice > 0){
            decoration.setBackgroundResource(R.color.custom_bright_green);
            differenceTxt.setText("+" + formatter.format(differencePrice));
        }else{
            decoration.setBackgroundResource(R.color.custom_red_error);
            differenceTxt.setText(formatter.format(differencePrice));
        }



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                new AlertDialog.Builder(PreviewEstimateActivity.this)
                        .setTitle("Costo Energia")
                        .setMessage("Modificare il costo dell'energia? \n(Standard: " + Constants.getPriceForEnergy() + ")")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                show();
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                populateList();
                                return;
                            }
                        }
                ).setIcon(android.R.drawable.ic_dialog_alert).show();
                break;
            case R.id.action_mail:
                    new LoadViewTask().execute();
                break;
        }

        return false;
    }

    public void deleteFolder(){
        String folderPath = Utilities.getSystem().getName();

        String folderName = folderPath.replace(" ", "_");
        folderName = folderName.replace("?", "DOT7");
        folderName = folderName.replace(".", "DOT8");

        folderName = folderName.replace(".", "DOT8");

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Nololed" + File.separator + folderName);

        deleteRecursive(folder);

    }


    private void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();

    }

    @Override
    public void onBackPressed(){
        if(noReturn) {
            new AlertDialog.Builder(this)
                    .setTitle("Uscita")
                    .setMessage("Sei sicuro di voler uscire?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            PreviewEstimateActivity.this.finish();

                            Intent intent = new Intent(PreviewEstimateActivity.this, HomeActivity.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else {
            startActivity(new Intent(PreviewEstimateActivity.this, SystemTecsActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_value_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void show(){
        final Dialog d = new Dialog(PreviewEstimateActivity.this);

        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_energy);
        Button b1 = (Button) d.findViewById(R.id.energy_no);
        Button b2 = (Button) d.findViewById(R.id.energy_ok);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.energy_number_picker);


        np.setMaxValue(values.length-1);
        np.setMinValue(0);
        np.setDisplayedValues(values);
        np.setValue(6); // 0.22 prezzo standard
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Constants.setPriceForEnergy(energy);
                populateList();

                d.dismiss();
            }
        });
        d.show();

    }



    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        energy = Double.parseDouble(values[newVal]);

    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try{
                synchronized (this){
                    Utilities.getSystem().sendMail(PreviewEstimateActivity.this);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected void onPostExecute(Void result){
            ProgressBar tester = (ProgressBar) findViewById(R.id.loading_bar);
            try{
                synchronized (this){

                    wait(5000);

                    new AlertDialog.Builder(PreviewEstimateActivity.this)
                            .setTitle("Eliminare foto?")
                            .setMessage("Vuoi eliminare le fotografie dal dispositivo? Non potrai più effettuare modifiche al preventivo.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteFolder();
                                    noReturn = true;
                                }
                            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();

                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }
}
