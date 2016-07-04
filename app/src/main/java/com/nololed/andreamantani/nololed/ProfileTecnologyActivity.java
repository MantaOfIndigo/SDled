package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nololed.andreamantani.nololed.Model.*;
import com.nololed.andreamantani.nololed.Model.Dialogs.WeekDialog;
import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Utils.Constants;
import com.nololed.andreamantani.nololed.Utils.DatabaseDataManager;
import com.nololed.andreamantani.nololed.Utils.SolarYearHours;
import com.nololed.andreamantani.nololed.Utils.StandardWorkHours;
import com.nololed.andreamantani.nololed.Utils.Utilities;

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
    Button model;
    //EditText power;
    int power = 0;
    Spinner tonality;
    EditText location;

    double maintenanceCost;
    boolean clickable;

    int hourInWeek = -1;
    int daysInYear = -1;
    int newOrNot = -1;

    int[] weekHoursDayPerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tecnology);


        newPhoto = (ImageView) findViewById(R.id.profile_tec_image);

        qta = (EditText) findViewById(R.id.profile_tec_qta);
        model = (Button) findViewById(R.id.profile_tec_model);
        //power = (EditText) findViewById(R.id.profile_tec_power);
        tonality = (Spinner) findViewById(R.id.profile_tec_tonality);
        location = (EditText) findViewById(R.id.profile_tec_description);

        LinearLayout setHours = (LinearLayout) findViewById(R.id.profile_tec_hours);
        setHours.setOnClickListener(setHourListener);


        setHoursAndDaysInWeek();
       /* if(SolarYearHours.isSolarYear()){
            hourInWeek = 0;
            daysInYear = SolarYearHours.getDayInYear();
        }else {
            int[] hoursInWeekArray = StandardWorkHours.getHoursNumber(false);
            hourInWeek = 0;
            for(int i = 0; i < hoursInWeekArray.length; i++){
                hourInWeek += hoursInWeekArray[i];
            }
            daysInYear = StandardWorkHours.daysCount();
        }*/


        qta.setText("1");
        location.setText("");

        ArrayAdapter spinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.tonality_array, android.R.layout.simple_spinner_item);
        tonality.setAdapter(spinnerArrayAdapter);
        tonality.setSelection(1);

        SharedPreferences sharedPreferences = getSharedPreferences("systems", Context.MODE_PRIVATE);
        String list = sharedPreferences.getString("current_system", null);

        String currItem = sharedPreferences.getString("current_item", null);

        if(currItem != null){
            Tecnology item = new Tecnology().DeserializeTecnology(currItem);
            qta.setText(String.valueOf(item.getQta()));

            model.setText(item.getInfos().getModel());
            power = item.getInfos().getPower();
            tonality.setSelection(item.getInfos().getTonalityItem());

            location.setText(item.getLocation());

            abspath = item.getPhoto().getPath();
            setGallery(abspath);

            if(item.getUsageDaysInYear() > 0){
                daysInYear = item.getUsageDaysInYear();
            }
            if(item.getUsageHourInWeekSum() > 0) {
                hourInWeek = item.getUsageHourInWeekSum();
                this.weekHoursDayPerDay = item.getUsageHourInWeek();
            }
        }

        sys = new SystemTec().DeserializeSystem(list);

        Bundle extras = getIntent().getExtras();

        if (extras == null && !SolarYearHours.isSolarYear()){
            StandardWorkHours.setCustomStandards();
        }



        if(extras != null){
            if(extras.getString("selected_model") != null){

                newOrNot = extras.getInt("new_or_not");
                model.setText(extras.getString("selected_model"));
                power = extras.getInt("custom_power");

            }

            if(extras.getInt("power_detail") > 0){
                power = extras.getInt("power_detail");
                newOrNot = extras.getInt("new_or_not");
                maintenanceCost = extras.getDouble("maintenance");
            }else if(extras.getInt("power_detail") == -1) {
                newOrNot = extras.getInt("new_or_not");
                maintenanceCost = extras.getDouble("maintenance");
            }

            if(extras.getBoolean("item_new_place")){
                Utilities.itemInNewPlace = true;
                Toast.makeText(this, "Modifica la locazione del modello", Toast.LENGTH_LONG).show();
                model.setEnabled(false);

                newPhoto.setClickable(false);
                clickable = false;
            }else{
                Utilities.itemInNewPlace = false;
                model.setEnabled(true);
                newPhoto.setClickable(true);
                clickable = true;
            }
        }else{
            clickable = true;
        }


        if(extras != null){
            if(extras.getInt("new_photo") == 0){
                if(extras.getString("url_image") != null){

                    Utilities.tecnologyIndexDisplayed = -1;

                    abspath = extras.getString("url_image");
                    setGallery(abspath);

                    newOrNot = 2;
                }
            }

            if(extras.getInt("new_photo") == 1) {
                if(extras.getString("url_image") != null){

                    abspath = extras.getString("url_image");
                    setGallery(abspath);
                    newOrNot = 1;
                    /*File sd = new File(abspath);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();

                    bmOptions.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(abspath, bmOptions);
                    float imageHeight = (float) bmOptions.outHeight;
                    float imageWidth = (float) bmOptions.outWidth;
                    int scaled = Math.round(imageWidth * (200 / imageHeight));

                    bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                    newPhoto.setImageBitmap(bitmap);*/
                }
                if (extras.getString("tec_info") != null) {

                    Tecnology tec = new Tecnology().DeserializeTecnology(extras.getString("tec_info"));
                    qta.setText(String.valueOf(tec.getQta()));
                    model.setText(tec.getInfos().getModel());
                    //power.setText(tec.getInfos().getPowerString());
                    power = tec.getInfos().getPower();
                    tonality.setSelection(tec.getInfos().getTonalityItem());
                    location.setText(tec.getLocation());

                    if(tec.getUsageDaysInYear() > 0){
                        daysInYear = tec.getUsageDaysInYear();
                    }
                    if(tec.getUsageHourInWeekSum() > 0) {
                        hourInWeek = tec.getUsageHourInWeekSum();
                        this.weekHoursDayPerDay = tec.getUsageHourInWeek();
                    }

                    newOrNot = 1;
                    Utilities.tecnologyIndexDisplayed = tec.getIndex();

                    Utilities.setOldItem(tec);

                    abspath = tec.getPhoto().getPath();

                    setGallery(abspath);
                    /*
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
                    */
                }
            }



            if(SolarYearHours.isSolarYear()) {
                TextView txtDaysInYear = (TextView) findViewById(R.id.profile_tec_text_days);
                txtDaysInYear.setText("Giorni annuali: " + daysInYear);

                TextView txtHourInAWeek = (TextView) findViewById(R.id.profile_tec_text_hour);
                txtHourInAWeek.setText("Ore settimanali: variabili a seconda del periodo");
            }else {
                TextView txtDaysInYear = (TextView) findViewById(R.id.profile_tec_text_days);


                TextView txtHourInAWeek = (TextView) findViewById(R.id.profile_tec_text_hour);

                if(daysInYear == -1 || hourInWeek == -1){

                    txtDaysInYear.setText("" + Constants.daysInYearStandard);
                    txtHourInAWeek.setText("" + Constants.hourInWeek);
                }else {
                    txtDaysInYear.setText("" + daysInYear);
                    txtHourInAWeek.setText("" + hourInWeek);
                }
            }

        }


    }

    private void setHoursAndDaysInWeek(){

        if(SolarYearHours.isSolarYear()){
            hourInWeek = 0;
            //INIZIALIZZAZIONE
            weekHoursDayPerDay = new int[1];
            weekHoursDayPerDay[0] = 0;
            daysInYear = SolarYearHours.getDayInYear();
        }else {
            weekHoursDayPerDay = StandardWorkHours.getHoursNumber(false);
            hourInWeek = 0;
            for(int i = 0; i < weekHoursDayPerDay.length; i++){
                hourInWeek += weekHoursDayPerDay[i];
            }
            daysInYear = StandardWorkHours.daysCount();
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
        if(clickable) {
            saveCurrentItem();
            try {
                Intent photo = new Intent(this, TakePhotoActivity.class);
                if (newOrNot != 1) {
                    photo.putExtra("new_photo", 0);
                }
                if (newOrNot == 1) {
                    photo.putExtra("new_photo", 1);
                }

                photo.putExtra("tec_folder_id", abspath);
                //photo.putExtra("tec_image_id", Utilities.getImageCounter());

                startActivity(photo);
            } catch (ActivityNotFoundException anfe) {
                anfe.printStackTrace();
            }
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

        Tecnology tec;


        if(abspath == null || abspath.equals("")){
            tec = new Tecnology(nQTA, Uri.parse(""), new Information(model.getText().toString(), String.valueOf(power) /*power.getText().toString()*/ , tonality.getSelectedItem().toString() /*daysYear.getText().toString())*/), location.getText().toString(), this.daysInYear, this.weekHoursDayPerDay);
        }else {
            tec = new Tecnology(nQTA, Uri.parse(abspath), new Information(model.getText().toString(), String.valueOf(power) /*power.getText().toString()*/ , tonality.getSelectedItem().toString() /*daysYear.getText().toString())*/), location.getText().toString(),this.daysInYear, this.weekHoursDayPerDay);
        }

        if(maintenanceCost > 1 && power > 0){
            tec.setMaintenanceCost(maintenanceCost);
        }

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
                    newTech.setUsageHourInWeek(this.weekHoursDayPerDay);


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
        if(maintenanceCost > 1){
            newTech.setMaintenanceCost(maintenanceCost);
        }

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
        if(maintenanceCost > 1){
            newTech.setMaintenanceCost(maintenanceCost);
        }

        sys.addList(newTech);

        editor.putString("current_system", sys.toString());
        editor.putString("current_item", null);
        editor.commit();
        return true;
    }

    private boolean control() {
        String qtaStr = qta.getText().toString();
        Information infos;



        if(model.getText().toString().isEmpty() || model.getText().toString().contains("SELEZIONA MODELLO")){
            Toast.makeText(ProfileTecnologyActivity.this, "Devi inserire tutti i campi",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tonality.getSelectedItem() != null) {
            infos = new Information(model.getText().toString(), /*power.getText().toString()*/ String.valueOf(power), tonality.getSelectedItem().toString() /*, daysYear.getText().toString()*/);
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

            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root_layout);
            View.inflate(ProfileTecnologyActivity.this, R.layout.splash_loading, rootLayout);

            if(!SolarYearHours.isSolarYear()) {
                Dialog weekDialog = new Dialog(ProfileTecnologyActivity.this);
                weekDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                weekDialog.setContentView(new WeekDialog(ProfileTecnologyActivity.this, null, weekDialog));

                weekDialog.show();
            }else{
                new AlertDialog.Builder(ProfileTecnologyActivity.this)
                        .setTitle("Attenzione")
                        .setMessage("Hai selezionato il calendario solare, non potrai modificare l'orario delle tecnologie")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }


            rootLayout.removeView(findViewById(R.id.splash_layout));

        }
    };

    private View.OnClickListener imageListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root_layout);
            View.inflate(ProfileTecnologyActivity.this, R.layout.splash_loading, rootLayout);

            tapImage(v);
            rootLayout.removeView(findViewById(R.id.splash_layout));
        }
    };

    private View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(clickable) {
                File folder = new File(abspath);

                for (int i = 0; i < folder.listFiles().length; i++) {

                    ProfilePhotoItem item = (ProfilePhotoItem) v.getParent().getParent();
                    if (item.getImagePath().equals(folder.listFiles()[i].getAbsolutePath())) {
                        File deleter = new File(item.getImagePath());
                        deleter.delete();
                        setGallery(folder.getAbsolutePath());
                    }

                }
            }
        }
    };


    public void setHoursNumber(int[] hoursNumber){
        weekHoursDayPerDay = hoursNumber;
        TextView txtHour = (TextView) findViewById(R.id.profile_tec_text_hour);

        this.hourInWeek = 0;
        for(int i = 0; i < weekHoursDayPerDay.length; i++) {
            this.hourInWeek += weekHoursDayPerDay[i];
        }


        txtHour.setText("" + this.hourInWeek);
    }

    public void setDaysInYear(){
        int value = StandardWorkHours.daysCount();
        TextView txtHour = (TextView) findViewById(R.id.profile_tec_text_days);
        txtHour.setText("" + value);
        this.daysInYear = value;
    }

    private void setGallery(String folderPath){

        if(folderPath.equals("") || folderPath == null || folderPath.equals("none")){
            return;
        }


        LinearLayout content = (LinearLayout) findViewById(R.id.profile_scroll_photo_content);
        content.removeAllViews();

        File folder = new File(folderPath);



        ImageView image = (ImageView) findViewById(R.id.profile_tec_image);

        if (folder.isDirectory()) {
            for (File child : folder.listFiles()) {


                //BitmapFactory.Options bmOptions = new BitmapFactory.Options();

                //bmOptions.inSampleSize = 2;
                //Bitmap bitmap = BitmapFactory.decodeFile(child.getPath(), bmOptions);
                //float imageHeight = (float) bmOptions.outHeight;
                //float imageWidth = (float) bmOptions.outWidth;
                //int scaled = Math.round(imageWidth * (200 / imageHeight));

                //bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 200, true);
                ProfilePhotoItem newItem = new ProfilePhotoItem(ProfileTecnologyActivity.this, null, imageListener , child.getAbsolutePath(), delete, 370);
                //newItem.setImageBitmap(bitmap);

                content.addView(newItem);
                //filePhoto = new File(child.getPath());
            }
        }


        if(folder.listFiles().length == 0) {
            content.addView(new ProfilePhotoItem(this, null, imageListener));
        }


    }


    private void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public void viewDetail(View v){
        if(power != 0) {
            saveCurrentItem();
            Intent intnt = new Intent(ProfileTecnologyActivity.this, TecnologyDetailActivity.class);
            TecnologyModel item = DatabaseDataManager.getObjectFromName(model.getText().toString());


            SharedPreferences sharedPreferences = getSharedPreferences("systems", Context.MODE_PRIVATE);
            String currItem = sharedPreferences.getString("current_item", null);


            intnt.putExtra("model", item.serializeModel());
            intnt.putExtra("tecnology", currItem);
            intnt.putExtra("power", power);
            intnt.putExtra("new_or_not", newOrNot);

            startActivity(intnt);
        }else{
            Toast.makeText(this, "Seleziona un modello", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectModel(View v){
        if(clickable) {
            saveCurrentItem();
            Intent intnt = new Intent(ProfileTecnologyActivity.this, SelectFamilyActivity.class);
            intnt.putExtra("new_item", newOrNot);
            startActivity(intnt);
        }else{
            Toast.makeText(this, "Impossibile - Modalità copia", Toast.LENGTH_SHORT).show();
        }
    }

}