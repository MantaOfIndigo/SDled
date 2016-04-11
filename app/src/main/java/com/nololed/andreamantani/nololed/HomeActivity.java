package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_system", null);
        editor.putString("current_item", null);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        return;
    }
    public void addNewSystem(View view){
        startActivity(new Intent(this, NewSystemActivity.class));
    }
}
