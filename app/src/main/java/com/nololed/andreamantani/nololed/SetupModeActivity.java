package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.nololed.andreamantani.nololed.Utils.SolarYearHours;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;

/**
 * Created by andreamantani on 05/05/16.
 */
public class SetupModeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_mode);

        RelativeLayout lManual = (RelativeLayout) findViewById(R.id.mode_manual);
        RelativeLayout l24h = (RelativeLayout) findViewById(R.id.mode_24);
        RelativeLayout lSolar = (RelativeLayout) findViewById(R.id.mode_solar);

        lManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetupModeActivity.this, HourSetUpActivity.class));
            }
        });

        l24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardWorkHours.set24hStandard();
                startActivity(new Intent(SetupModeActivity.this, WeekSetUpActivity.class));
            }
        });

        lSolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolarYearHours.selectSolarCalendar();
                new AlertDialog.Builder(SetupModeActivity.this)
                        .setTitle("Uscita")
                        .setMessage("Impostare un orario solare 24h o personalizzarlo?")
                        .setPositiveButton("Personalizza", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(SetupModeActivity.this, SetupSolarActivity.class));
                            }
                        }).setNeutralButton("24h", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(SetupModeActivity.this, WeekSetUpActivity.class));
                            }
                        }).setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SolarYearHours.deselectSolarCalendar();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SetupModeActivity.this.finish();
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
        }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
