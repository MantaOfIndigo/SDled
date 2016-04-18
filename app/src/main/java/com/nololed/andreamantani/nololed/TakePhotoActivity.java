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

/**
 * Created by andreamantani on 28/03/16.
 */
public class TakePhotoActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 1;

    SystemTec sys;
    File filePhoto;
    String abspath;
    Uri tecPhotoPath;

    boolean isMorePhoto;

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
        }

        if(extras.getInt("new_photo") == 0) {
            newPhoto = 0;
        }
        if(extras.getInt("new_photo") == 1){
            newPhoto = 1;
        }
        dispatchTakePictureIntent();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(TakePhotoActivity.this, "Si è verificato un errore", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == PICK_FROM_CAMERA) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            newPhoto.setImageBitmap(imageBitmap);
            tecPhotoPath = Uri.f
            //tecPhotoPath = data.getData();
            //Log.v("cont", "filepath: " + tecPhotoPath.getPath());*/

            try{
                StoreImage(this, Uri.parse(filePhoto.getAbsolutePath()), filePhoto);
                abspath = filePhoto.getAbsolutePath();

            }catch(Exception e){
                e.printStackTrace();
            }
        }


        if(isMorePhoto){
            Intent back = new Intent(this, ImagesListActivity.class);
            back.putExtra("url_image", abspath);
            startActivity(back);
        }else {
            Intent back = new Intent(this, ProfileTecnologyActivity.class);
            back.putExtra("url_image", abspath);
            back.putExtra("new_photo", newPhoto);
            startActivity(back);
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
        Log.v("lastcheck", dir);
        String tecPhotoStringPath = Environment.getExternalStorageDirectory() + File.separator + "Nololed" + File.separator + sys.getName().replace(" ", "_") + File.separator +  dir + Utilities.getImageCounter() + ".jpg";

        Log.v("lastcheck", "tec: " + tecPhotoStringPath);
        filePhoto = new File(tecPhotoStringPath);
        tecPhotoPath = Uri.fromFile(filePhoto);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tecPhotoPath);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            Toast.makeText(TakePhotoActivity.this, "Fotografa una tecnologia", Toast.LENGTH_LONG).show();


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
