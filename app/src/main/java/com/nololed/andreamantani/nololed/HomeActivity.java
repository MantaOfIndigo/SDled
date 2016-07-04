package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nololed.andreamantani.nololed.Utils.DatabaseDataManager;
import com.nololed.andreamantani.nololed.Utils.SolarYearHours;

public class HomeActivity extends AppCompatActivity {

    boolean flag1 = false;
    boolean flag2 = false;

    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        rootLayout = (RelativeLayout)findViewById(R.id.root_layout);
        View.inflate(this, R.layout.splash_loading, rootLayout);

        new LoadSolarHours().execute();
        new LoadInnerDatabase().execute();
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
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
    }

    public void addNewSystem(View view) {
        startActivity(new Intent(this, NewSystemActivity.class));
        //startActivity(new Intent(this, SelectFamilyActivity.class));
    }

    private class LoadSolarHours extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try {
                synchronized (this) {
                    SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("current_system", null);
                    editor.putString("current_item", null);
                    editor.commit();

                    SolarYearHours.setSolarYear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            ImageView button = (ImageView) findViewById(R.id.home_button_add);
            button.setVisibility(View.GONE);

        }

        @Override
        protected void onPostExecute(Void result) {

            try {
                synchronized (this) {
                    flag1 = true;
                    if(flag2 == true) {
                        ImageView button = (ImageView) findViewById(R.id.home_button_add);
                        button.setVisibility(View.VISIBLE);

                        rootLayout.removeView(findViewById(R.id.splash_layout));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private class LoadInnerDatabase extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try{
                synchronized (this){
                    DatabaseDataManager.createList();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
            ImageView button = (ImageView) findViewById(R.id.home_button_add);
            button.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Void result){

            try {
                synchronized (this) {
                    flag2 = true;
                    if(flag1 == true) {
                        ImageView button = (ImageView) findViewById(R.id.home_button_add);
                        button.setVisibility(View.VISIBLE);

                        rootLayout.removeView(findViewById(R.id.splash_layout));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
