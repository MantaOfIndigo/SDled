package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nololed.andreamantani.nololed.Model.*;
import com.nololed.andreamantani.nololed.Model.Dialogs.HoursDialog;
import com.nololed.andreamantani.nololed.Model.Dialogs.SingleDateDialog;
import com.nololed.andreamantani.nololed.Model.Dialogs.WeekDialog;
import com.nololed.andreamantani.nololed.Model.Records.*;
import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import org.w3c.dom.Text;

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
    EditText model;
    EditText power;
    Spinner tonality;
    EditText location;

    int hourInWeek;
    int daysInYear;

    int newOrNot = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tecnology);


        newPhoto = (ImageView) findViewById(R.id.profile_tec_image);

        qta = (EditText) findViewById(R.id.profile_tec_qta);
        model = (EditText) findViewById(R.id.profile_tec_model);
        power = (EditText) findViewById(R.id.profile_tec_power);
        tonality = (Spinner) findViewById(R.id.profile_tec_tonality);
        location = (EditText) findViewById(R.id.profile_tec_description);

        LinearLayout setHours = (LinearLayout) findViewById(R.id.profile_tec_hours);
        setHours.setOnClickListener(setHourListener);


        hourInWeek = StandardWorkHours.getHoursNumber(false);
        daysInYear = StandardWorkHours.daysCount();


        qta.setText("1");
        location.setText("");

        ArrayAdapter spinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.tonality_array, android.R.layout.simple_spinner_item);
        tonality.setAdapter(spinnerArrayAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("systems", Context.MODE_PRIVATE);
        String list = sharedPreferences.getString("current_system", null);

        String currItem = sharedPreferences.getString("current_item", null);

        if(currItem != null){
            Tecnology item = new Tecnology().DeserializeTecnology(currItem);
            qta.setText(String.valueOf(item.getQta()));

            model.setText(item.getInfos().getModel());
            power.setText(item.getInfos().getPowerString());
            tonality.setSelection(item.getInfos().getTonalityItem());

            location.setText(item.getLocation());

            if(item.getUsageDaysInYear() > 0){
                daysInYear = item.getUsageDaysInYear();
            }
            if(item.getUsageHourInYear() > 0) {
                hourInWeek = item.getUsageHourInYear();
            }
        }

        sys = new SystemTec().DeserializeSystem(list);

        Bundle extras = getIntent().getExtras();

        if (extras == null){
            StandardWorkHours.setCustomStandards();
        }


        if(extras != null){
            if(extras.getBoolean("item_new_place")){
                Utilities.itemInNewPlace = true;
                Toast.makeText(this, "Modifica la locazione del modello", Toast.LENGTH_LONG).show();
                model.setEnabled(false);
                newPhoto.setClickable(false);
            }else{
                Utilities.itemInNewPlace = false;
                model.setEnabled(true);
                newPhoto.setClickable(true);
            }
        }


        if(extras != null){
            if(extras.getInt("new_photo") == 0){
                if(extras.getString("url_image") != null){

                    Utilities.tecnologyIndexDisplayed = -1;

                    abspath = extras.getString("url_image");

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

                    bmOptions.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(abspath, bmOptions);
                    float imageHeight = (float) bmOptions.outHeight;
                    float imageWidth = (float) bmOptions.outWidth;
                    int scaled = Math.round(imageWidth * (200 / imageHeight));

                    bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                    newPhoto.setImageBitmap(bitmap);
                }
                if (extras.getString("tec_info") != null) {

                    Tecnology tec = new Tecnology().DeserializeTecnology(extras.getString("tec_info"));
                    qta.setText(String.valueOf(tec.getQta()));
                    model.setText(tec.getInfos().getModel());
                    power.setText(tec.getInfos().getPowerString());
                    tonality.setSelection(tec.getInfos().getTonalityItem());
                    location.setText(tec.getLocation());

                    if(tec.getUsageDaysInYear() > 0){
                        daysInYear = tec.getUsageDaysInYear();
                    }
                    if(tec.getUsageHourInYear() > 0) {
                        hourInWeek = tec.getUsageHourInYear();
                    }

                    newOrNot = 1;
                    Utilities.tecnologyIndexDisplayed = tec.getIndex();

                    Utilities.setOldItem(tec);

                    abspath = tec.getPhoto().getPath();

                    File sd = new File(abspath);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();

                    bmOptions.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(abspath, bmOptions);
                    float imageHeight = (float) bmOptions.outHeight;
                    float imageWidth = (float) bmOptions.outWidth;
                    int scaled = Math.round(imageWidth * (200 / imageHeight));

                    bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                    newPhoto.setImageBitmap(bitmap);

                    filePhoto = new File(tec.getPhoto().getPath());
                }
            }



            TextView txtDaysInYear = (TextView) findViewById(R.id.profile_tec_text_days);
            txtDaysInYear.setText("Giorni annuali: " + daysInYear);

            TextView txtHourInAWeek = (TextView) findViewById(R.id.profile_tec_text_hour);
            txtHourInAWeek.setText("Ore settimanali: " + hourInWeek);

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

        Tecnology tec = new Tecnology(nQTA, Uri.parse("none"), new Information(model.getText().toString(), power.getText().toString(), tonality.getSelectedItem().toString() /*daysYear.getText().toString())*/ ), location.getText().toString());
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

                    newTech.setUsageDaysInYear(this.daysInYear);
                    newTech.setUsageHourInYear(this.hourInWeek);

                    if(Utilities.itemInNewPlace && Utilities.isNotOldItem(newTech)){

                        new AlertDialog.Builder(ProfileTecnologyActivity.this)
                                .setTitle("Attenzione")
                                .setMessage("La stessa tecnologia è presente in una diversa locazione, aggiungere comunque?")
                                .setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Utilities.resetOldItem();
                                        Utilities.itemInNewPlace = false;
                                        saveNewTecnology();

                                        Utilities.addLocation(location.getText().toString());
                                        startActivity(new Intent(ProfileTecnologyActivity.this, SystemTecsActivity.class));
                                    }
                                }).setNegativeButton(R.string.dialog_back, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }
                        ).setIcon(android.R.drawable.ic_dialog_alert).show();
                    }else{

                        if(Utilities.itemInNewPlace){
                            Toast.makeText(this, "Modifica la locazione", Toast.LENGTH_LONG).show();
                        }else{

                            if(newOrNot == 1){
                                modifyTecnology();
                            }
                            if(newOrNot == 2){
                                saveNewTecnology();
                            }


                            Utilities.addLocation(location.getText().toString());
                            startActivity(new Intent(this, SystemTecsActivity.class));
                        }

                    }


                } else {
                    Toast.makeText(ProfileTecnologyActivity.this, "Devi inserire tutti i campi", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
        }

        return true;
    }

    private boolean modifyTecnology(){
        sys.getList().remove(Utilities.tecnologyIndexDisplayed);
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

        editor.putString("current_system", sys.toString());
        editor.putString("current_item", null);
        editor.commit();
        return true;
    }

    private boolean control() {
        String qtaStr = qta.getText().toString();
        Information infos;



        if(model.getText().toString().isEmpty()){
            Toast.makeText(ProfileTecnologyActivity.this, "Devi inserire tutti i campi",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tonality.getSelectedItem() != null) {
            infos = new Information(model.getText().toString(), power.getText().toString(), tonality.getSelectedItem().toString() /*, daysYear.getText().toString()*/);
        }else{
            Toast.makeText(ProfileTecnologyActivity.this, "Devi selezionare una tonalità", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(location.getText().toString().isEmpty()){
            Toast.makeText(ProfileTecnologyActivity.this, "Devi inserire tutti i campi", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (infos.getDaysForYear() != -1 && infos.getTonality() != -1 && infos.getPower() != -1) {
            if (qtaStr != null && abspath != null) {
                newTech = new Tecnology(Integer.parseInt(qtaStr), Uri.parse(abspath), infos, location.getText().toString());
                return true;
            }
        }


        return false;
    }


    /*private boolean isSameModel(){
        if(sys.doesModelInSamePositionExists(model.getText().toString(), location.getText().toString())) {
            new AlertDialog.Builder(ProfileTecnologyActivity.this)
                    .setTitle("Attenzione")
                    .setMessage("Il modello è già memorizzato nella seguente locazione.")
                    .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SystemTecsActivity.class));
    }

    private View.OnClickListener setHourListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Dialog weekDialog = new Dialog(ProfileTecnologyActivity.this);
            weekDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            weekDialog.setContentView(new WeekDialog(ProfileTecnologyActivity.this, null, weekDialog));

            weekDialog.show();

        }
    };

    public void setHoursNumber(int hoursNumber){
        TextView txtHour = (TextView) findViewById(R.id.profile_tec_text_hour);
        txtHour.setText("Ore settimanali: " + hoursNumber);
        this.hourInWeek = hoursNumber;
    }

    public void setDaysInYear(){
        int value = StandardWorkHours.daysCount();
        TextView txtHour = (TextView) findViewById(R.id.profile_tec_text_days);
        txtHour.setText("Giorni annuali: " + value);
        this.daysInYear = value;
    }


}