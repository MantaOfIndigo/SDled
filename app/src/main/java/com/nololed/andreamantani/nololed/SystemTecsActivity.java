package com.nololed.andreamantani.nololed;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
                sendEmail();
        }

        return true;
    }


    private void populateTable(String list){
        sys = new SystemTec();
        sys = sys.DeserializeSystem(list);

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
                View row = tbLayout.getChildAt(i);
                if(((TecnologyRecord) row).getMainLayout() == v){
                    Intent intent = new Intent(SystemTecsActivity.this , ProfileTecnologyActivity.class);
                    intent.putExtra("tec_info", ((TecnologyRecord) row).getTecnology().toString());
                    intent.putExtra("new_photo", 1);
                    startActivity(intent);
                }
            }
        }
    };

    private View.OnClickListener deleteIndexRow = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for(int i = 0; i < tbLayout.getChildCount(); i++){
                View row = tbLayout.getChildAt(i);
                if(((TecnologyRecord) row).getDeleteLayout()  == v){
                    sys.getList().remove(((TecnologyRecord) row).getTecnology().getIndex());

                    SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("current_system", sys.toString());
                    editor.commit();

                    finish();
                    startActivity(getIntent());
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        return;
    }

}
