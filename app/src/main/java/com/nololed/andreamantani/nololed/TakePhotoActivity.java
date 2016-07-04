package com.nololed.andreamantani.nololed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.nololed.andreamantani.nololed.Model.SystemTec;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by andreamantani on 28/03/16.
 */
public class TakePhotoActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 1;

    SystemTec sys;
    File filePhoto;
    String abspath;
    String tecFolderName;
    Integer tecImageId;
    Uri tecPhotoPath;

    boolean isMorePhoto;
    boolean firstTime;
    boolean gallery;

    int newPhoto = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("systems", Context.MODE_PRIVATE);
        String list = sharedPreferences.getString("current_system", null);
        sys = new SystemTec().DeserializeSystem(list);
        Bundle extras = getIntent().getExtras();

        if(extras.getBoolean("is_more_photo")){
            this.isMorePhoto = true;
        }else{
            this.isMorePhoto = false;
            Toast.makeText(TakePhotoActivity.this, "Fotografa una tecnologia", Toast.LENGTH_LONG).show();
        }

        if(extras.getString("origin") != null) {
            if (extras.getString("origin").equals("first")) {
                firstTime = true;
            } else {
                firstTime = false;
            }

            if (extras.getString("origin").equals("gallery")) {
                gallery = true;
            } else {
                gallery = false;
            }
        }


        if(!gallery){
            if(extras.getString("tec_folder_id") != null){
                tecFolderName = extras.getString("tec_folder_id");
                //tecImageId = extras.getInt("tec_image_id");
            }else{
                tecFolderName = "";
            }
        }

        if(extras.getInt("new_photo") == 0) {
            newPhoto = 0;
        }
        if(extras.getInt("new_photo") == 1){
            newPhoto = 1;
        }
        dispatchTakePictureIntent();

    }


    private void returnFunction(){
        Toast.makeText(TakePhotoActivity.this, "Si è verificato un errore", Toast.LENGTH_SHORT).show();

        if(firstTime){
            Intent back = new Intent(this, AddSingleHolidays.class);
            startActivity(back);
            return;

        }else if(gallery){
            Intent back = new Intent(this, ImagesListActivity.class);
            startActivity(back);
            return;
        } else{

            if(isMorePhoto){
                Intent back = new Intent(this, ImagesListActivity.class);
                back.putExtra("url_image", abspath);
                startActivity(back);
            }else if(!isMorePhoto){
                if(abspath == null){
                    Toast.makeText(TakePhotoActivity.this, "Scatta una foto", Toast.LENGTH_SHORT).show();
                    Intent reset = getIntent();
                    finish();
                    startActivity(reset);
                }else {
                    Intent back = new Intent(this, ProfileTecnologyActivity.class);
                    back.putExtra("url_image", abspath);
                    back.putExtra("new_photo", newPhoto);
                    startActivity(back);
                }
            }
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
             returnFunction();
        }

        if (requestCode == PICK_FROM_CAMERA && resultCode != RESULT_CANCELED) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            newPhoto.setImageBitmap(imageBitmap);
            tecPhotoPath = Uri.f
            //tecPhotoPath = data.getData();
            //Log.v("cont", "filepath: " + tecPhotoPath.getPath());*/

            try{
                StoreImage(this, Uri.parse(filePhoto.getAbsolutePath()), filePhoto);
                abspath = filePhoto.getAbsolutePath();
                String imagePath = "";

                if(!gallery && abspath != ""){
                    String[] splitFolder = abspath.split(Pattern.quote(File.separator));
                    imagePath = splitFolder[splitFolder.length -1];
                    splitFolder[splitFolder.length - 1] = "";
                    abspath = "";

                    for(int i = 0; i < splitFolder.length-1; i++){
                        abspath += splitFolder[i] + File.separator;
                    }
                }

                if(isMorePhoto){
                    Intent back = new Intent(this, ImagesListActivity.class);
                    back.putExtra("url_image", abspath);
                    if(!gallery){
                        back.putExtra("url_image_photo", imagePath);
                    }
                    startActivity(back);
                }else if(!isMorePhoto){
                    Intent back = new Intent(this, ProfileTecnologyActivity.class);
                    back.putExtra("url_image", abspath);
                    back.putExtra("new_photo", newPhoto);
                    if(!gallery){
                        back.putExtra("url_image_photo", imagePath);
                    }
                    startActivity(back);
                }

            }catch(Exception e){
                e.printStackTrace();
                returnFunction();
            }


        }



        /*if (requestCode == PICK_FROM_CAMERA) {
            Bundle extras = data.getExtras();
            Uri picPath = data.getData();
            try {
                //Bitmap picture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picPath);
                //newPhoto.setImageBitmap(picture);
                tecPhoto = picture;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void dispatchTakePictureIntent() {

        String dir = sys.getName().replace(" ", "_");
        dir = dir.replace(".", "DOT8");
        dir += Utilities.getImageCounter();

        Log.v("lastcheck", dir);
        String tecPhotoStringPath = Environment.getExternalStorageDirectory() + File.separator + "Nololed" + File.separator + sys.getName().replace(" ", "_") + File.separator +  dir + ".png";
        // Se viene da profile aggiungo una cartella davanti al file

        if(!gallery){
            if(tecFolderName == "") {
                String suffix = "tecFolder" + Utilities.getImageCounter();
                File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Nololed" + File.separator + sys.getName().replace(" ", "_") + File.separator + suffix);
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }

                if(success){
                    tecPhotoStringPath = folder.getAbsolutePath()  + File.separator + dir + "-" + suffix + ".png";
                }
            }else{
                String[] splitter = tecFolderName.split(Pattern.quote("/"));
                String suffix = splitter[splitter.length-1];
                tecPhotoStringPath = tecFolderName + File.separator + dir + "-" + suffix +".png";
            }
        }

        Log.v("lastcheck", "tec: " + tecPhotoStringPath);
        filePhoto = new File(tecPhotoStringPath);
        tecPhotoPath = Uri.fromFile(filePhoto);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tecPhotoPath);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


            startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
        }
    }


    public static void StoreImage(Context mContext, Uri imageLoc, File imageDir) {
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageLoc);
            FileOutputStream out = new FileOutputStream(imageDir);
            bm.compress(Bitmap.CompressFormat.JPEG, 60, out);
            bm.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        return;
    }


}
