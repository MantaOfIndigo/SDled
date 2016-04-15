package com.nololed.andreamantani.nololed;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nololed.andreamantani.nololed.Model.*;
import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Utils.Counter;

import java.io.File;

/**
 * Created by andreamantani on 23/03/16.
 */
public class ProfileTecnologyActivity extends AppCompatActivity {


    SystemTec sys;
    Tecnology newTech;
    File filePhoto;
    String abspath;
    ImageView newPhoto;
    EditText qta;
    EditText power;
    EditText hour;
    EditText daysYear;
    EditText description;

    int newOrNot = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tecnology);


        newPhoto = (ImageView) findViewById(R.id.profile_tec_image);

        qta = (EditText) findViewById(R.id.profile_tec_qta);
        power = (EditText) findViewById(R.id.profile_tec_power);
        hour = (EditText) findViewById(R.id.profile_tec_hour);
        //daysYear = (EditText) findViewById(R.id.profile_tec_days_year);
        description = (EditText) findViewById(R.id.profile_tec_description);

        qta.setText("1");
        description.setText("");

        SharedPreferences sharedPreferences = getSharedPreferences("systems", Context.MODE_PRIVATE);
        String list = sharedPreferences.getString("current_system", null);

        String currItem = sharedPreferences.getString("current_item", null);

        if(currItem != null){
            Tecnology item = new Tecnology().DeserializeTecnology(currItem);
            qta.setText(String.valueOf(item.getQta()));

            power.setText(item.getInfos().getPowerString());
            hour.setText(item.getInfos().getHourString());
            daysYear.setText(item.getInfos().getDaysForYearString());


            description.setText(item.getDesc());
        }

        sys = new SystemTec().DeserializeSystem(list);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.getInt("new_photo") == 0){
                if(extras.getString("url_image") != null){

                    Counter.tecnologyIndexDisplayed = -1;

                    abspath = extras.getString("url_image");

                    Log.v("lastcheck", abspath);
                    File sd = new File(abspath);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    //bmOptions.inJustDecodeBounds = true;
                    bmOptions.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(abspath, bmOptions);
                    float imageHeight = (float) bmOptions.outHeight;
                    float imageWidth = (float) bmOptions.outWidth;
                    int scaled = Math.round(imageWidth * (200 / imageHeight));
                    Log.v("check", "bound: " + sd.getAbsolutePath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                    newPhoto.setImageBitmap(bitmap);

                    newOrNot = 2;
                }
            }

            if(extras.getInt("new_photo") == 1) {
                if(extras.getString("url_image") != null){

                    abspath = extras.getString("url_image");
                    newOrNot = 1;
                    File sd = new File(abspath);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    //bmOptions.inJustDecodeBounds = true;
                    bmOptions.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(abspath, bmOptions);
                    float imageHeight = (float) bmOptions.outHeight;
                    float imageWidth = (float) bmOptions.outWidth;
                    int scaled = Math.round(imageWidth * (200 / imageHeight));
                    Log.v("check", "bound: " + sd.getAbsolutePath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                    newPhoto.setImageBitmap(bitmap);
                }
                if (extras.getString("tec_info") != null) {

                    Tecnology tec = new Tecnology().DeserializeTecnology(extras.getString("tec_info"));
                    qta.setText(String.valueOf(tec.getQta()));
                    power.setText(tec.getInfos().getPowerString());
                    hour.setText(tec.getInfos().getHourString());
                    daysYear.setText(tec.getInfos().getDaysForYearString());
                    description.setText(tec.getDesc());

                    newOrNot = 1;
                    Counter.tecnologyIndexDisplayed = tec.getIndex();



                    abspath = tec.getPhoto().getPath();

                    File sd = new File(abspath);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    //bmOptions.inJustDecodeBounds = true;
                    bmOptions.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(abspath, bmOptions);
                    float imageHeight = (float) bmOptions.outHeight;
                    float imageWidth = (float) bmOptions.outWidth;
                    int scaled = Math.round(imageWidth * (200 / imageHeight));
                    Log.v("check", "bound: " + sd.getAbsolutePath());
                    bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                    newPhoto.setImageBitmap(bitmap);
                    //bitmap.recycle();

                    filePhoto = new File(tec.getPhoto().getPath());
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        hideKeyboard();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void tapImage(View v) {
        saveCurrentItem();
        try {
            Intent photo = new Intent(this, TakePhotoActivity.class);
            if(newOrNot != 1) {
                photo.putExtra("new_photo", 0);
            }
            if(newOrNot == 1){
                photo.putExtra("new_photo", 1);
            }
            startActivity(photo);
        } catch (ActivityNotFoundException anfe) {
            anfe.printStackTrace();
        }
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void saveCurrentItem(){
        SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int nQTA = -1;
        if(qta.getText().toString() != null){
            nQTA = Integer.parseInt(qta.getText().toString());
        }

        Tecnology tec = new Tecnology(nQTA, Uri.parse("none"), new Information(power.getText().toString(), hour.getText().toString(), /*daysYear.getText().toString())*/ "7"), description.getText().toString());
        Log.v("cont", "sysstring: " + sys.toString());
        editor.putString("current_item", tec.toString());
        editor.commit();
    }

    public void plusCount(View v) {
        hideKeyboard();

        TextView numberText = (TextView) findViewById(R.id.profile_tec_qta);

        String number = numberText.getText().toString();
        int num = 0;
        try {
            num = Integer.parseInt(number);
            num++;
        } catch (NumberFormatException e) {
            num = 0;
        }

        qta.setText(String.valueOf(num));
    }

    public void minusCount(View v) {
        hideKeyboard();

        TextView numberText = (TextView) findViewById(R.id.profile_tec_qta);

        String number = numberText.getText().toString();
        int num = 0;
        try {
            num = Integer.parseInt(number);
            if (num > 1) {
                num--;
            }

        } catch (NumberFormatException e) {
            num = 0;
        }

        qta.setText(String.valueOf(num));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                if (control()) {
                    if(newOrNot == 1){
                        modifyTecnology();
                    }
                    if(newOrNot == 2){
                        saveNewTecnology();
                    }
                    startActivity(new Intent(this, SystemTecsActivity.class));
                } else {
                    Toast.makeText(ProfileTecnologyActivity.this, "Devi inserire tutti i campi", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
        }

        return true;
    }

    private boolean modifyTecnology(){
        sys.getList().remove(Counter.tecnologyIndexDisplayed);
        sys.addList(newTech);

        SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_system", sys.toString());
        editor.putString("current_item", null);
        editor.commit();
        return true;
    }
    private boolean saveNewTecnology() {
        SharedPreferences sharedPref = getSharedPreferences("systems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        sys.addList(newTech);
        Log.v("cont", "sysstring: " + sys.toString());
        editor.putString("current_system", sys.toString());
        editor.putString("current_item", null);
        editor.commit();
        return true;
    }

    private boolean control() {
        String qtaStr = qta.getText().toString();

        Information infos = new Information(power.getText().toString(), hour.getText().toString(), daysYear.getText().toString());

        // if(infos.getPower() >= TOT)
        if (infos.getHour() > 24 || infos.getHour() < 1){
            Toast.makeText(ProfileTecnologyActivity.this, "Il numero di ore è errato", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infos.getDaysForYear() > 365 || infos.getDaysForYear() < 1) {
            Toast.makeText(ProfileTecnologyActivity.this, "Il numero di giorni è errato", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(description.getText().toString().isEmpty()){
            return false;
        }

        if (infos.getDaysForYear() != -1 && infos.getHour() != -1 && infos.getPower() != -1) {
            if (qtaStr != null) {
                newTech = new Tecnology(Integer.parseInt(qtaStr), Uri.parse(abspath), infos, description.getText().toString());
                return true;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SystemTecsActivity.class));
    }



}