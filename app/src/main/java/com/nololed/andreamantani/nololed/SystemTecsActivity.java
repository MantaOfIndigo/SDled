package com.nololed.andreamantani.nololed;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Model.Tecnology;
import com.nololed.andreamantani.nololed.Model.Records.TecnologyRecord;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import java.util.List;

/**
 * Created by andreamantani on 21/03/16.
 */
public class SystemTecsActivity extends AppCompatActivity{

    TableLayout tbLayout;
    SystemTec sys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_tecs);

        Utilities.itemInNewPlace = false;

        SharedPreferences sharedPreferences = getSharedPreferences("systems", Context.MODE_PRIVATE);
        String list = sharedPreferences.getString("current_system", null);
        Log.v("deser", "current: " + list);

        tbLayout = (TableLayout) findViewById(R.id.sys_tec_table);

        if(list != null){
            populateTable(list);
        }

/*
        TecnologyRecord uno = new TecnologyRecord(getBaseContext(), null, new Tecnology());
        uno.setOnClickListener(tablerowOnClickListener);
        TecnologyRecord due = new TecnologyRecord(getBaseContext(), null, new Tecnology());
        due.setOnClickListener(tablerowOnClickListener);

        tbLayout.addView(due);
        tbLayout.addView(uno);*/
        //tbLayout.setOnClickListener(tablerowOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_email_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mail:
                new AlertDialog.Builder(SystemTecsActivity.this)
                        .setTitle("Attenzione")
                        .setMessage("Vuoi aggiungere delle foto del sito?")
                        .setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Utilities.setSystem(sys);
                                Intent photo = new Intent(SystemTecsActivity.this, TakePhotoActivity.class);
                                photo.putExtra("is_more_photo", true);
                                photo.putExtra("origin", "gallery");
                                startActivity(photo);
                            }
                        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utilities.setSystem(sys);
                                startActivity(new Intent(SystemTecsActivity.this, PreviewEstimateActivity.class));
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }
                ).setIcon(android.R.drawable.ic_dialog_alert).show();

        }

        return true;
    }


    private void populateTable(String list){
        sys = new SystemTec();
        sys = sys.DeserializeSystem(list);

        tbLayout.removeAllViews();

        TextView nameText = (TextView) findViewById(R.id.sys_tec_name);
        nameText.setText(sys.getName());
        List<Tecnology> showTecs = sys.getList();

        for(int i = 0; i < showTecs.size();i++){
            TecnologyRecord record = new TecnologyRecord(getBaseContext(), null, showTecs.get(i), tablerowOnClickListener, deleteIndexRow);
            tbLayout.addView(record);
        }
    }

    private void populateTable(SystemTec list){
        sys = new SystemTec();
        sys = list;

        tbLayout.removeAllViews();

        TextView nameText = (TextView) findViewById(R.id.sys_tec_name);
        nameText.setText(sys.getName());
        List<Tecnology> showTecs = sys.getList();

        for(int i = 0; i < showTecs.size();i++){
            TecnologyRecord record = new TecnologyRecord(getBaseContext(), null, showTecs.get(i), tablerowOnClickListener, deleteIndexRow);
            tbLayout.addView(record);
        }
    }

    public void sendEmail(){
        sys.sendMail(SystemTecsActivity.this);
    }

    public void addNewTecnology(View v){
        Intent intent = new Intent(SystemTecsActivity.this , ProfileTecnologyActivity.class);
        startActivity(intent);
    }

    private View.OnClickListener tablerowOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0; i < tbLayout.getChildCount(); i++){
                final View row = tbLayout.getChildAt(i);
                if(((TecnologyRecord) row).getMainLayout() == v){
                    new AlertDialog.Builder(SystemTecsActivity.this)
                            .setTitle("Attenzione")
                            .setMessage("Vuoi modificare la tecnologia o copiare le informazioni?")
                            .setPositiveButton(R.string.dialog_modify, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendModify((TecnologyRecord) row);
                                }
                            }).setNegativeButton(R.string.dialog_copy, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendCopy((TecnologyRecord) row);
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }
        }
    };


    private void sendModify(TecnologyRecord row){
        Intent intent = new Intent(SystemTecsActivity.this , ProfileTecnologyActivity.class);
        intent.putExtra("tec_info", row.getTecnology().toString());
        intent.putExtra("new_photo", 1);
        intent.putExtra("item_new_place", false);
        startActivity(intent);
    }

    private void sendCopy(TecnologyRecord row){
        Intent intent = new Intent(SystemTecsActivity.this , ProfileTecnologyActivity.class);
        intent.putExtra("tec_info", row.getTecnology().toString());
        intent.putExtra("new_photo", 1);
        intent.putExtra("item_new_place", true);
        startActivity(intent);
    }

    private View.OnClickListener deleteIndexRow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0; i < tbLayout.getChildCount(); i++){
                final View row = tbLayout.getChildAt(i);
                if(((TecnologyRecord) row).getDeleteLayout()  == v){
                    new AlertDialog.Builder(SystemTecsActivity.this)
                            .setTitle("Attenzione")
                            .setMessage("Sicuro di voler rimuovere la tecnologia dall'elenco?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alertForRemove((TecnologyRecord) row);
                                }
                            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }
                    ).setIcon(android.R.drawable.ic_dialog_alert).show();

                }
            }
        }
    };


    private void alertForRemove(TecnologyRecord row){
        sys.getList().remove(((TecnologyRecord) row).getTecnology().getIndex());

        SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_system", sys.toString());
        editor.commit();

        populateTable(sys);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SystemTecsActivity.this.finish();

                        Intent intent = new Intent(SystemTecsActivity.this, HomeActivity.class);
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
