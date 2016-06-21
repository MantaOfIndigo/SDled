package com.nololed.andreamantani.nololed;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.Model.SystemTec;

import java.io.File;

/**
 * Created by andreamantani on 25/03/16.
 */
public class NewSystemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_system);
    }

    public void toAddTecnology(View v){
        String name = ((TextView) findViewById(R.id.new_system_name)).getText().toString();

        String dir = name.replace(" ", "_");
        dir = dir.replace("?", "DOT7");
        dir = dir.replace(".", "DOT8");

        dir = dir.replace(".", "DOT8");
        Log.v("lastcheck", dir);

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Nololed");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }



        if (success) {

            File innerFolder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "Nololed" + File.separator + dir);
            boolean innerSuccess = true;
            if (!innerFolder.exists()) {
                innerSuccess = innerFolder.mkdir();
            }
            if (innerSuccess) {

            }
        }


        if(name != null) {
            SystemTec newSystem = new SystemTec(dir);

            SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("current_system", newSystem.toString());
            editor.commit();


            Intent intent = new Intent(NewSystemActivity.this, SetupModeActivity.class);
            intent.putExtra("new_system_name", dir);
            intent.putExtra("new_photo", 0);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NewSystemActivity.this.finish();
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


}
