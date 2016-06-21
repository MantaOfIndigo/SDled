package com.nololed.andreamantani.nololed;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nololed.andreamantani.nololed.Utils.SolarYearHours;

/**
 * Created by andreamantani on 12/05/16.
 */
public class LoadingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new LoadViewTask().execute();

    }

    private class LoadViewTask extends AsyncTask<Void, Integer, Void>{
        @Override
        protected Void doInBackground(Void... params) {

            try{
                synchronized (this){
                    this.wait(850);
                    SolarYearHours.setSolarYear();
                }
            }catch(InterruptedException e){
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

           tester.setVisibility(View.GONE);

           try {
               synchronized (this) {
                   this.wait(500);
               }
               startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

       }

    }
}
